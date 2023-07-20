package com.ksyun.trade.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author vercen
 * @version 1.0
 * @date 2023/4/21 22:17
 */
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    //通用
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),

    // 客户端错误
    BAD_REQUEST(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),

    //登录模块
    LOGIN_ERROR(500210, "用户名或者密码错误"),
    MOBILE_ERROR(500211, "手机号码格式不正确"),
    BING_ERROR(500212, "参数绑定异常"),
    MOBILE_NOT_EXIST(500213, "手机号码不存在"),
    PASSWORD_UPDATE_FAIL(500214, "更新密码失败"),
    CAPTCHA_ERROR(500202,"验证码错误"),
    //秒杀模块
    REQUEST_ILLEGAL(500503, "请求非法"),
    SESSION_ERROR(500502, "用户信息有误"),
    SEC_KILL_WAIT(500504, "排队中...."),
    ENTRY_STOCK(500500, "库存不足"),
    REPEATE_ERROR(500501, "该商品每人限购一件"),
    ACCESS_LIMIT_REACH(500505,"你在刷接口");

    private final Integer code;
    private final String message;
}
