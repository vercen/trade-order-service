package com.ksyun.trade.service;

import com.ksyun.trade.client.OrderClient;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@Slf4j
public class GatewayService {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private RestTemplate restTemplate;

    public Object loadLalancing(Object param) {
        // 1. 模拟路由 (负载均衡) 获取接口
        String url = orderClient.getRandomUrl()+ "/online/trade_order/" + param;
        log.info("url:{}", url);
        log.info("param:{}", param);
        // 2. 请求转发
        // 创建 HttpHeaders 对象，并添加自定义请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("requestId", UUID.randomUUID().toString());
        headers.add("upsteam", url);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Object.class);
        return responseEntity.getBody();
    }

}
