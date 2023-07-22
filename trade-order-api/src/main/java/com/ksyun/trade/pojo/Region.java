package com.ksyun.trade.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Region {
    private String id;
    private String code;
    private String name;
    private int status;
}
