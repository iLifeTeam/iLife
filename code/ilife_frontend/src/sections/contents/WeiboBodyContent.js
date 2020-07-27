import React, { Component } from 'react'
import axios from 'axios';
import ZhihuActivity from '../../zhihu/ZhihuActivity';
export default class WeiboBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      code: "",
      picBase64: "",
      needCaptcha: false,
      activities: []
    }
    this.login = this.login.bind(this);
    this.loginTwice = this.loginTwice.bind(this);
  }
  componentDidMount() {
    const script = document.createElement("script");

    script.src = "../../dist/js/content.js";
    script.async = true;
    document.body.appendChild(script);

  }

  nameOnChange(val) {
    this.setState({
      username: val.target.value,
    })
  }
  codeOnChange(val) {
    this.setState({
      code: val.target.value,
    })
  }
  psdOnChange(val) {
    this.setState({
      password: val.target.value,
    })
  }

  async login() {
    var data;
    var config = {
      method: 'post',
      url: 'http://47.97.206.169:8090/login',
      headers: {
        'Content-Type': 'application/json'
      },
      data:
      {
        password: this.state.password,
        username: this.state.username,
      }
    };
    await axios(config)
      .then(function (response) {
        console.log(response);
        data = response;
      })
      .catch(function (error) {
        console.log(error.response);
        data = error.response;
      })

    if (data.data === "success") {
      var activities_data;
      await axios.get("http://47.97.206.169:8090/activity/all?username=" + this.state.username)
        .then(function (response) {
          console.log(response);
          activities_data = response.data;
        })
      this.setState({
        activities: activities_data
      })
    }
    else
      this.setState({
        picBase64: `data:image/png;base64,${data.data}`,
        needCaptcha: true,
      });

  }

  async loginTwice() {
    var config = {
      method: 'post',
      url: 'http://47.97.206.169:8090/login',
      headers: {
        'Content-Type': 'application/json'
      },
      data:
      {
        password: this.state.password,
        username: this.state.username,
        captcha: this.state.code
      }
    };
    await axios(config)
      .then(function (response) {
        console.log(response);
      })
    console.log("done");
    await axios.post("http://47.97.206.169:8090/updateActivities?username=" + this.state.username)
      .then(function (response) {
        console.log(response);
      })
    var activities_data;
    await axios.get("http://47.97.206.169:8090/activity/all?username=" + this.state.username)
      .then(function (response) {
        console.log(response);
        activities_data = response.data;
      })
    this.setState({
      activities: activities_data
    })
  }



  render() {
    const { activities } = this.state;
    return (
      <div className="content-wrapper">
        <section className="content">
          <div className="row">
            <div className="col-md-9">
              <div className="box box-primary">
                <div className="box-header with-border">
                  <h3 className="box-title">登录</h3>
                </div>
                {/* form start */}
                <form role="form">
                  <div className="box-body">
                    <div className="form-group">
                      <label htmlFor="exampleInputEmail1">Email地址</label>
                      <input type="email" className="form-control" id="exampleInputEmail1" placeholder="Enter email"
                        onChange={(val) => this.nameOnChange(val)} />
                    </div>
                    <div className="form-group">
                      <label htmlFor="exampleInputPassword1">密码</label>
                      <input type="password" className="form-control" id="exampleInputPassword1" placeholder="Password"
                        onChange={(val) => this.psdOnChange(val)} />
                    </div>
                  </div>
                </form>
                {/* /.box-body */}
                <div className="box-footer">
                  <button className="btn btn-primary" onClick={this.login}>Submit1</button>
                </div>
              </div>
            </div>
            {this.state.needCaptcha ?
              <div className="col-md-3" >
                <div className="box box-primary">
                  <div className="box-header with-border">
                    <h3 className="box-title">验证码</h3>
                  </div>
                  <img src={this.state.picBase64} className="img-square" alt="验证码" />
                  <form role="form">
                    <div className="box-body">
                      <div className="form-group">
                        <label htmlFor="exampleInputCode1">验证码</label>
                        <input className="form-control" id="exampleInputCode1"
                          onChange={(val) => this.codeOnChange(val)} />
                      </div>
                    </div>
                  </form>
                  <div className="box-footer">
                    <button className="btn btn-primary" onClick={this.loginTwice}>Submit2</button>
                  </div>
                </div>
              </div>
              : null}
          </div>
          <div className="row">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">Data Table With Full Features</h3>
                </div>
                <div className="box-body">
                  <table id="example1" className="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>action_text</th>
                        <th>question</th>
                        <th>answer</th>
                        <th>target_id</th>
                        <th>created_time</th>
                      </tr>
                    </thead>
                    <tbody>
                      {activities.map((activity, index) => (
                        <ZhihuActivity
                          key={index}
                          action_text={activity.action_text}
                          created_time={activity.created_time}
                          id={activity.id}
                          target_id={activity.target_id}
                          type={activity.type}
                        ></ZhihuActivity>
                      ))}

                    </tbody>
                    <tfoot>
                      <tr>
                        <th>action_text</th>
                        <th>question</th>
                        <th>answer</th>
                        <th>target_id</th>
                        <th>created_time</th>
                      </tr>
                    </tfoot>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div >
    )
  }
}
