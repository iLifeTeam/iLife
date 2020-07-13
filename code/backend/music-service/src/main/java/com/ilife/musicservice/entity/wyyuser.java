package com.ilife.musicservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "hibernateLazyInitializer","handler"})
public class wyyuser {
    @Id
    @Column(name = "HISID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hisid;
    @Column(name = "WYYID")
    private Long wyyid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "M_ID",referencedColumnName = "M_ID",insertable = false,updatable = false)
    private musics musics;

    @Column(name = "PLAYCOUNT")
    private Integer playcount;
    @Column(name = "SCORE")
    private Integer score;
}
