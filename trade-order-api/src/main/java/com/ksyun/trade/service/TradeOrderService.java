package com.ksyun.trade.service;

import com.ksyun.trade.mapper.OrderMapper;
import com.ksyun.trade.pojo.KscTradeOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradeOrderService {
    @Autowired
    private OrderMapper orderMapper;

    public Object query(Integer id) {
        log.info("id:{}", id);
        KscTradeOrder tradeOrder = orderMapper.queryById(id);

        log.info("tradeOrder:{}", tradeOrder.toString());
        //TODO
        return null;
    }

}