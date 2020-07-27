package com.ilife.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  @Column(name = "ID")
  private long id;
  @Column(name = "WYYID")
  private long wyyid;
  @Column(name = "BILIID")
  private long biliid;
  @Column(name = "TBID")
  private String tbid;
  @Column(name = "DOUBANID")
  private String doubanid;
  @Column(name = "WEIBID")
  private long weibid;
  @Column(name = "ZHID")
  private String zhid;
  @Column(name = "NICKNAME")
  private String nickname;
  @Column(name = "ACCOUNT")
  private String account;
  @Column(name = "PASSWORD")
  private String password;
  @Column(name = "EMAIL")
  private String email;
  @Column(name = "TYPE")
  private String type;

  public Users(String _nickname, String _account, String _password, String _email,String _type) {
    wyyid = weibid=biliid = 0;
    zhid = tbid= doubanid = "0";
    nickname = _nickname;
    account = _account;
    password = _password;
    email = _email;
    type=_type;
  }



}
