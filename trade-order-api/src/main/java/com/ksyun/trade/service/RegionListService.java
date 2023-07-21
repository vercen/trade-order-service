package com.ksyun.trade.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksyun.trade.pojo.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;

@Service
public class RegionListService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${meta.url}")
    private String url;

    public static HashMap<String, Region> regionList;

    @PostConstruct
    public void init() {
        regionList = new HashMap<>();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "/online/region/list", String.class);
        String jsonResponse = forEntity.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            if (jsonNode.has("data")) {
                JsonNode regionListData = jsonNode.get("data");
                for (JsonNode regionData : regionListData) {
                    Region region = objectMapper.treeToValue(regionData, Region.class);
                    regionList.put(region.getId(), region);
                }

            } else {
                // 处理错误、数据未找到或其他情况
            }
        } catch (IOException e) {
            // 处理JSON解析错误
            e.printStackTrace();
        }
    }

    public HashMap<String, Region> getRegionList() {
        return regionList;
    }
}