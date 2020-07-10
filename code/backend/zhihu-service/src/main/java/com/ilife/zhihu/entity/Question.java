package com.ilife.zhihu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "question_id")
    Integer id;

    @Column(name = "title")
    String title;

    @Column(name = "content")
    String content;

    @Column(name = "excerpt")
    String excerpt;

    @Column(name = "create_time")
    Timestamp created_time;

    @Column(name = "update_time")
    Timestamp update_time;

    @Column(name = "answer_count")
    Integer answer_count;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    List<Answer> answerList;

}
