package com.ksyun.trade.service;

import com.ksyun.trade.BaseSpringAllTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RateLimiterTest extends BaseSpringAllTest {
    @Autowired
    private RateLimiter rateLimiter;

    @Test
    public void testLimitRequest() {
        for (int i = 0; i < 10; i++) {
            System.out.println(rateLimiter.limitRequest());
        }
    }
}
