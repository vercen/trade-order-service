package com.ksyun.trade.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksyun.trade.pojo.Region;
import com.ksyun.trade.vo.RegionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegionListService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${meta.url}")
    private String url;

    private ConcurrentHashMap<String, RegionVo> regionList = null;

    private synchronized void init() {
        if (regionList == null) {
            regionList = new ConcurrentHashMap<>();
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "/online/region/list", String.class);
            String jsonResponse = forEntity.getBody();
            System.out.println("发送请求获取到的数据：");
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                if (jsonNode.has("data")) {
                    JsonNode regionListData = jsonNode.get("data");
                    for (JsonNode regionData : regionListData) {
                        Region region = objectMapper.treeToValue(regionData, Region.class);
                        RegionVo regionVo = new RegionVo();
                        regionVo.setCode(region.getCode());
                        regionVo.setName(region.getName());
                        regionList.put(region.getId(), regionVo);
                    }

                } else {
                    // 处理错误、数据未找到或其他情况
                }
            } catch (IOException e) {
                // 处理JSON解析错误
                e.printStackTrace();
            }
        }
    }

    public ConcurrentHashMap<String, RegionVo> getRegionList() {
        if (regionList == null) {
            init();
        }
        return regionList;
    }

    public RegionVo getRegionById(String id) {
        return getRegionList().get(id);
    }

}