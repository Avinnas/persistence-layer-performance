package com.example.persistencelayer.configuration;

import com.example.persistencelayer.model.Product;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CachingConfig {

//    @Bean
//    public CacheManager cacheManager(Caffeine caffeine) {
//        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
//        caffeineCacheManager.setCaffeine(caffeine);
//        return caffeineCacheManager;
//    }
//
//    @Bean
//    public Caffeine caffeineConfig() {
//        return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
//    }

}
