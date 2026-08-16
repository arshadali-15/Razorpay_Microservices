package com.spring.razorpay_micro.common_lib.ratelimit;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
public class SlidingWindowRateLimiter implements RateLimiter {

    private final StringRedisTemplate redis;

    @Override
    public RateLimitResult check(String key, int maxRequestAllowed, long windowSeconds) {
        long nowMs = System.currentTimeMillis() / 1000;
        long floorMs = nowMs - windowSeconds * 1000;

        String redisKey = "ratelimit:sliding" + key;

        var zset = redis.opsForZSet();
        zset.removeRangeByScore(redisKey, Double.NEGATIVE_INFINITY, floorMs);

        Long count = zset.zCard(redisKey);
        long curr = count != null ? count : 0;

        if (curr >= maxRequestAllowed) {
            var oldest = zset.rangeWithScores(redisKey, 0, 0);
            int retryAfter = 1;

            if (oldest != null && !oldest.isEmpty()) {
                Double oldestScore = oldest.iterator().next().getScore();
                if (oldestScore != null) {
                    long windowExpiresMs = oldestScore.longValue() + windowSeconds * 1000;
                    retryAfter = (int) Math.ceil(windowExpiresMs - nowMs) / 1000;

                }
            }
            return RateLimitResult.denied(retryAfter);
        }
        zset.add(redisKey, UUID.randomUUID().toString(), nowMs);

        redis.expire(redisKey, Duration.ofSeconds(windowSeconds + 1));

        return RateLimitResult.allowed((int) (maxRequestAllowed - curr - 1));
    }
}
