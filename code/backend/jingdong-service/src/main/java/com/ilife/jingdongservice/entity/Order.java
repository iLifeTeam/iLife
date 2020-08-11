package com.ilife.jingdongservice.entity;
/*
* this class is left unused now, since the logic of obtaining comment data
* has not been finished
**/
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`order`")
public class Order {

    @Id
    @Column(name = "`id`")
    @JSONField(name = "orderID")
    private Long id;

    @Column(name = "`date`")
    private Date date;

    @Column(name = "`total`")
    private Double total;

    @Column(name = "`shop`")
    private String shop;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "`uid`",referencedColumnName = "`uid`")
    @JSONField(deserialize = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JSONField(deserialize = false)
    private List<Item> items;
}
