package com.ksyun.trade.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* 
* @TableName ksc_voucher_deduct
*/
@Data
public class KscVoucherDeduct implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Integer id;
    /**
    * 订单ID
    */
    @NotNull(message="[订单ID]不能为空")
    private Integer orderId;
    /**
    * 优惠券ID
    */
    @NotBlank(message="[优惠券ID]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @Length(max= 100,message="编码长度不能超过100")
    private String voucherNo;
    /**
    * 抵扣券金额
    */
    @NotNull(message="[抵扣券金额]不能为空")
    private BigDecimal amount;
    /**
    * 订单抵扣券前金额
    */
    @NotNull(message="[订单抵扣券前金额]不能为空")
    private BigDecimal beforeDeductAmount;
    /**
    * 订单抵扣券后金额
    */
    @NotNull(message="[订单抵扣券后金额]不能为空")
    private BigDecimal afterDeductAmount;
    /**
    * 开始时间
    */
    @NotNull(message="[开始时间]不能为空")
    private Date createTime;
    /**
    * 更新时间
    */
    @NotNull(message="[更新时间]不能为空")
    private Date updateTime;

}
