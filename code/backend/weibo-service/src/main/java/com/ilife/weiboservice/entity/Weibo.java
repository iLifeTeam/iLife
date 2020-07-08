package com.ilife.weiboservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEIBO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weibo {

    @Id
    @Column(name = "W_ID")
    private Integer wid;
    @Column(name = "U_ID")
    private Integer uid;
    @Column(name = "TEXT")
    private String text;
    @Column(name = "REPOSTS_COUNT")
    private Integer reposts_count;
    @Column(name = "COMMENTS_COUNT")
    private Integer comments_count;
    @Column(name = "ATTITUDES_COUNT")
    private Integer attitudes_count;
    @Column(name = "READ_COUNT")
    private Integer read_count;
    @Column(name = "PUBLISH_TIME")
    private Integer publish_time;
}
