package com.ksyun.trade.service;

import com.ksyun.trade.BaseSpringAllTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class GatewayServiceTest extends BaseSpringAllTest {

    @Autowired
    private GatewayService gatewayService;

    @Test
    public void testOrderloadLalancing() {
        //api服务要先启动
       // gatewayService.orderloadLalancing(1, "www.baidu.com");
        log.info("api服务要先启动-testOrderloadLalancing");
    }
}
