package com.ljtao.redisdistributedlock.service;

import com.ljtao.redisdistributedlock.annotation.DistributedLock;
import com.ljtao.redisdistributedlock.redisson.RedissonLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author ljtao3 on 2020/4/15
 */
@Service
public class LockService {
    @Autowired
    private RedissonLock redissonLock;

    /*
        测试单机部署Redisson配置
     */
    private static int count=20;
    public void  testRedissonLock() throws InterruptedException {
        redissonLock.lock("lock-value",200L);
        if(count>0){
            Thread.sleep(1000);
            --count;
        }
        System.out.println(LocalDateTime.now());
        System.out.println("count 的值 "+count);
        if(redissonLock.isHeldByCurrentThread("lock-value")){
            redissonLock.unlock("lock-value");
        }
    }
    /*
        测试单机部署Redisson配置，用tryLock() 方法
     */
    public void  trylockDecreaseStock() throws InterruptedException {
        if(redissonLock.tryLock("tryLock-value",5L,20L)){
            if (count > 0) {
                Thread.sleep(50);
                count--;
            }
            Thread.sleep(50);
            redissonLock.unlock("tryLock-value");
            System.out.println("count 的值 "+count);
        } else {
            System.out.println(("[ExecutorRedisson]获取锁失败"));
        }
    }

    /*
        注解形式设置分布式锁
        不过这种的颗粒度比较大， 但是使用方便
     */
    @DistributedLock(value="annLock-value",leaseTime = 10)
    public void annotationRessionLock() throws InterruptedException {
        if (count > 0) {
            Thread.sleep(50);
            count--;
        }
        System.out.println("count 的值 "+count);
    }


}
