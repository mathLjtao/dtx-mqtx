package com.ljtao.redisdistributedlock.strategy;

import com.ljtao.redisdistributedlock.entity.RedissonProperties;
import org.redisson.config.Config;

/**
 * @Description: Redisson配置构建接口
 *
 */
public interface RedissonConfigService {

    /**
     * 根据不同的Redis配置策略创建对应的Config
     * @param redissonProperties
     * @return Config
     */
    Config createRedissonConfig(RedissonProperties redissonProperties);
}