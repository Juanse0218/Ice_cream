package com.example.IceCream_SpringBoot.config;

import java.util.HashMap;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class DBcacheconfig {

    public static final String CACHE_HALMACEN = "almacen";

    public static final String CACHE_HELADERIA = "heladeria";

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        var config = new Config();
        config.useSingleServer()
            .setAddress("redis://redis:6379")
            .setPassword("icecream");
        return Redisson.create(config);
    }

    @Bean
     public CacheManager cacheName(RedissonClient redissonClient){
        HashMap<String, CacheConfig> configmap = new HashMap<>();
        configmap.put(CACHE_HALMACEN, new CacheConfig());
        configmap.put(CACHE_HELADERIA, new CacheConfig());
        //var config = Collections.singletonMap(CACHE_NAME, new CacheConfig());
        return new RedissonSpringCacheManager(redissonClient, configmap);
    }
}
