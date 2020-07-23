package com.ilife.douban.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "MOVIE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "LANGUAGE")
    private String language;
    @Column(name = "RANKING")
    private float ranking;
    @Column(name = "HOT")
    private Integer hot;
}