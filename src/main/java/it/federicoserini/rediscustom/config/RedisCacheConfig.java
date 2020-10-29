package it.federicoserini.rediscustom.config;

import it.federicoserini.rediscustom.config.compression.SnappyCompressorAdapter;
import it.federicoserini.rediscustom.config.serialization.KryoSerializerAdapter;
import it.federicoserini.rediscustom.data.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @author Federico Serini
 */
@Configuration
@Log4j2
public class RedisCacheConfig {

    //@Value("${redis_host}")
    private String host = "localhost";

    //@Value("${redis_port}")
    private Integer port = 6379;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));
    }

    @Bean
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableKeyPrefix()
                .disableCachingNullValues()
                .entryTtl(Duration.ofHours(24))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(snappyCompressorAdapter()));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
                .cacheDefaults(redisCacheConfiguration).build();
    }

    @Bean
    public RedisTemplate<String, Person> redisTemplate() {
        RedisTemplate<String, Person> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setHashValueSerializer(snappyCompressorAdapter());
        return template;
    }

    @Bean
    public SnappyCompressorAdapter snappyCompressorAdapter() {
        return new SnappyCompressorAdapter<>(new KryoSerializerAdapter<>());
    }
}
