package com.ksyun.trade.controller.online;

import com.ksyun.trade.rest.RestResult;
import com.ksyun.trade.service.OrdertByConfig;
import com.ksyun.trade.service.TradeOrderService;
import com.ksyun.trade.service.UserService;
import com.ksyun.trade.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value = "/online/trade_order", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class TradeOrderController {
    @Autowired
    private TradeOrderService orderService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/{id}")
    public RestResult query(@PathVariable("id") Integer id, @RequestHeader("upsteam") String upsteam) {
        OrderVo orderVo = (OrderVo) redisTemplate.opsForValue().get("orderVo:" + id);
        if (orderVo != null) {
            orderVo.setUpsteam(upsteam);
            return RestResult.success().data(orderVo);
        }
        orderVo = orderService.query(id);
        orderVo.setUpsteam(upsteam);
        redisTemplate.opsForValue().set("orderVo:" + id, orderVo);
        return RestResult.success().data(orderVo);
    }

}
