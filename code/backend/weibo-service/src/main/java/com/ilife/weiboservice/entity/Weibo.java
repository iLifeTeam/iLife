package com.ilife.weiboservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "WEIBO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weibo {

    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "USER_ID")
    private Long uid;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "PUBLISH_PLACE")
    private String publish_place;
    @Column(name = "RETWEET_NUM")
    private Integer retweet_num;
    @Column(name = "COMMENT_NUM")
    private Integer comment_count;
    @Column(name = "UP_NUM")
    private Integer up_num;
    @Column(name = "PUBLISH_TIME")
    private Date publish_time;
}
