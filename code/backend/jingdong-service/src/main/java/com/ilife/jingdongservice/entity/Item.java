package com.ilife.jingdongservice.entity;
/*
* this class is left unused now, since the logic of obtaining comment data
* has not been finished
**/
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @JSONField(deserialize = false)
    private Integer id;

    @Column(name = "`product`")

    private String product;

    @Column(name = "`price`")
    private Double price;

    @Column(name = "`number`")
    private Integer number;

    @Column(name = "`img_url`")
    private String imgUrl;

    @Column(name = "`cate1`")
    private String firstCategory;
    @Column(name = "`cate2`")
    private String secondCategory;
    @Column(name = "`cate3`")
    private String thirdCategory;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    @JSONField(serialize = false)
    private Order order;
}
