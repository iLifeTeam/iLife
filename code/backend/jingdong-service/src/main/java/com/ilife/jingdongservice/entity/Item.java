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

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    @JSONField(deserialize = false,serialize = false)
    private Order order;
}
