import React, { Component } from "react";
import { Link } from "react-router-dom";
import { createBrowserHistory } from "history";
import axios from "../axios";
import { message } from "antd";

var CryptoJS = require("crypto-js");
axios.defaults.withCredentials = true;
export default class LoginPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
    };
    this.handleNameChange = this.handleNameChange.bind(this);
    this.handlePsdChange = this.handlePsdChange.bind(this);
    this.login = this.login.bind(this);
  }

  componentDidMount() {
    const script = document.createElement("script");

    script.src = "dist/js/login.js";
    script.async = true;
    document.body.appendChild(script);
    this.loadPassword();
  }

  loadPassword() {
    let arr,
      reg = new RegExp("(^| )" + "username" + "=([^;]*)(;|$)");
    let username = "";
    if ((arr = document.cookie.match(reg))) {
      username = unescape(arr[2]);
    } else {
      username = null;
    }
    if (username) {
      const history = createBrowserHistory();
      history.push("/home/weibo");
      window.location.reload();
    }

    reg = new RegExp("(^| )" + "accountInfo" + "=([^;]*)(;|$)");
    let accountInfo = "";
    if ((arr = document.cookie.match(reg))) {
      accountInfo = unescape(arr[2]);
    } else {
      accountInfo = null;
    }

    if (Boolean(accountInfo) === false) {
      return false;
    } else {
      let userName = "";
      let passWord = "";
      //console.log(accountInfo);
      var bytes = CryptoJS.AES.decrypt(accountInfo, "secretilifeteam");
      var originalText = bytes.toString(CryptoJS.enc.Utf8);
      //console.log(originalText);
      let i = new Array();
      i = originalText.split("&");
      userName = i[0];
      passWord = i[1];
      const $ = require("jquery");
      $("#checkinput")[0].checked = true;

      this.setState({
        username: userName,
        password: passWord,
      });
    }
  }

  handleNameChange(val) {
    this.setState({
      username: val.target.value,
    });
  }

  handlePsdChange(val) {
    this.setState({
      password: val.target.value,
    });
  }

  async login() {
    const $ = require("jquery");
    if ($("#checkinput")[0].checked) {
      //是否保存密码
      let accountInfo = this.state.username + "&" + this.state.password;
      // Encrypt
      var ciphertext = CryptoJS.AES.encrypt(
        accountInfo,
        "secretilifeteam"
      ).toString();
      //console.log(ciphertext); // 'my message'
      let Days = 2; //cookie保存时间
      let exp = new Date();
      exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
      document.cookie =
        "accountInfo" +
        "=" +
        escape(ciphertext) +
        ";expires=" +
        exp.toGMTString() +
        ";";
    } else {
      let exp = new Date();
      exp.setTime(exp.getTime() - 1);
      let accountInfo = document.cookie;
      var cookie_pos = accountInfo.indexOf("accountInfo");

      if (cookie_pos != -1) {
        document.cookie =
          "accountInfo" + "=" + " " + ";expires=" + exp.toGMTString();
      }
    }

    const { username, password } = this.state;
    let password_md5 = CryptoJS.MD5(
      username + "&" + password,
      "ilifeteam"
    ).toString();
    var config = {
      method: "post",
      url: "http://18.166.111.161:8686/login",
      headers: {
        withCredentials: true,
      },
      data: {
        account: username,
        password: password_md5,
      },
    };

    const history = createBrowserHistory();
    await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        if (response.data === "iLife login success") {
          let time = new Date();
          time.setTime(time.getTime() + 3000000);
          document.cookie =
            "username" +
            "=" +
            escape(username) +
            ";expires=" +
            time.toGMTString() +
            ";";
          history.push("/home/weibo");
          message.success("登录成功！");
          //console.log(document.cookie);
          window.location.reload();
        } else message.error("登录失败！");
        return;
      })
      .catch(function (error) {
        console.log(error);
        message.error("用户名或密码错误！");
        return;
      });
  }
  render() {
    return (
      <body className="hold-transition login-page kin-bg"  >
        <div className="kin-blue sidebar-mini">
          <div className="login-box ">
            <div className="login-logo">
              <a>
                <b>iLife</b>
              </a>
            </div>
            {/* /.login-logo */}
            <div className="login-box-body">
              <p className="login-box-msg">登录</p>
              <form action="../../index2.html" method="post">
                <div className="form-group has-feedback">
                  <input
                    id="nameinput"
                    type="email"
                    className="form-control"
                    placeholder="Account"
                    value={this.state.username}
                    onChange={(val) => this.handleNameChange(val)}
                  />
                  {this.state.username ? (
                    <span
                      className="glyphicon glyphicon-envelope form-control-feedback"
                      style={{ color: "#3C8DBC" }}
                    />
                  ) : (
                    <span className="glyphicon glyphicon-envelope form-control-feedback" />
                  )}
                </div>
                <div className="form-group has-feedback">
                  <input
                    id="psdinput"
                    type="password"
                    className="form-control"
                    placeholder="Password"
                    value={this.state.password}
                    onChange={(val) => this.handlePsdChange(val)}
                  />
                  {this.state.password ? (
                    <span
                      className="glyphicon glyphicon-lock form-control-feedback"
                      style={{ color: "#3C8DBC" }}
                    />
                  ) : (
                    <span className="glyphicon glyphicon-lock form-control-feedback" />
                  )}
                </div>
                <div className="row">
                  <div className="col-xs-8">
                    <div className="checkbox icheck">
                      <label>
                        <input id="checkinput" type="checkbox" /> Remember Me{" "}
                      </label>
                    </div>
                  </div>
                  {/* /.col */}
                  <div className="col-xs-4">
                    <p
                      onClick={this.login}
                      id="login"
                      className="btn btn-primary btn-block btn-flat"
                    >
                      登录
                    </p>
                  </div>
                  {/* /.col */}
                </div>
              </form>
              <br />
              <br />
              <Link to={"/register"} className="text-center">
                Register a new membership
              </Link>
            </div>
          </div>
        </div>
      </body>
    );
  }
}

/*
<Link to={"/login"}>I forgot my password</Link>
*/
