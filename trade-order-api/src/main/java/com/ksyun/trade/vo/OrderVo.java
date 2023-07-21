package com.ksyun.trade.vo;

import com.ksyun.trade.pojo.KscTradeProductConfig;
import com.ksyun.trade.pojo.Region;
import com.ksyun.trade.pojo.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderVo {
    private String upsteam;
    private int id;
    private BigDecimal priceValue;
    private User user;
    private Region region;
    private List<KscTradeProductConfig> configs;
}
