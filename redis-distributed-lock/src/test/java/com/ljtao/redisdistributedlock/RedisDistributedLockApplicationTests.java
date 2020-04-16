package com.ljtao.redisdistributedlock;

import com.ljtao.redisdistributedlock.redisson.RedissonLock;
import com.ljtao.redisdistributedlock.service.LockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisDistributedLockApplicationTests {

    @Autowired
    private RedissonLock redissonLock;
    @Autowired
    private LockService lockService;

    @Test
    void contextLoads() {
    }


}
