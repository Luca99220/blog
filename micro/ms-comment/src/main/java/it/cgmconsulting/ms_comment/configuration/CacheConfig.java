package it.cgmconsulting.ms_comment.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public Caffeine<Object,Object> caffeineConfig(){
        return Caffeine.newBuilder()
                .expireAfterAccess(60, TimeUnit.MINUTES)
                .maximumSize(1000);
    }
    @Bean
    public CacheManager cacheManager(Caffeine<Object,Object> caffeine){
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
