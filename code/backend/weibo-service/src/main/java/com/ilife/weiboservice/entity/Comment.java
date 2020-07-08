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
@Table(name = "COMMENT")
public class Comment {
    @Id
    @Column(name = "P_ID")
    private Integer pid;
    @Column(name = "U_ID")
    private Integer uid;
    @Column(name = "W_ID")
    private Integer wid;
    @Column(name = "TEXT")
    private String text;
    @Column(name = "DIRECTION")
    private String direction;
}
