package com.ksyun.trade.mapper;

import com.ksyun.trade.pojo.KscTradeProductConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ksc_trade_product_config】的数据库操作Mapper
* @createDate 2023-07-21 16:24:38
* @Entity com.ksyun.trade.pojo.KscTradeProductConfig
*/
@Mapper
public interface KscTradeProductConfigMapper {


    List<KscTradeProductConfig> selectByOrderId(Integer orderId);

}
