package com.ilife.musicservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class sing {
    @Id
    @Column(name = "SINGID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long singid;


    @Column(name = "M_ID")
    private Long mid;

    @Column(name = "S_ID")
    private Long sid;

    @Column(name = "SNAME")
    private String sname;
}
