import React, { Component } from 'react'
import axios from 'axios';
import {Button,Dropdown,Menu,Radio} from 'antd';
import ZhihuActivity from '../../zhihu/ZhihuActivity';
import 'antd/dist/antd.css';


export default class zhihuBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      code: "",
      picBase64: "",
      needCaptcha: false,
      loginSuccess: false,
      activities: [],
      indeterminate: true,
      checkAll: false,
      userinfo: null,
      wordCloudReady:false,
      radioValue: 1,
      wordCloud: "",
      wordCloudReady: false,
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
  onRadioChange = e => {
    console.log('radio checked', e.target.value);
    this.setState({
      value: e.target.value,
    });
  };
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
  server_ip = "http://18.166.111.161:8090"
  word_cloud_server = "http://18.162.53.235:8103"
  fetchWordCloud = (username) =>{
    let type = ""
    switch (this.state.radioValue) {
      case 1:
        type = "ANSWER"
        break
      case 2:
        type = "QUESTION"
        break
      case 3:
        type = "ARTICLE"
        break
      case 4:
        type = ""
        break
    }
    const config = {
      method: 'get',
      url: this.word_cloud_server + '/word_cloud',
      headers: {
        'Content-Type': 'application/json'
      },
      params: {
        username: username,
        type: type,
      },
      withCredentials:true
    }
    axios(config)
        .then(response => {
          console.log("qrCorde", response.data)
          this.setState({
            wordCloud:`data:image/png;base64,${response.data}`,
            wordCloudReady : true
          })
        })
  }
  fetchUserinfo = (username) => {
    const config = {
      method: 'get',
      url: this.server_ip + '/user',
      headers: {
        'Content-Type': 'application/json'
      },
      params:{
        username: username,
      },
      withCredentials:true
    };
    axios(config)
        .then(response => {
          console.log("userinfo",response)
        })
  }
  updateUserActivities = (username) => {
    axios.post(this.server_ip + "/updateActivities?username=" + this.state.username,{
      withCredentials:true
    }).then(function (response) {
          console.log(response.data);
        })
  }
  async login() {
    var data;
    var config = {
      method: 'post',
      url: this.server_ip + '/login',
      headers: {
        'Content-Type': 'application/json'
      },
      data:
      {
        password: this.state.password,
        username: this.state.username,
      },
      withCredentials:true
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

    if (data === undefined) return;

    if (data.data === "Login successfully!") {
      var activities_data;
      await axios.get( this.server_ip + "/activity/all?username=" + this.state.username,{
        withCredentials:true
      })
        .then(function (response) {
          console.log(response);
          activities_data = response.data;
        })
      this.setState({
        activities: activities_data,
        loginSuccess: true
      })
    }
    else
      this.setState({
        needCaptcha: true,
        picBase64: `data:image/png;base64,${data.data}`,
      });
  }

  async loginTwice() {
    var config = {
      method: 'post',
      url: this.server_ip + '/login',
      headers: {
        'Content-Type': 'application/json'
      },
      data:
      {
        password: this.state.password,
        username: this.state.username,
        captcha: this.state.code
      },
      withCredentials:true
    };


    await axios(config)
      .then(function (response) {
        console.log(response);
      })
    console.log("done");
    /*
    await axios.post("http://18.162.168.229:8090/updateActivities?username=" + this.state.username)
      .then(function (response) {
        console.log(response);
      })
    */
    var activities_data;
    await axios.get(this.server_ip + "/activity/all?username=" + this.state.username,{

      withCredentials:true
    })
      .then(function (response) {
        console.log(response);
        activities_data = response.data;
      })
    this.setState({
      activities: activities_data,
      loginSuccess: true,
    })
  }

  render() {
    const { activities,loginSuccess,indeterminate,checkedList,checkAll,username,wordCloudReady,wordCloud} = this.state;
    return (
      <div className="content-wrapper">
        <section className="content">
          <div className="row">
            <div className="col-md-9">

              {loginSuccess ?
                  <div className="box box-primary">
                    <div className="box-header with-border">
                      <h3 className="box-title">登录成功</h3>
                    </div>
                    <Button onClick={()=>this.update()}>>更新数据</Button>
                  </div> :

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
                                 onChange={(val) => this.nameOnChange(val)}/>
                        </div>
                        <div className="form-group">
                          <label htmlFor="exampleInputPassword1">密码</label>
                          <input type="password" className="form-control" id="exampleInputPassword1"
                                 placeholder="Password"
                                 onChange={(val) => this.psdOnChange(val)}/>
                        </div>
                      </div>
                    </form>
                    {/* /.box-body */}
                    <div className="box-footer">
                      <button id="submit1" className="btn btn-primary" onClick={this.login}>Submit1</button>
                    </div>
                  </div>
              }
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
                    <button id="submit2" className="btn btn-primary" onClick={this.loginTwice}>Submit2</button>
                  </div>
                </div>
              </div>
              : null}
          </div>
          <div className="row">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">知乎浏览数据</h3>
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
          {
            loginSuccess ?
            <div className="row">
              <div className="col-xs-12">
                <div className="box">
                  <div className="box-header">
                    <h3 className="box-title">知乎浏览关键词</h3>
                  </div>
                  <Radio.Group onChange={this.onChange} value={this.state.value}>
                    <Radio value={1}>回答</Radio>
                    <Radio value={2}>提问</Radio>
                    <Radio value={3}>文章</Radio>
                    <Radio value={4}>全部</Radio>
                  </Radio.Group>
                  <Button
                      onClick={()=>this.fetchWordCloud(username)}
                  > 生成我的词云报告 </Button>
                </div>
              </div>
            </div> : null
          }
          {
            wordCloudReady ? <img src={wordCloud} className="img-square" alt="词云"/> : null
          }
        </section>
      </div >
    )
  }
}
