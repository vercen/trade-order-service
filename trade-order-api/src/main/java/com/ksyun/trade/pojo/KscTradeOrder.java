package com.ksyun.trade.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* 
* @TableName ksc_trade_order
*/
@Data
public class KscTradeOrder implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Integer id;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Integer userId;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Integer regionId;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Integer productId;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private BigDecimal priceValue;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Date createTime;


}
