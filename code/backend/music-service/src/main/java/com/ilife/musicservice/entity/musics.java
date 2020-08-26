package com.ilife.musicservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class musics {
    @Id
    @Column(name = "M_ID")
    private Long mid;
    @Column(name = "MNAME")
    private String mname;
    @Column(name = "STYLE")
    private String style;
    @Column(name = "TIMES")
    private String times;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "M_ID")
    private List<sing> singers;
}
