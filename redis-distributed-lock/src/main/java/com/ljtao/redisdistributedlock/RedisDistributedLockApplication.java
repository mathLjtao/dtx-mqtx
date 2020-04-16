package com.ljtao.redisdistributedlock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling//开启定时任务
@SpringBootApplication
public class RedisDistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDistributedLockApplication.class, args);
    }

}
