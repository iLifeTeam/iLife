package com.ilife.douban.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieStats {
    Integer preferHot;
    float avgRanking;
    float maxRanking;
    Movie maxRankingMovie;
    float minRanking;
    Movie minRankingMovie;
    float avgHot;
    float maxHot;
    Movie maxHotMovie;
    float minHot;
    Movie minHotMovie;
    Integer allMovie;
    String preLanguage;
    String preType;
}
