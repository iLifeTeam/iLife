package com.ilife.weiboservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {
    private Float avgUp;
    private Integer maxUp;
    private Integer allUp;
    private String maxUpWb;

    private Float avgRt;
    private Integer maxRt;
    private Integer allRt;
    private String maxRtWb;

    private Float avgCm;
    private Integer maxCm;
    private Integer allCm;
    private String maxCmWb;

    private Float avgWb;
    private Integer maxWb;
    private Integer allWb;
    private Timestamp maxDate;

}
