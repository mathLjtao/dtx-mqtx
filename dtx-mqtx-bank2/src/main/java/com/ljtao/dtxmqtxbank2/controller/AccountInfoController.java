package com.ljtao.dtxmqtxbank2.controller;


import com.ljtao.dtxmqtxbank2.dao.AccountInfoDao;
import com.ljtao.dtxmqtxbank2.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ljtao3 on 2020/4/1
 */
@RestController
@RequestMapping("ai")
@Slf4j
public class AccountInfoController {
    @Autowired
    private AccountInfoDao accountInfoDao;
    /*
    测试能不能正常连接数据库
     */
    @GetMapping("/fun1/{id}")
    public JsonData fun1(@PathVariable("id") int id){
        return JsonData.success(accountInfoDao.getById(id));
    }
}
