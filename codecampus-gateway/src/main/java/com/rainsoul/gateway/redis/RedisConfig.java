package com.rainsoul.gateway.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis的config处理
 * 配置 RedisTemplate，它是 Spring Data Redis 提供的 Redis 操作模板，
 * 用于执行各种 Redis 操作。配置中使用了 StringRedisSerializer 来序列化键（key）和哈希键（hash key），
 * 使用 Jackson2JsonRedisSerializer 来序列化值（value）和哈希值（hash value），以支持 JSON 格式的数据。
 */
@Configuration
public class RedisConfig {

    /**
     * 创建并配置RedisTemplate，用于操作Redis数据库。
     *
     * @param redisConnectionFactory Redis连接工厂，用于创建Redis连接。
     * @return 配置好的RedisTemplate对象，可以用于执行Redis操作。
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // 配置字符串类型的序列化器
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);

        // 配置JSON类型的序列化器，用于序列化和反序列化Value和Hash的值
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());

        return redisTemplate;
    }


    /**
     * 创建并配置 Jackson2JsonRedisSerializer 对象。
     * 该方法用于初始化一个 Jackson2JsonRedisSerializer 实例，配置其对象映射器以支持JSON的序列化和反序列化。
     *
     * @return Jackson2JsonRedisSerializer<Object> 返回配置好的Jackson2JsonRedisSerializer实例。
     */
    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        // 创建 Jackson2JsonRedisSerializer 实例，并指定序列化对象的类型为 Object
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // 创建 ObjectMapper 实例以进行 JSON 序列化和反序列化配置
        ObjectMapper objectMapper = new ObjectMapper();

        // 配置 ObjectMapper，使其能自动检测任何属性，以便于反序列化时使用
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 配置 ObjectMapper，当遇到未知属性时，不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 启用 ObjectMapper 的默认类型推断，以便于反序列化时能正确处理泛型
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        // 将配置好的 ObjectMapper 设置到 Jackson2JsonRedisSerializer 中
        jsonRedisSerializer.setObjectMapper(objectMapper);

        return jsonRedisSerializer; // 返回配置好的序列化器实例
    }
}
