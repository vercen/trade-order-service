package com.ksyun.trade.service;

import com.ksyun.trade.mapper.OrderMapper;
import com.ksyun.trade.pojo.KscTradeOrder;
import com.ksyun.trade.pojo.KscTradeProductConfig;
import com.ksyun.trade.pojo.Region;
import com.ksyun.trade.pojo.User;
import com.ksyun.trade.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ksyun.trade.service.RegionListService.regionList;

@Service
@Slf4j
public class TradeOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    UserService userService;
    @Autowired
    private OrdertByConfig ordertByConfig;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public OrderVo query(Integer id) {
        KscTradeOrder tradeOrder = (KscTradeOrder)redisTemplate.opsForValue().get("order:" + id);
        if (tradeOrder == null) {
             tradeOrder = orderMapper.queryById(id);
            redisTemplate.opsForValue().set("order:" + id, tradeOrder);
        }
        //调用用户查询服务
        User user = userService.querybyid(tradeOrder.getUserId());
        //获取订单配置信息
        List<KscTradeProductConfig> orderconfig = ordertByConfig.query(id);
        //获取地域信息
        Region region = regionList.get(tradeOrder.getRegionId().toString());
        OrderVo orderVo = new OrderVo();
        orderVo.setId(tradeOrder.getId());
        orderVo.setPriceValue(tradeOrder.getPriceValue());
        orderVo.setUser(user);
        orderVo.setRegion(region);
        orderVo.setConfigs(orderconfig);
        return orderVo;
    }

}