package com.ljtao.dtxmqtxbank2.message;

import com.alibaba.fastjson.JSONObject;
import com.ljtao.dtxmqtxbank2.entity.AccountChangeEvent;
import com.ljtao.dtxmqtxbank2.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ljtao3 on 2020/4/2
 */
@Component
@RocketMQMessageListener(topic = "topic_txmsg",consumerGroup = "consumer_txmsg_group_bank2")
@Slf4j
public class TxmsgConsumer implements RocketMQListener<String> {
    @Autowired
    AccountInfoService accountInfoService;
    @Override
    public void onMessage(String s) {
        log.info("开始消费消息:{}",s);
        JSONObject jsonObject = JSONObject.parseObject(s);
        AccountChangeEvent ace = JSONObject.parseObject(jsonObject.getString("accountChange"), AccountChangeEvent.class);
        //为某个账户转账
        ace.setAccountNo("2");
        accountInfoService.addAccountInfoBalance(ace);
    }
}
