package com.ksyun.trade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
public class RateLimiter {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final long intervalTime = 1000;
    private static final int maxRequests = 5; // QPS = 5
    // 漏桶限流算法
    public Boolean limitRequest() {
        Long currentTime = new Date().getTime();
        String requestId = getID();
        String luaScriptPath = "ratelimiter.lua"; // Lua脚本在resources目录下

        // 读取Lua脚本文件
        ClassPathResource scriptResource = new ClassPathResource(luaScriptPath);

        // 创建RedisScript对象
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(scriptResource);
        redisScript.setResultType(Long.class);

        // 执行Lua脚本
        Long result = redisTemplate.execute(redisScript, Arrays.asList("ratelimiter"),
                String.valueOf(currentTime), String.valueOf(intervalTime), requestId, String.valueOf(maxRequests));

        return result != null && result == 1;
    }

    private String getID() {
        return UUID.randomUUID().toString();
    }

}
