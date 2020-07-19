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
@Table(name = "answer")
public class Answer {

    @Id
    @Column(name = "id")
    String id;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "question_id",referencedColumnName = "id")
    Question question;

    @Column(name = "author")
    String author;

    @Column(name = "content")
    String content;

    @Column(name = "excerpt")
    String excerpt;

    @Column(name = "create_time")
    Timestamp created_time;

    @Column(name = "update_time")
    Timestamp update_time;

    @Column(name = "voteup_count")
    Integer voteup_count;

    @Column(name = "comment_count")
    Integer comment_count;
}
