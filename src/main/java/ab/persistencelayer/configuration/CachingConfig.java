package ab.persistencelayer.configuration;

import org.springframework.cache.annotation.EnableCaching;

import org.springframework.context.annotation.Configuration;

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
