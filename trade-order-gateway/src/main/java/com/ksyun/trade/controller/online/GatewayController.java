package com.ksyun.trade.controller.online;

import com.ksyun.trade.dto.VoucherDeductDTO;
import com.ksyun.trade.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GatewayController {
    @Autowired
    private GatewayService gatewayService;

    /**
     * 查询订单详情 (GET)
     */
    @RequestMapping(value = "/online/queryOrderInfo", produces = "application/json")
    public Object queryOrderInfo(Integer id) {
        return gatewayService.orderloadLalancing(id);
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
    @RequestMapping(value = "/online/deduct", produces = "application/json")
    public Object deduct(@RequestBody VoucherDeductDTO param) {
        return gatewayService.deductloadLalancing(param);
    }

    /**
     * 基于Redis实现漏桶限流算法，并在API调用上体现
     */
    @RequestMapping(value = "/online/listUpstreamInfo", produces = "application/json")
    public Object listUpstreamInfo() {
        return null;
    }

}
