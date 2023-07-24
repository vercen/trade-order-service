package com.ksyun.trade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class RateLimiter {
    private static final long INTERVAL_TIME = 1000;
    private static final int MAX_REQUESTS = 5; // QPS = 5
    private static final String LUA_SCRIPT_PATH = "ratelimiter.lua"; // Lua脚本在resources目录下

    private final RedisTemplate<String, String> redisTemplate;
    private final DefaultRedisScript<Long> redisScript;

    @Autowired
    public RateLimiter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisScript = new DefaultRedisScript<>();
        this.redisScript.setLocation(new ClassPathResource(LUA_SCRIPT_PATH));
        this.redisScript.setResultType(Long.class);
    }

    // 漏桶限流算法
    public Boolean limitRequest() {
        Long currentTime = System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();

        // 执行Lua脚本
        Long result = redisTemplate.execute(redisScript, Arrays.asList("ratelimiter"),
                String.valueOf(currentTime), String.valueOf(INTERVAL_TIME), requestId, String.valueOf(MAX_REQUESTS));

        return result != null && result == 1;
    }
}
