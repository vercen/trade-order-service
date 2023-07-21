package com.ksyun.trade.service;

import com.ksyun.trade.client.OrderClient;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class GatewayService {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private RestTemplate restTemplate;

    public Object loadLalancing(Object param) {
        // 1. 模拟路由 (负载均衡) 获取接口
        String url = orderClient.getRandomUrl();
        log.info("url:{}", url);
        log.info("param:{}", param);
        // 2. 请求转发
        HttpHeaders headers = new HttpHeaders();
        headers.add("requestId", "test123456"); // 在这里添加 "requestId" 头部
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "online/trade_order/" + param, String.class, headers);
        return forEntity.getBody();
    }

}
