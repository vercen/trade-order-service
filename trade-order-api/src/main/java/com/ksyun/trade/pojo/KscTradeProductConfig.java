package com.ksyun.trade.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
* 
* @TableName ksc_trade_product_config
*/
@Data
public class KscTradeProductConfig implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @JsonIgnore
    private Integer id;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @Length(max= 100,message="编码长度不能超过100")
    private String itemNo;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @Length(max= 100,message="编码长度不能超过100")
    private String itemName;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 10,message="编码长度不能超过10")
    @Length(max= 10,message="编码长度不能超过10")
    private String unit;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Integer value;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @JsonIgnore
    private Integer orderId;

}
