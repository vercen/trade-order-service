package com.ksyun.trade.service;

import com.ksyun.trade.mapper.KscTradeProductConfigMapper;
import com.ksyun.trade.pojo.KscTradeProductConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdertByConfig {
    @Autowired
    private KscTradeProductConfigMapper kscTradeProductConfigMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public List<KscTradeProductConfig> query(Integer id) {
        List<KscTradeProductConfig> configList =(List<KscTradeProductConfig>) redisTemplate.opsForValue().get("config" + id);
        if (configList != null) {
            return configList;
        }
        System.out.println("查询数据库");
        System.out.println("id = " + id);
        configList = kscTradeProductConfigMapper.selectByOrderId(id);
        redisTemplate.opsForValue().set("config:" + id, configList);
        return configList;
    }
}
