package com.ilife.douban.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookStats {
    float avgPrice;
    float maxPrice;
    Book maxPriceBook;
    float avgRanking;
    float maxRanking;
    Book maxRankingBook;
    float avgHot;
    float maxHot;
    Book maxHotBook;
    float minHot;
    Book minHotBook;
    Integer allBook;
    String preAuthor;
}
