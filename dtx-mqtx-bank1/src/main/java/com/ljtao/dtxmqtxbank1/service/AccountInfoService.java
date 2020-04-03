package com.ljtao.dtxmqtxbank1.service;

import com.alibaba.fastjson.JSONObject;
import com.ljtao.dtxmqtxbank1.dao.AccountInfoDao;
import com.ljtao.dtxmqtxbank1.entity.AccountChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author ljtao3 on 2020/4/1
 */
@Slf4j
@Service
public class AccountInfoService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private AccountInfoDao accountInfoDao;
    /**
     * 更新帐号余额‐发送消息
     * producer向MQ Server发送消息
     *
     */
    public void sendUpdateAccountBalance(AccountChangeEvent ace){
        //构件消息体
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("accountChange",ace);
        Message<String> message= MessageBuilder.withPayload(jsonObject.toJSONString()).build();
        //发送一条事务消息
        /**
         * String txProducerGroup 生产组
         * String destination topic，
         * Message<?> message, 消息内容
         * Object arg 参数
         */
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("producer_group_txmsg_bank1",
                "topic_txmsg", message, null);

        System.out.println("send transcation message body="+message.getPayload()+",result="+sendResult.getSendStatus());

    }
    /**
     * 更新帐号余额‐本地事务
     * producer发送消息完成后接收到MQ Server的回应即开始执行本地事务
     */
    @Transactional
    public void doUpdateAccountBalance(AccountChangeEvent ace) {
        System.out.println("开始更新本地事务，事务号："+ace.getTxNo());

        accountInfoDao.updateAccountBalance(ace.getAccountNo(),ace.getAmount() * -1);
        //为幂等性做准备
        accountInfoDao.addTx(ace.getTxNo());

        //测试
        if(ace.getAmount()==2){
            throw new RuntimeException("bank1更新本地事务时抛出异常");
        }
        log.info("结束更新本地事务，事务号：{}",ace.getTxNo());
    }
        public  int transfer(String accountNo,Double amount){
        return accountInfoDao.updateAccountBalance(accountNo,amount);
    }
}
