package vn.hoanggiang.jobhunter.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // sets the Redis connection factory to establish connections to Redis
        template.setConnectionFactory(factory);

        // sets the serializer for keys to StringRedisSerializer to ensure keys are stored as readable strings
        template.setKeySerializer(new StringRedisSerializer());

        // sets the serializer for values to GenericJackson2JsonRedisSerializer to store values as JSON
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

}
