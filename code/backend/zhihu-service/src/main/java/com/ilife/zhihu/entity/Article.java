package com.ilife.zhihu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "article_id")
    Integer id;

    @Column(name = "title")
    String title;

    @Column(name = "author")
    String author;

    @Column(name = "content")
    String content;

    @Column(name = "excerpt")
    String excerpt;

    @Column(name = "column_name")
    String column_name;

    @Column(name = "create_time")
    Timestamp created_time;

    @Column(name = "update_time")
    Timestamp update_time;

    @Column(name = "image_url")
    String image_url;
    
}
