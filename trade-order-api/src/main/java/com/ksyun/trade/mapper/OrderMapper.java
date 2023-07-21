package com.ksyun.trade.mapper;


import com.ksyun.trade.pojo.KscTradeOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper  {

    KscTradeOrder queryById(int id);

}
