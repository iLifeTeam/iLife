package com.ilife.bilibiliservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class history {
    @Id
    @Column(name = "HISID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hisid;

    @OneToOne
    @JoinColumnsOrFormulas(value= {@JoinColumnOrFormula(column=@JoinColumn(name="oid",referencedColumnName="oid")),
            @JoinColumnOrFormula(column=@JoinColumn(name ="type", referencedColumnName = "type"))})
    private video video;

    @Column(name="mid")
    private Long mid;

    @Column(name="is_fav")
    private boolean is_fav;
}
