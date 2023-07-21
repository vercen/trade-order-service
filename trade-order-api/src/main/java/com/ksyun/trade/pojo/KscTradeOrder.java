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

    /**
    * 
    */
    private void setId(Integer id){
    this.id = id;
    }

    /**
    * 
    */
    private void setUserId(Integer userId){
    this.userId = userId;
    }

    /**
    * 
    */
    private void setRegionId(Integer regionId){
    this.regionId = regionId;
    }

    /**
    * 
    */
    private void setProductId(Integer productId){
    this.productId = productId;
    }

    /**
    * 
    */
    private void setPriceValue(BigDecimal priceValue){
    this.priceValue = priceValue;
    }

    /**
    * 
    */
    private void setCreateTime(Date createTime){
    this.createTime = createTime;
    }


    /**
    * 
    */
    private Integer getId(){
    return this.id;
    }

    /**
    * 
    */
    private Integer getUserId(){
    return this.userId;
    }

    /**
    * 
    */
    private Integer getRegionId(){
    return this.regionId;
    }

    /**
    * 
    */
    private Integer getProductId(){
    return this.productId;
    }

    /**
    * 
    */
    private BigDecimal getPriceValue(){
    return this.priceValue;
    }

    /**
    * 
    */
    private Date getCreateTime(){
    return this.createTime;
    }

}
