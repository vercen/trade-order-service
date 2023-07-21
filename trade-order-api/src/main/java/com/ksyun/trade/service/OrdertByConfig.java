package com.ksyun.trade.service;

import com.ksyun.trade.mapper.KscTradeProductConfigMapper;
import com.ksyun.trade.pojo.KscTradeProductConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdertByConfig {
    @Autowired
    private KscTradeProductConfigMapper kscTradeProductConfigMapper;

    public List<KscTradeProductConfig> query(Integer id) {
        return kscTradeProductConfigMapper.selectByOrderId(id);
    }
}
