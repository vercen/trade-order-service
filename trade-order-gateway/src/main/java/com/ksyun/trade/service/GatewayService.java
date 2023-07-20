package com.ksyun.trade.service;

import com.ksyun.trade.rest.RespBean;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {

    public Object loadLalancing(Object param) {
        // 1. 模拟路由 (负载均衡) 获取接口

        // 2. 请求转发
        return RespBean.success();
    }


}
