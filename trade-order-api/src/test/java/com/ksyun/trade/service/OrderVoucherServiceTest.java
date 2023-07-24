package com.ksyun.trade.service;

import com.ksyun.trade.BaseSpringAllTest;
import com.ksyun.trade.dto.VoucherDeductDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class OrderVoucherServiceTest extends BaseSpringAllTest {
    @Autowired
    private OrderVoucherService orderVoucherService;

    @Test
    public void calculateDiscountAmount() {
        VoucherDeductDTO voucherDeductDTO = new VoucherDeductDTO();
        voucherDeductDTO.setOrderId(1);
        voucherDeductDTO.setVoucherNo("NO362543");
        voucherDeductDTO.setAmount(new BigDecimal(100));
        orderVoucherService.calculateDiscountAmount(voucherDeductDTO);
    }
}
