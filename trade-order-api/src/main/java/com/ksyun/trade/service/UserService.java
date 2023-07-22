package com.ksyun.trade.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksyun.trade.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${meta.url}")
    private String url;

    public User querybyid(Integer id) {

        User user = (User) redisTemplate.opsForValue().get("user:"+id);
        if (user != null) {
            System.out.println(user.toString());
            return user;
        }
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "online/user/" + id, String.class);
        String jsonResponse = forEntity.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            if (jsonNode.has("data")) {
                JsonNode userData = jsonNode.get("data");
                user = objectMapper.treeToValue(userData, User.class);
                redisTemplate.opsForValue().set("user:"+id, user);
                return user;
            } else {
                // 处理错误、数据未找到或其他情况
            }
        } catch (IOException e) {
            // 处理JSON解析错误
            e.printStackTrace();
        }

        return null;
    }
}