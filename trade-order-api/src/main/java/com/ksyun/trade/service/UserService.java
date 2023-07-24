package com.ksyun.trade.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksyun.trade.pojo.User;
import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@Slf4j
public class UserService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${meta.url}")
    private String url;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public UserService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public User querybyid(Integer id) {
        User user = (User) redisTemplate.opsForValue().get("user:" + id);
        if (user != null) {
            System.out.println(user.toString());
            return user;
        }

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "/online/user/" + id, String.class);
        String jsonResponse = forEntity.getBody();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            if (jsonNode.has("data")) {
                JsonNode userData = jsonNode.get("data");
                user = objectMapper.treeToValue(userData, User.class);
                redisTemplate.opsForValue().set("user:" + id, user);
                return user;
            } else {
                log.error("查询用户信息失败，用户ID：{}", id);
            }
        } catch (IOException e) {
            // 处理JSON解析错误
            e.printStackTrace();
        }
        return null;
    }
}
