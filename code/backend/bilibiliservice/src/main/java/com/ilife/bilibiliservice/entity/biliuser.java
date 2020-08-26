package com.ilife.bilibiliservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class biliuser {
    @Id
    @Column(name = "mid")
    private Long mid;

    @Column(name = "uname")
    private String uname;
}
