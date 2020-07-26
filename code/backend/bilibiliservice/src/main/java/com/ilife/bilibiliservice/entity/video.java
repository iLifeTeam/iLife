package com.ilife.bilibiliservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.IdClass;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(videokey.class)
public class video extends videokey{
    @Column(name = "auther_name")
    private String auther_name;
    @Column(name = "auther_id")
    private Long auther_id;
    @Column(name = "tag_name")
    private String tag_name;
    @Column(name = "title")
    private String title;
}
