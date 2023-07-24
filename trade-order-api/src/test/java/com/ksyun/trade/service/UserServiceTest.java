package com.ksyun.trade.service;

import com.ksyun.trade.BaseSpringAllTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;

public class UserServiceTest extends BaseSpringAllTest {
    @Autowired
    private UserService userService;

    @Test
    public void querybyid() {
        userService.querybyid(1);
    }

}
