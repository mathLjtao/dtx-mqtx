package com.ljtao.dtxmqtxbank1.controller;

import com.ljtao.dtxmqtxbank1.dao.AccountInfoDao;
import com.ljtao.dtxmqtxbank1.entity.AccountChangeEvent;
import com.ljtao.dtxmqtxbank1.service.AccountInfoService;
import com.ljtao.dtxmqtxbank1.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author ljtao3 on 2020/4/1
 */
@RestController
@RequestMapping("ai")
public class AccountInfoController {
    @Autowired
    private AccountInfoDao accountInfoDao;
    @Autowired
    private AccountInfoService accountInfoService;

    @GetMapping("/transfer")
    public JsonData transfer(String accountNo,Double amount){
        String tx_no= UUID.randomUUID().toString();
        AccountChangeEvent ace = new AccountChangeEvent(accountNo, amount, tx_no);
        accountInfoService.sendUpdateAccountBalance(ace);
        return JsonData.success();
    }
    /*
    测试能不能正常连接数据库
     */
    @GetMapping("/fun1/{id}")
    public JsonData fun1(@PathVariable("id") int id){
        return JsonData.success(accountInfoDao.getById(id));
    }
}
