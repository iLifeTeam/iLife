package com.ilife.douban.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "BOOK")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "AUTHOR")
    private String author;
    @Column(name = "PRICE")
    private String price;
    @Column(name = "RANKING")
    private float ranking;
    @Column(name = "HOT")
    private Integer hot;
}
