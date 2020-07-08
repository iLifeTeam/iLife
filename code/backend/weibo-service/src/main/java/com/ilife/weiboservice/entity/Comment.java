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
    private Integer p_id;
    @Column(name = "U_ID")
    private Integer u_id;
    @Column(name = "W_ID")
    private Integer w_id;
    @Column(name = "TEXT")
    private String text;
    @Column(name = "DIRECTION")
    private String direction;
}
