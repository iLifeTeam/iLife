package com.ilife.douban.entity;

import lombok.Data;

import java.io.Serializable;

@Data

public class BookKeys implements Serializable {
    private String id;
    private String name;
}
