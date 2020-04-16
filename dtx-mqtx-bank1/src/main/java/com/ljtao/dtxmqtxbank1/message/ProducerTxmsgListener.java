package com.ljtao.dtxmqtxbank1.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljtao.dtxmqtxbank1.dao.AccountInfoDao;
import com.ljtao.dtxmqtxbank1.entity.AccountChangeEvent;
import com.ljtao.dtxmqtxbank1.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;

/**
 * @author ljtao3 on 2020/4/1
 */
@Component
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "producer_group_txmsg_bank1")
public class ProducerTxmsgListener implements RocketMQLocalTransactionListener{
    @Autowired
    AccountInfoService accountInfoService;
    @Autowired
    AccountInfoDao accountInfoDao;
    /**
     ‐ 发送prepare消息成功此方法被回调，该方法用于执行本地事务
     ‐ @param msg 回传的消息，利用transactionId即可获取到该消息的唯一Id
     ‐ @param arg 调用send方法时传递的参数，当send时候若有额外的参数可以传递到send方法中，这里能获取到
     ‐ @return 返回事务状态，COMMIT：提交 ROLLBACK：回滚 UNKNOW：回调
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try{
            //解析消息内容
            String jsonString = new String((byte[]) message.getPayload());
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            AccountChangeEvent ace = JSONObject.parseObject(jsonObject.getString("accountChange"), AccountChangeEvent.class);
            //扣除金额
            accountInfoService.doUpdateAccountBalance(ace);

            //Thread.sleep(30000);//延迟发送MQ
            return RocketMQLocalTransactionState.COMMIT;


            //return RocketMQLocalTransactionState.UNKNOWN;
            //return RocketMQLocalTransactionState.ROLLBACK; //后面这两个在这里的用法效果就是不想bank2接收到这个信息。
        }
        catch (Exception e){
            System.out.println("事务执行失败");
            e.printStackTrace();
            //return RocketMQLocalTransactionState.ROLLBACK;//表示队列中的消息，不提交到消费者那里去了。这里就是不让消息到bank2项目那里去了。
            return RocketMQLocalTransactionState.ROLLBACK;//表示队列中的消息，不提交到消费者那里去了。这里就是不让消息到bank2项目那里去了。
        }
    }
    /**
     ‐ @param msg 通过获取transactionId来判断这条消息的本地事务执行状态
     ‐ @return 返回事务状态，COMMIT：提交 ROLLBACK：回滚 UNKNOW：回调
     */
    //检查事务执行状态
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        RocketMQLocalTransactionState state;
        final JSONObject jsonObject = JSON.parseObject(new String((byte[])
                message.getPayload()));
        AccountChangeEvent accountChangeEvent =
                JSONObject.parseObject(jsonObject.getString("accountChange"),AccountChangeEvent.class);
        //事务id
        String txNo = accountChangeEvent.getTxNo();
        int isexistTx = accountInfoDao.isExistTx(txNo);
        log.info("回查事务，事务号: {} 结果: {}", accountChangeEvent.getTxNo(),isexistTx);
        if(isexistTx>0){
            state= RocketMQLocalTransactionState.COMMIT;
        }else{
            state= RocketMQLocalTransactionState.UNKNOWN;
        }
        return state;
    }
}
