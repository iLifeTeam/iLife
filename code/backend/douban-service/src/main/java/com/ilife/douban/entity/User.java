package com.ilife.douban.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "MOVIE_WISH")
    private Integer MOVIE_WISH;
    @Column(name = "MOVIE_WATCHED")
    private Integer MOVIE_WATCHED;
    @Column(name = "BOOK_WISH")
    private Integer BOOK_WISH;
    @Column(name = "BOOK_READ")
    private Integer BOOK_READ;
    @Column(name = "COMMENT")
    private Integer COMMENT;
    @Column(name = "FOLLOWING")
    private Integer FOLLOWING;
    @Column(name = "FOLLOWER")
    private Integer FOLLOWER;
}