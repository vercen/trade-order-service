package com.ksyun.trade.rest;

/**
 * @author ksc
 */
public class RestConsts {

    /**
     * msg 默认成功信息
     */
    public static final String SUCCESS_MESSAGE = "ok";

    public static final String ERROR_MESSAGE = "error";

    //限流了
    public static final String LIMIT_MESSAGE = "对不起,系统压力过大,请稍后再试!";

    /**
     * op 默认成功操作
     */
    public static final int DEFAULT_SUCCESS_CODE = 200;

    //限流了
    public static final int DEFAULT_LIMIT_CODE = 429;
}
