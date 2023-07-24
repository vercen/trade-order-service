package com.ksyun.trade.controller.online;

import com.ksyun.trade.dto.VoucherDeductDTO;
import com.ksyun.trade.rest.RestResult;
import com.ksyun.trade.service.OrderVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class OrderVoucherController {

    @Autowired
    private OrderVoucherService orderVoucherService;

    @RequestMapping(value = "/online/order_coupon", produces = "application/json")
    @ResponseBody
    public Object calculateDiscountAmount(@RequestBody VoucherDeductDTO param){
        Boolean aBoolean = orderVoucherService.calculateDiscountAmount(param);
        if(aBoolean){
            return RestResult.success();
        }
        return RestResult.failure();
    }




}