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
    private RedisScript<Long> redisScript;
    @Autowired
    private OrderVoucherDeductMapper orderVoucherDeductMapper;

    private static final String LOCK_PREFIX = "lock:";
    private static final long LOCK_EXPIRATION_TIME = 3; // 3 seconds

    public Boolean calculateDiscountAmount(VoucherDeductDTO param) {

        Boolean exists = redisTemplate.hasKey("voucher:" + param.getVoucherNo());
        // 如果已经使用过了，就不能再使用了，实现幂等
        if (exists) {
            return true;
        }

        String uuid = UUID.randomUUID().toString();
        // 查询订单的价格
        BigDecimal priceValue = tradeOrderService.query(param.getOrderId()).getPriceValue();
        String lockKey = LOCK_PREFIX + param.getOrderId();

        // 分布式锁优化
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, LOCK_EXPIRATION_TIME, TimeUnit.SECONDS);
        if (lock != null && lock) {
            try {
                KscVoucherDeduct kscVoucherDeduct = new KscVoucherDeduct();
                // 设置订单ID
                kscVoucherDeduct.setOrderId(param.getOrderId());
                // 设置优惠券ID
                kscVoucherDeduct.setVoucherNo(param.getVoucherNo());
                // 设置抵扣券金额
                kscVoucherDeduct.setAmount(param.getAmount());
                // 设置订单抵扣券前金额
//                BigDecimal beforeDeductAmount = orderVoucherDeductMapper.selectVoucherDeductAmount(param.getOrderId());
//                if (beforeDeductAmount == null) {
//                    beforeDeductAmount = BigDecimal.ZERO;
//                }
//                if (beforeDeductAmount.compareTo(priceValue) > 0) {
//                    beforeDeductAmount = priceValue;
//                }
//                kscVoucherDeduct.setBeforeDeductAmount(priceValue.subtract(beforeDeductAmount));
                BigDecimal totalDeduction = orderVoucherDeductMapper.selectVoucherDeductAmount(param.getOrderId());
                if (totalDeduction == null) {
                    totalDeduction = BigDecimal.ZERO;
                }
                BigDecimal beforeDeductAmount = priceValue.subtract(totalDeduction).min(priceValue);
                BigDecimal afterDeductAmount = beforeDeductAmount.subtract(param.getAmount()).max(BigDecimal.ZERO);
                kscVoucherDeduct.setBeforeDeductAmount(beforeDeductAmount);
                kscVoucherDeduct.setAfterDeductAmount(afterDeductAmount);
                // 设置订单抵扣券后金额
//                if (kscVoucherDeduct.getBeforeDeductAmount().compareTo(param.getAmount()) < 0) {
//                    kscVoucherDeduct.setAfterDeductAmount(BigDecimal.ZERO);
//                } else {
//                    kscVoucherDeduct.setAfterDeductAmount(kscVoucherDeduct.getBeforeDeductAmount().subtract(param.getAmount()));
//                }
                // 插入数据库
                int insert = kscVoucherDeductMapper.insert(kscVoucherDeduct);
                if (insert != 1) {
                    //插入失败 释放锁
                    redisTemplate.execute(redisScript, Arrays.asList(lockKey), uuid);
                    return false;
                }
                redisTemplate.opsForValue().set("voucher:" + param.getVoucherNo(), kscVoucherDeduct);
            } finally {
                // 在finally块中释放锁，以防止异常情况下锁未释放
                redisTemplate.execute(redisScript, Arrays.asList(lockKey), uuid);
                return true;

            }
        }

        return true;
    }
}
