package com.ilife.alipayservice.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bill")
public class Bill {

    @Id
    @Column(name = "bid")
    String bid;

    @Column(name = "shop_bid")
    String shopBid;

    @Column(name = "product_info")
    String productInfo;

    @Column(name = "`time`")
    Timestamp time;

    @Column(name = "target_account")
    String targetAccount;

    @Column(name = "`in`")
    Float in;

    @Column(name = "`out`")
    Float out;

    @Column(name = "payment_approach")
    String paymentApproach;

    @Column(name = "comment")
    String comment;

    @Column(name = "uid")
    Integer uid;
}
