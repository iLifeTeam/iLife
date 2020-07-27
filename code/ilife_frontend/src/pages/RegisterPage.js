import React, { Component } from 'react'
import { Link } from "react-router-dom"
import { createBrowserHistory } from 'history'
import axios from 'axios'

export default class RegisterPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      nickname: "",
      account: "",
      password: "",
      email: ""
    }
    this.handleNameChange = this.handleNameChange.bind(this);
    this.handleAccountChange = this.handleAccountChange.bind(this);
    this.handlePsdChange = this.handlePsdChange.bind(this);
    this.handleEmailChange = this.handleEmailChange.bind(this);
    this.register = this.register.bind(this);
  }
  componentDidMount() {

    const script = document.createElement("script");
    script.src = "dist/js/login.js";
    script.async = true;
    document.body.appendChild(script);

  }

  handleNameChange(val) {
    console.log(val.target.value)
    this.setState({
      nickname: val.target.value,
    })
  }

  handleAccountChange(val) {
    console.log(val.target.value)
    this.setState({
      account: val.target.value,
    })
  }

  handlePsdChange(val) {
    this.setState({
      password: val.target.value,
    })
  }

  handleEmailChange(val) {
    this.setState({
      email: val.target.value,
    })
  }

  async register() {
    // console.log("111");
    var config = {
      method: 'post',
      url: 'http://18.162.168.229:8686/auth/register',
      headers: {
        'Content-Type': 'application/json'
      },
      data: {
        "nickname": this.state.nickname,
        "account": this.state.account,
        "password": this.state.password,
        "email": this.state.email,
        "type": "ROLE_USER"
      }
    };

    const history = createBrowserHistory();

    await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        alert("注册成功！");
        localStorage.setItem("username", config.data.account);
        history.push("/home");
        window.location.reload();
      })
      .catch(function (error) {
        console.log(error);
        alert("用户名或密码错误！");
        return;
      });

  }

  render() {
    return (
      <body className="hold-transition register-page">
        <div className="register-box">
          <div className="register-logo">
            <a><b>iLife</b></a>
          </div>
          <div className="register-box-body">
            <p className="login-box-msg">Register a new membership</p>
            <form >
              <div className="form-group has-feedback">
                <input type="text" id="nameinput" className="form-control" placeholder="NickName" onChange={(val) => this.handleNameChange(val)} />
                <span className="glyphicon glyphicon-user form-control-feedback" />
              </div>
              <div className="form-group has-feedback">
                <input type="text" id="accountinput" className="form-control" placeholder="Account" onChange={(val) => this.handleAccountChange(val)} />
                <span className="glyphicon glyphicon-user form-control-feedback" />
              </div>
              <div className="form-group has-feedback">
                <input type="email" id="emailinput" className="form-control" placeholder="Email" onChange={(val) => this.handleEmailChange(val)} />
                <span className="glyphicon glyphicon-envelope form-control-feedback" />
              </div>
              <div className="form-group has-feedback">
                <input type="password" id="psdinput" className="form-control" placeholder="Password" onChange={(val) => this.handlePsdChange(val)} />
                <span className="glyphicon glyphicon-lock form-control-feedback" />
              </div>
              <div className="form-group has-feedback">
                <input type="password" id="psd2input" className="form-control" placeholder="Retype password" />
                <span className="glyphicon glyphicon-log-in form-control-feedback" />
              </div>
              <div className="row">
                <div className="col-xs-8">
                  <div className="checkbox icheck">
                    <label>
                      <input type="checkbox" /> I agree to the <a>terms</a>
                    </label>
                  </div>
                </div>
                {/* /.col */}
                <div className="col-xs-4">
                  <p id="register" onClick={this.register} className="btn btn-primary">注册</p>
                </div>
                {/* /.col */}
              </div>
            </form>
            <br />
            <br />
            <Link to={"/login"}><p className="text-center">I already have a membership</p></Link>
          </div>
        </div>
      </body>
    )
  }
}
