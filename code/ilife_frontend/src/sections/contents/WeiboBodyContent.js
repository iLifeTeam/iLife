import React, { Component } from 'react'
import axios from 'axios';
import ZhihuActivity from '../../zhihu/ZhihuActivity';
import { createBrowserHistory } from 'history'
import WeiboInfo from '../../weibo/WeiboInfo';

export default class WeiboBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      weiboId: null,
      username: "",
      password: "",
      activities: null
    }
    //this.login = this.login.bind(this);
    this.getWeiboId = this.getWeiboId.bind(this);
  }
  componentDidMount() {
    const username = localStorage.getItem("username");

    if (username === null || username === undefined) {
      const history = createBrowserHistory();
      history.push("/login");
      window.location.reload();
    }


    const script = document.createElement("script");

    script.src = "../../dist/js/content.js";
    script.async = true;
    document.body.appendChild(script);
    this.getWeiboId(username);
  }

  async getWeiboId(username) {
    var config = {
      method: 'get',
      url: 'http://18.166.111.161:8686/auth/getByAccount?account=' + username,
      headers: {
        withCredentials: true
      }
    };

    const weiboId = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        return response.data.weibid;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });

    console.log(weiboId);
    var config = {
      method: 'get',
      url: 'http://121.36.196.234:8787/weibo/getWeibos?userId=' + weiboId,
      headers: {
        withCredentials: true
      }
    };

    const activities = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });

    if (activities) {
      this.setState({
        activities,
        weiboId
      })
    }
  }

  nameOnChange(val) {
    this.setState({
      username: val.target.value,
    })
  }

  psdOnChange(val) {
    this.setState({
      password: val.target.value,
    })
  }
  /*
    async login() {
      var data;
      var config = {
        method: 'post',
        url: 'http://121.36.196.234:8090/login',
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
        await axios.get("http://121.36.196.234:8090/activity/all?username=" + this.state.username)
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
  */
  render() {
    const { activities } = this.state;
    return (
      <div className="content-wrapper">
        <section className="content">{/*
          <div className="row">
            <div className="col-md-9">
              <div className="box box-primary">
                <div className="box-header with-border">
                  <h3 className="box-title">登录</h3>
                </div>
                {/* form start *}
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
          {/* /.box-body *}
          <div className="box-footer">
            <button className="btn btn-primary" onClick={this.login}>Submit1</button>
          </div>
              </div>
      </div>
          </div >*/}
          < div className="row" >
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">用户{this.state.weiboId}的微博数据</h3>
                </div>
                <div className="box-body">
                  {<WeiboInfo activities={activities} />}
                </div>
              </div>
            </div>
          </div >
        </section >
      </div >
    )
  }
}
