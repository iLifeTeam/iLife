package com.ilife.zhihu.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "activity")
public class Activity {

    @Id
    @Column(name = "id")
    String id;

    @Column(name = "target_id")
    String target_id;

    @JSONField(serialize=false)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name="zhihu_uid",referencedColumnName = "uid")
    User user;

    @Column(name = "type")
    String type;

    @Column(name = "action_text")
    String action_text;

    @Column(name = "create_time")
    Timestamp created_time;

}
