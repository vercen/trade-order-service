package com.ksyun.trade.controller.online;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/online")
public class LbController {
    private static final List<String> serverList = Arrays.asList(
            "http://campus.query1.ksyun.com:8089/online/trade_order/2",
            "http://campus.query2.ksyun.com:8089/online/trade_order/2",
            "http://campus.query3.ksyun.com:8089/online/trade_order/2",
            "http://campus.query4.ksyun.com:8089/online/trade_order/2",
            "http://campus.query5.ksyun.com:8089/online/trade_order/2");

    private static final List<Integer> weightList = Arrays.asList(1, 2, 3, 4, 5);

    private static int index = 0;

    @GetMapping("/random")
    public String random() {
        Random random = new Random();
        int index = random.nextInt(serverList.size());
        return serverList.get(index);
    }

    @GetMapping("/hash")
    public String hash(HttpServletRequest request) {
        String ip = getIpAddr(request);
        int hashCode = Math.abs(ip.hashCode());
        int index = hashCode % serverList.size();
        return serverList.get(index);
    }

    @GetMapping("/round")
    public String round() {
        if (index >= serverList.size()) {
            index = 0;
        }
        String server = serverList.get(index);
        index++;
        return server;
    }

    @GetMapping("/weight")
    public String weight() {
        int totalWeight = 0;
        for (int weight : weightList) {
            totalWeight += weight;
        }

        Random random = new Random();
        int randomWeight = random.nextInt(totalWeight);

        int index = 0;
        for (int i = 0; i < weightList.size(); i++) {
            randomWeight -= weightList.get(i);
            if (randomWeight < 0) {
                index = i;
                break;
            }
        }

        return serverList.get(index);
    }

    // 获取请求过来的IP
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
