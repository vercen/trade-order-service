package com.ksyun.trade.service;

import com.ksyun.trade.BaseSpringAllTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrdertByConfigTest extends BaseSpringAllTest {
    @Autowired
    private OrdertByConfig ordertByConfig;

    @Test
    public void query() {
        ordertByConfig.query(1);
    }
}
