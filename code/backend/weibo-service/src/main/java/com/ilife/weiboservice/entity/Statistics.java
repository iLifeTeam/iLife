package com.ilife.weiboservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {
    private Integer avgUp;
    private Integer maxUp;
    private Integer allUp;
    private String maxUpWb;
    private Integer avgRt;
    private Integer maxRt;
    private Integer allRt;
    private String maxRtWb;
    private Integer avgCm;
    private Integer maxCm;
    private Integer allCm;
    private String maxCmWb;
    private Integer preTime;
    private Integer maxWb;
    private Date maxDate;

}
