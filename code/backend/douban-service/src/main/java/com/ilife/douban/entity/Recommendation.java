package com.ilife.douban.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "RCMD")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {
    @Id
    String id;
    String actors_list_movie;

    String author_book;
    int hot_book;

    String introduction_book;
    String introduction_movie;

    String picture_book;

    String picture_movie;

    String price_book;
    double rate_book;
    double rate_movie;

    String title_book;

    String type_movie;

    String title_movie;

    String url_book;

    String url_movie;

}

