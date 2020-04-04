package com.ljtao.dtxmqtxbank2.service;

import com.ljtao.dtxmqtxbank2.dao.AccountInfoDao;
import com.ljtao.dtxmqtxbank2.entity.AccountChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ljtao3 on 2020/4/2
 */
@Slf4j
@Service
public class AccountInfoService {
    @Autowired
    AccountInfoDao accountInfoDao;
    /**
     * 消费消息，更新本地事务，添加金额
     */
    @Transactional
    public void addAccountInfoBalance(AccountChangeEvent ace) {
        log.info("bank2更新本地账号，账号：{},金额： {}",ace.getAccountNo(),ace.getAmount());
        int tx_no = accountInfoDao.isExistTx(ace.getTxNo());
        if(tx_no<=0){
            accountInfoDao.updateAccountBalance(ace.getAccountNo(),ace.getAmount());
            accountInfoDao.addTx(ace.getTxNo());
            log.info("更新本地事务执行成功，本次事务号: {}", ace.getTxNo());
        }else{
            log.info("更新本地事务执行失败，本次事务号: {}", ace.getTxNo());
        }
        if(ace.getAmount()==4){
            //throw new RuntimeException("人为产出异常！");
        }

    }
}
