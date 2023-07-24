package com.ksyun.trade.service;

import com.ksyun.trade.dto.VoucherDeductDTO;
import com.ksyun.trade.mapper.KscVoucherDeductMapper;
import com.ksyun.trade.mapper.OrderVoucherDeductMapper;
import com.ksyun.trade.pojo.KscVoucherDeduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class OrderVoucherService {

    @Autowired
    private KscVoucherDeductMapper kscVoucherDeductMapper;

    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private OrderVoucherDeductMapper orderVoucherDeductMapper;
    @Autowired
    private RedisScript<Long> redisScript;


    public Boolean calculateDiscountAmount(VoucherDeductDTO param) {

        Boolean aBoolean = redisTemplate.hasKey("voucher:" + param.getVoucherNo());
        //如果已经使用过了，就不能再使用了
        //实现幂等
        if (aBoolean) {
            return true;
        }
        String uuid = UUID.randomUUID().toString();
        //查询订单的价格
        BigDecimal priceValue = tradeOrderService.query(param.getOrderId()).getPriceValue();
        //分布式锁
        //Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock:" + param.getOrderId(), uuid, 3, TimeUnit.SECONDS);
        while ((redisTemplate.opsForValue().setIfAbsent("lock:" + param.getOrderId(), uuid, 3, TimeUnit.SECONDS))) {
            KscVoucherDeduct kscVoucherDeduct = new KscVoucherDeduct();
            //设置订单ID
            kscVoucherDeduct.setOrderId(param.getOrderId());
            //设置优惠券ID
            kscVoucherDeduct.setVoucherNo(param.getVoucherNo());
            //设置抵扣券金额
            kscVoucherDeduct.setAmount(param.getAmount());
            //设置订单抵扣券前金额
            BigDecimal bigDecimal = orderVoucherDeductMapper.selectVoucherDeductAmount(param.getOrderId());
            System.out.println("bigDecimal = " + bigDecimal);
            System.out.println("priceValue = " + priceValue);
            if (bigDecimal == null) {
                bigDecimal = BigDecimal.ZERO;
            }
            if (bigDecimal.compareTo(priceValue) > 0) {
                bigDecimal = priceValue;
            }
            kscVoucherDeduct.setBeforeDeductAmount(priceValue.subtract(bigDecimal));
            //设置订单抵扣券后金额
            if (kscVoucherDeduct.getBeforeDeductAmount().compareTo(param.getAmount()) < 0) {
                kscVoucherDeduct.setAfterDeductAmount(BigDecimal.ZERO);
            } else {
                kscVoucherDeduct.setAfterDeductAmount(kscVoucherDeduct.getBeforeDeductAmount().subtract(param.getAmount()));
            }
            //插入数据库
            int insert = kscVoucherDeductMapper.insert(kscVoucherDeduct);
            if (insert != 1) {
                //释放锁
                redisTemplate.execute(redisScript, Arrays.asList("lock:" + param.getOrderId()), uuid);
                return false;
            }

            redisTemplate.opsForValue().set("voucher:" + param.getVoucherNo(), kscVoucherDeduct);
            //释放锁
            redisTemplate.execute(redisScript, Arrays.asList("lock:" + param.getOrderId()), uuid);
            return true;
        }
        return true;

    }
}
