package com.ilife.weiboservice.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER")
public class User {
    @Id
    @Column(name = "U_ID")
    private Integer uid;
    @Column(name = "NICKNAME")
    private String nickname;
    @Column(name = "FOLLOWERS_COUNT")
    private Integer followers_count;
    @Column(name = "FOLLOWINGS_COUNT")
    private Integer followings_count;
    @Column(name = "WEIBO_COUNT")
    private Integer weibo_count;
    @Column(name = "FRIENDS_COUNT")
    private Integer friends_count;
    @Column(name = "REGISTER_TIME")
    private Integer register_time;
}
