package com.ksyun.trade.service;

import com.ksyun.trade.BaseSpringAllTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RegionListServiceTest extends BaseSpringAllTest {
    @Autowired
    private RegionListService regionListService;

    @Test
    public void getRegionList() {
        regionListService.getRegionList();
    }
}
