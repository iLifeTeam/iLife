import React, { Component } from 'react'
import { Link } from "react-router-dom"
import { createBrowserHistory } from 'history'
import axios from 'axios'


axios.defaults.withCredentials = true;
export default class LoginPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: ""
    }
    this.handleNameChange = this.handleNameChange.bind(this);
    this.handlePsdChange = this.handlePsdChange.bind(this);
    this.login = this.login.bind(this);
  }

  componentDidMount() {

    const script = document.createElement("script");

    script.src = "dist/js/login.js";
    script.async = true;
    document.body.appendChild(script);

  }

  handleNameChange(val) {
    this.setState({
      username: val.target.value,
    })
  }

  handlePsdChange(val) {
    this.setState({
      password: val.target.value,
    })
  }

  async login() {
    //console.log("111");
    var config = {
      method: 'post',
      url: 'http://18.162.168.229:8686/login',
      headers: {
        withCredentials: true
      },
      data: {
        "account": this.state.username,
        "password": this.state.password
      }
    }

    const history = createBrowserHistory();
    await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        if (response.data === "iLife login success") {
          alert("登录成功！");
          localStorage.setItem("username", config.data.account);
          history.push("/home");
          window.location.reload();
        }
        else alert("登录失败！")
        return;
      })
      .catch(function (error) {
        console.log(error);
        alert("用户名或密码错误！");
        return;
      });

  }
  render() {
    return (
      <body className="hold-transition login-page">
        <div className="kin-blue sidebar-mini">
          <div className="login-box ">
            <div className="login-logo">
              <a><b>iLife</b></a>
            </div>
            {/* /.login-logo */}
            <div className="login-box-body">
              <p className="login-box-msg">登录</p>
              <form action="../../index2.html" method="post">
                <div className="form-group has-feedback">
                  <input id="nameinput" type="email" className="form-control" placeholder="Account" onChange={(val) => this.handleNameChange(val)} />
                  <span className="glyphicon glyphicon-envelope form-control-feedback" />
                </div>
                <div className="form-group has-feedback">
                  <input id="psdinput" type="password" className="form-control" placeholder="Password" onChange={(val) => this.handlePsdChange(val)} />
                  <span className="glyphicon glyphicon-lock form-control-feedback" />
                </div>
                <div className="row">
                  <div className="col-xs-8">
                    <div className="checkbox icheck">
                      <label>
                        <input type="checkbox" /> Remember Me
            </label>
                    </div>
                  </div>
                  {/* /.col */}
                  <div className="col-xs-4">
                    <p onClick={this.login} id="login" className="btn btn-primary btn-block btn-flat">登录</p>
                  </div>
                  {/* /.col */}
                </div>
              </form>
              <br />
              <br />
              {/* /.social-auth-links */}
              <Link to={"/login"}>I forgot my password</Link><br />
              <Link to={"/register"} className="text-center">Register a new membership</Link>
            </div>
          </div>
        </div>
      </body>
    )
  }
}