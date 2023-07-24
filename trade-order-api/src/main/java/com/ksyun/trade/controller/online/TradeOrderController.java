package com.ksyun.trade.controller.online;

import com.ksyun.trade.rest.RestResult;
import com.ksyun.trade.service.TradeOrderService;
import com.ksyun.trade.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/online/trade_order")
@Slf4j
public class TradeOrderController {
    private static final String ORDER_VO_KEY_PREFIX = "orderVo:";

    @Autowired
    private TradeOrderService orderService;

    @Autowired
    private RedisTemplate<String ,Object> redisTemplate;

    @RequestMapping("/{id}")
    public RestResult query(@PathVariable("id") Integer id, @RequestHeader("upsteam") String upsteam) {
        OrderVo orderVo =(OrderVo) redisTemplate.opsForValue().get(ORDER_VO_KEY_PREFIX + id);
        if (orderVo != null) {
            orderVo.setUpsteam(upsteam);
            log.info("订单ID: {}，缓存命中", id);
            return RestResult.success().data(orderVo);
        }

        orderVo = orderService.query(id);
        orderVo.setUpsteam(upsteam);
        redisTemplate.opsForValue().set(ORDER_VO_KEY_PREFIX + id, orderVo);
        log.info("订单ID: {}，缓存未命中，添加到缓存", id);
        return RestResult.success().data(orderVo);
    }
}
