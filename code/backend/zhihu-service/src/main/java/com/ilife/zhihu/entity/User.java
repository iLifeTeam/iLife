package com.ilife.zhihu.entity;


import com.alibaba.fastjson.annotation.JSONField;
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
@Table(name = "user")
public class User {

    @Id
    @Column(name = "uid")
    String uid;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "answer_count")
    Integer answerCount;

    @Column(name = "voteup_count")
    Integer voteupCount;

    @Column(name = "thanked_count")
    Integer thankedCount;

    @Column(name = "gender")
    Integer gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Activity> activities;
}
