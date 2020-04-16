package com.ljtao.redisdistributedlock.controller;

import com.ljtao.redisdistributedlock.service.LockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ljtao3 on 2020/4/15
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class Test {
    @Autowired
    private LockService lockService;
    @GetMapping("/fun1")
    public String  fun1(){
        log.info("fun1,info");
        log.debug("fun1,debug");
        log.error("fun1,error");
        return "fun1";
    }
    @GetMapping("/fun2")
    public String fun2(){
        System.out.println("-----------开始");
        for(int i=0;i<100;i++){
            new Thread(()->{
                try {
                    lockService.testRedissonLock();
                    //lockService.trylockDecreaseStock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        return "fun2";
    }
    /*
        测试注解形式的分布式锁
     */
    @GetMapping("/fun3")
    public String fun3(){
        System.out.println("-----------开始");
        for(int i=0;i<100;i++){
            new Thread(()->{
                try {
                    lockService.annotationRessionLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        return "fun3";
    }

}
