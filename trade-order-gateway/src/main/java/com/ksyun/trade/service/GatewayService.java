package com.ksyun.trade.service;

import com.ksyun.trade.client.OrderClient;
import com.ksyun.trade.dto.VoucherDeductDTO;
import com.ksyun.trade.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class GatewayService {

    @Autowired
    private OrderClient orderClient;

    private final RestTemplate restTemplate;
    private static final String ONLINE_TRADE_ORDER_ENDPOINT = "/online/trade_order/";
    private static final String ONLINE_REGION_NAME_ENDPOINT = "/online/region/name/";

    @Value("${meta}")
    private String mate;

    @Autowired
    public GatewayService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Object orderloadLalancing(Integer param, String requestId) {
        String url = orderClient.getRandomUrl() + ONLINE_TRADE_ORDER_ENDPOINT + param;
        HttpHeaders headers = createHeaders(url, requestId);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Object.class);
        return responseEntity.getBody();
    }

    public Object regionIdloadLalancing(Integer param) {
        int maxRetries = 5;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            ResponseEntity<Map> forEntity = restTemplate.getForEntity(mate + ONLINE_REGION_NAME_ENDPOINT + param, Map.class);
            Map<String, Object> responseMap = forEntity.getBody();
            if ( (int) responseMap.get("code") == 200 && responseMap.get("msg")!=null && responseMap.get("data") != null) {
                return forEntity.getBody();
            } else {
                retryCount++;
            }
        }
        return RestResult.success().data("请求数据不存在");
    }

    public Object deductloadLalancing(VoucherDeductDTO param, String requestId) {
        String url = orderClient.getRandomUrl() + "/online/order_coupon";

        HttpHeaders headers = createHeaders(url, requestId);
        HttpEntity<VoucherDeductDTO> requestEntity = new HttpEntity<>(param, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);
        return responseEntity.getBody();
    }

    private HttpHeaders createHeaders(String url, String requestId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("upsteam", url);
        headers.add("X-KSY-REQUEST-ID", requestId);
        return headers;
    }
}
