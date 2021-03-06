package com.ldblock.carid.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.ldblock.carid.constant.RedisConstant;

import java.lang.reflect.Parameter;
import java.time.Duration;

/**
   * 配置springboot cache缓存
 * @author 老徐
 */
@Configuration
@EnableCaching
public class CacheConfig {
	
	//配置使用redis来进行缓存，否则是ConcurrentHashMap来进行缓存
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer());

        //默认过期时间24小时
		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(pair).entryTtl(Duration.ofHours(24));

		//配置特定缓存的缓存时间
        //配置cahce_auth_code缓存时间为5分钟
		CacheManager cacheManager = RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
				.cacheWriter(redisCacheWriter).cacheDefaults(defaultCacheConfig)
                .withCacheConfiguration("cache_auth_code", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)))
				.build();

		return cacheManager;
	}

//    // 设置redis其他的缓存时间类型
//    public RedisCacheManager cacheManager5Minutes(RedisConnectionFactory redisConnectionFactory) {
//        RedisConfiguration redisConfiguration =
//    }



    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder builder = new StringBuilder();
            builder.append(o.getClass().getName()).append(RedisConstant.DIVIDER);
            builder.append(method.getName()).append(RedisConstant.DIVIDER);
            for (Parameter parameter : method.getParameters()) {
                builder.append(parameter.getName()).append(RedisConstant.DIVIDER);
            }
            return builder.toString();
        };
    }

    @Bean
    public KeyGenerator constantKeyGenerator() {
        return (o, method, objects) -> "default_key";
    }
}