package com.ljtao.redisdistributedlock.controller;

import lombok.extern.slf4j.Slf4j;
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
    @GetMapping("/fun1")
    public String  fun1(){
        log.info("fun1,info");
        log.debug("fun1,debug");
        log.error("fun1,error");
        return "fun1";
    }
}
