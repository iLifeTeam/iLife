package com.ilife.douban.entity;

import lombok.Data;

import java.io.Serializable;

@Data

public class MovieKeys implements Serializable {
    private String id;
    private String name;
}
