package com.ksyun.trade.service;

import com.ksyun.trade.client.OrderClient;
import com.ksyun.trade.dto.VoucherDeductDTO;
import com.ksyun.trade.rest.RestResult;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class GatewayService {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${meta}")
    private String mate;

    public Object orderloadLalancing(Integer param,String requestId) {
        // 1. 模拟路由 (负载均衡) 获取接口
        String url = orderClient.getRandomUrl() + "/online/trade_order/" + param;
        log.info("url:{}", url);
        log.info("param:{}", param);
        // 2. 请求转发
        // 创建 HttpHeaders 对象，并添加自定义请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("upsteam", url);
        headers.add("X-KSY-REQUEST-ID", requestId);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Object.class);
        return responseEntity.getBody();
    }

    //根据机房Id查询机房名称
    public Object regionIdloadLalancing(Integer param) {

        int maxRetries = 3;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            ResponseEntity<Map> forEntity = restTemplate.getForEntity(mate + "/online/region/name/" + param, Map.class);
            Map<String, Object> responseMap = forEntity.getBody();
            if ((int) responseMap.get("code") == 500&&(String) responseMap.get("msg")!="null") {
                retryCount++;
            } else {
                return forEntity.getBody();
            }
        }
        return RestResult.success().data("请求数据不存在");
    }

    public Object deductloadLalancing(VoucherDeductDTO param,String requestId) {
        // 1. 模拟路由 (负载均衡) 获取接口
        String url = orderClient.getRandomUrl() + "/online/order_coupon";
        // 2. 请求转发

        HttpHeaders headers = new HttpHeaders();
        headers.add("upsteam", url);
        headers.add("X-KSY-REQUEST-ID", requestId);
        // 3. 构造请求参数
        HttpEntity<VoucherDeductDTO> requestEntity = new HttpEntity<>(param, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);
        return responseEntity.getBody();
    }
}
