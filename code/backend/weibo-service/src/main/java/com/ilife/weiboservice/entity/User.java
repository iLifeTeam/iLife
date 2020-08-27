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
    @Column(name = "ID")
    private Long id;
    @Column(name = "NICKNAME")
    private String nickname;
    @Column(name = "FOLLOWERS")
    private Integer followers;
    @Column(name = "FOLLOWING")
    private Integer following;
    @Column(name = "WEIBO_NUM")
    private Integer weibo_num;
    @Column(name = "BIRTHDAY")
    private String birthday;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "EDUCATION")
    private String education;
    @Column(name = "WORK")
    private String work;
    @Column(name = "AVATAR")
    private String avatar;
}
