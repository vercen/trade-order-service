package com.ksyun.trade.controller.online;

import com.ksyun.trade.dto.VoucherDeductDTO;
import com.ksyun.trade.rest.RestResult;
import com.ksyun.trade.service.GatewayService;
import com.ksyun.trade.service.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class GatewayController {
    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private RateLimiter rateLimiter;

    /**
     * 查询订单详情 (GET)
     */
    @RequestMapping(value = "/online/queryOrderInfo", produces = "application/json")
    public Object queryOrderInfo(Integer id, HttpServletRequest request) {
        //获取请求头X-KSY-REQUEST-ID
        String requestId = request.getHeader("X-KSY-REQUEST-ID");
        return gatewayService.orderloadLalancing(id,requestId);
    }

    /**
     * 根据机房Id查询机房名称 (GET)
     */
    @RequestMapping(value = "/online/queryRegionName", produces = "application/json")
    public Object queryRegionName(Integer regionId) {
        return gatewayService.regionIdloadLalancing(regionId);
    }

    /**
     * 订单优惠券抵扣 (POST json)
     */
    @RequestMapping(value = "/online/voucher/deduct", produces = "application/json")
    public Object deduct(@RequestBody VoucherDeductDTO param,HttpServletRequest request) {
        String requestId = request.getHeader("X-KSY-REQUEST-ID");
        return gatewayService.deductloadLalancing(param,requestId);
    }

    /**
     * 基于Redis实现漏桶限流算法，并在API调用上体现
     */
    @RequestMapping(value = "/online/listUpstreamInfo", produces = "application/json")
    public Object listUpstreamInfo() {
        if (rateLimiter.limitRequest()) {
            System.out.println("正常访问");
            return RestResult.success();
        }
        System.out.println("访问超限");
        return RestResult.limit();
    }

}
