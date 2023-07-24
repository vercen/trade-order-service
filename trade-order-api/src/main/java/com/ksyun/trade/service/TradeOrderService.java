package com.ksyun.trade.service;

import com.ksyun.trade.mapper.OrderMapper;
import com.ksyun.trade.pojo.KscTradeOrder;
import com.ksyun.trade.pojo.KscTradeProductConfig;
import com.ksyun.trade.pojo.User;
import com.ksyun.trade.vo.OrderVo;
import com.ksyun.trade.vo.RegionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TradeOrderService {
    private static final String ORDER_CACHE_KEY_PREFIX = "order:";
    private static final long ORDER_CACHE_EXPIRATION_SECONDS = 3600; // 缓存过期时间为1小时
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final OrdertByConfig ordertByConfig;
    private final RegionListService regionList;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public TradeOrderService(OrderMapper orderMapper, UserService userService,
                             OrdertByConfig ordertByConfig, RegionListService regionList,
                             RedisTemplate<String, Object> redisTemplate) {
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.ordertByConfig = ordertByConfig;
        this.regionList = regionList;
        this.redisTemplate = redisTemplate;
    }

    public OrderVo query(Integer id) {
        KscTradeOrder tradeOrder = (KscTradeOrder) redisTemplate.opsForValue().get(ORDER_CACHE_KEY_PREFIX + id);
        if (tradeOrder == null) {
            tradeOrder = orderMapper.queryById(id);
            redisTemplate.opsForValue().set(ORDER_CACHE_KEY_PREFIX + id, tradeOrder, ORDER_CACHE_EXPIRATION_SECONDS, TimeUnit.SECONDS);
        }
        //调用用户查询服务
        User user = userService.querybyid(tradeOrder.getUserId());
        //获取订单配置信息
        List<KscTradeProductConfig> orderconfig = ordertByConfig.query(id);
        //获取地域信息
        RegionVo region = regionList.getRegionById(tradeOrder.getRegionId());
        OrderVo orderVo = new OrderVo();
        orderVo.setId(tradeOrder.getId());
        orderVo.setPriceValue(tradeOrder.getPriceValue());
        orderVo.setUser(user);
        orderVo.setRegion(region);
        orderVo.setConfigs(orderconfig);
        return orderVo;
    }
}
