package com.spring.razorpay_micro.common_lib.idempotency;

import com.spring.razorpay_micro.common_lib.context.MerchantContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerExceptionResolver;

@AutoConfiguration
public class SharedResilienceAutoConfiguration {

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean
    public IdempotencyStore idempotencyStore(StringRedisTemplate stringRedisTemplate) {
        return new RedisIdempotencyStore(stringRedisTemplate);
    }

    @Bean
    public IdempotencyFilter idempotencyFilter(MerchantContext merchantContext,
                                               IdempotencyStore idempotencyStore,
                                               @Qualifier("handleExceptionResolver") HandlerExceptionResolver resolver
    ) {
        return new IdempotencyFilter(merchantContext, idempotencyStore, resolver);
    }


//    @Bean
//    @ConditionalOnProperty(name = "app.ratelimit.enabled", havingValue = "fixed")
//    public RateLimiter fixedRateLimiter()
}
