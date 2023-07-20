package com.ksyun.trade.client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Slf4j
@Service
@Data
public class OrderClient {

    @Value("${actions}")
    private String actions;
    public String getRandomUrl() {
        String[] split = actions.split(",");
        int size = split.length;
        int index = new Random().nextInt(size); // 随机选择一个地址
        String url = split[index];
        return url;
    }

}
