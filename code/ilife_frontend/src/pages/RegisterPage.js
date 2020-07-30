import React, { Component } from 'react'
import { Link } from "react-router-dom"
import { createBrowserHistory } from 'history'
import axios from 'axios'

const $ = require('jquery');

export default class RegisterPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      nickname: "",
      account: "",
      password: "",
      password2: "",
      email: "",
      check_psd: false,
      confirm: false,
    }
    this.handleNameChange = this.handleNameChange.bind(this);
    this.handleAccountChange = this.handleAccountChange.bind(this);
    this.handlePsdChange = this.handlePsdChange.bind(this);
    this.handlePsd2Change = this.handlePsd2Change.bind(this);
    this.handleEmailChange = this.handleEmailChange.bind(this);
    this.handleConfirmChange = this.handleConfirmChange.bind(this);
    this.register = this.register.bind(this);
  }
  componentDidMount() {

    const script = document.createElement("script");
    script.src = "dist/js/login.js";
    script.async = true;
    document.body.appendChild(script);

  }

  handleNameChange(val) {
    this.setState({
      nickname: val.target.value,
    })
  }

  handleAccountChange(val) {
    this.setState({
      account: val.target.value,
    })
  }

  handlePsdChange(val) {
    if (this.state.password2 === val.target.value) this.setState({
      check_psd: true,
      password: val.target.value
    });
    else this.setState({
      check_psd: false,
      password: val.target.value
    });
  }

  handlePsd2Change(val) {
    if (this.state.password === val.target.value && this.state.password !== "")
      this.setState({
        password2: val.target.value,
        check_psd: true,
      })
    else this.setState({
      password2: val.target.value,
      check_psd: false,
    })
  }

  handleEmailChange(val) {
    this.setState({
      email: val.target.value,
    })
  }

  handleConfirmChange() {
    //console.log(val); 
    console.log("111");
    this.setState({
      confirm: !this.state.confirm,
    })
  }

  async register() {
    // console.log("111");
    if (!this.state.check_psd) {
      alert("密码不匹配！");
      return;
    }


    var config = {
      method: 'post',
      url: 'http://18.166.111.161:8686/auth/register',
      headers: {
        'Content-Type': 'application/json',
        withCredentials: true
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
        alert("注册成功！请登录！");
        history.push("/login");
        window.location.reload();
      })
      .catch(function (error) {
        console.log(error);
        alert("注册失败！");
        return;
      });

    /*
  await axios.get("http://18.166.111.161:8686/auth/getByAccount?account=" + localStorage.getItem("username"),
    { headers: { withCredentials: true } })
    .then(function (response) {
      console.log(JSON.stringify(response.data));
    })
    .catch(function (error) {
      console.log(error);
    });
    */
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
                {this.state.nickname ? <span className="glyphicon glyphicon-leaf form-control-feedback" style={{ color: "#3C8DBC" }} /> :
                  <span className="glyphicon glyphicon-leaf form-control-feedback" />}
              </div>
              <div className="form-group has-feedback">
                <input type="text" id="accountinput" className="form-control" placeholder="Account" onChange={(val) => this.handleAccountChange(val)} />
                {this.state.account ? <span className="glyphicon glyphicon-user form-control-feedback" style={{ color: "#3C8DBC" }} /> :
                  <span className="glyphicon glyphicon-user form-control-feedback" />
                }
              </div>
              <div className="form-group has-feedback">
                <input type="email" id="emailinput" className="form-control" placeholder="Email" onChange={(val) => this.handleEmailChange(val)} />
                {this.state.email ? <span className="glyphicon glyphicon-envelope form-control-feedback" style={{ color: "#3C8DBC" }} /> :
                  <span className="glyphicon glyphicon-envelope form-control-feedback" />}
              </div>
              <div className="form-group has-feedback">
                <input type="password" id="psdinput" className="form-control" placeholder="Password" onChange={(val) => this.handlePsdChange(val)} />
                {this.state.password ? <span className="glyphicon glyphicon-lock form-control-feedback" style={{ color: "#3C8DBC" }} /> :
                  <span className="glyphicon glyphicon-lock form-control-feedback" />}
              </div>
              <div className="form-group has-feedback">
                <input type="password" id="psd2input" className="form-control" placeholder="Retype password" onChange={(val) => this.handlePsd2Change(val)} />
                {this.state.password2 && this.state.check_psd ? <span className="glyphicon glyphicon-ok-circle form-control-feedback" style={{ color: "#3C8DBC" }} /> : <span className="glyphicon glyphicon-ok-circle form-control-feedback" />}
              </div>
              <div className="row">
                <div className="col-xs-8">
                  <div className="checkbox icheck" >
                    <label >
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
