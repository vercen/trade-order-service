package com.ksyun.trade.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author vercen
 * @version 1.0
 * @date 2023/4/21 22:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private String requestId;
    private Object obj;

    //成功后,携带数据
    public static RespBean success(String requestId,Object data){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),
                RespBeanEnum.SUCCESS.getMessage(),requestId,data);
    }
    public static RespBean success(String requestId){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),
                RespBeanEnum.SUCCESS.getMessage(),requestId,null);
    }

    //失败,不带数据
    public static RespBean error(RespBeanEnum respBeanEnum,String requestId){
        return new RespBean(respBeanEnum.getCode(),
                respBeanEnum.getMessage(),requestId,null);
    }
    public static RespBean error(RespBeanEnum respBeanEnum,String requestId,Object obj){
        return new RespBean(respBeanEnum.getCode(),
                respBeanEnum.getMessage(),requestId,obj);
    }



}