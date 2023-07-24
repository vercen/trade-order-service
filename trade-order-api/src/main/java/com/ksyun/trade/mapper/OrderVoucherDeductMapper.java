package com.ksyun.trade.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

@Mapper
public interface OrderVoucherDeductMapper {
  BigDecimal selectVoucherDeductAmount(Integer orderId);
}
