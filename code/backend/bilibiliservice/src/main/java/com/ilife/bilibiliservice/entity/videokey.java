package com.ilife.bilibiliservice.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
public class videokey implements Serializable {
    @Id
    @Column(name = "oid")
    private Long oid;
    @Id
    @Column(name = "type")
    private String type;

}
