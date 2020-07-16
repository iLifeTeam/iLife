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

  public Users(String _nickname, String _account, String _password, String _email) {
    wyyid = weibid = 0;
    zhid = "0";
    nickname = _nickname;
    account = _account;
    password = _password;
    email = _email;
  }



}
