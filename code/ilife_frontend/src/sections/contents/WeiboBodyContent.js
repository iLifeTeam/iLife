import React, { Component } from 'react'
import axios from 'axios';
import ZhihuActivity from '../../zhihu/ZhihuActivity';
import { createBrowserHistory } from 'history'
import WeiboInfo from '../../weibo/WeiboInfo';
import {Button} from 'antd';
import 'antd/dist/antd.css';

export default class WeiboBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      weiboId: null,
      username: "",
      password: "",
      activities: null,
      /* hardcode parameter, need to be passed in */
      startTime :"Mon May 28 09:51:52 GMT 2019",
      endTime :"Mon July 29 09:51:52 GMT 2020",
      stats: null,
      statsReady: false,
      statsLoading : false,
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

  /* start 文案 here <- bad comment style*/
  weiboServer = "http://18.166.111.161:8787"
  fetchStats = (userId, startTime, endTime)=>{

    const config = {
      method: 'get',
      url: this.weiboServer + '/weibo/getStats',
      headers: {
        'Content-Type': 'application/json'
      },
      params:{
        userId: userId,
        startTime: startTime,
        endTime:endTime
      },
      withCredentials:true,
    };
    this.setState({
      statsLoading:true
    })
    axios(config)
        .then( response => {
          console.log(response.data)
          this.setState({
            stats: response.data,
            statsLoading:false,
            statsReady:true,
          })
        })
  }


  /* end 文案 here*/



  render() {
    const { activities,stats,statsReady,statsLoading,weiboId,startTime,endTime } = this.state;
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
        <section>
          < div className="row" >
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">用户{this.state.weiboId}的微博报表</h3>
                  <Button
                    loading={ statsLoading}
                    onClick={()=>{
                      this.fetchStats(weiboId,startTime,endTime)
                    }}
                  >
                    生成报表
                  </Button>
                </div>
                {statsReady ?
                    <div className="box-body">
                      {stats.avgWb > 1 ? "你是一个爱发微博,爱展示自己的人.\n" : "你是一个不太爱发微博,很有神秘感的人.\n"}
                      从{startTime}到{endTime}，你每天平均发{stats.avgWb}条微博，总共已经发了{stats.allWb}条微博呢！
                      {stats.maxDate}是一个特殊的日子，那天你发了{stats.maxWb}条微博，一定有很让你兴奋的事情吧！
                      你的微博质量很高呢，平均每条微博都有{stats.avgUp}个赞，总共获得了{stats.allUp}个赞，其中最多的一条微博，竟然获得了{stats.maxUp}个赞，还记得你发了什么内容吗！{stats.maxUpWb}是这个哟，写得确实很出彩！
                      有很多人转过你的微博，平均每条微博都有{stats.avgRt}次转发，总共总共获得了{stats.allRt}次转发，其中最多的一条微博，竟然获得了{stats.maxRt}次转发，还记得你发了什么内容吗！{stats.maxRtWb}是这个哟，果然很有传播力！
                      你的微博下讨论很热烈，平均每条微博都有{stats.avgCm}次转发，总共总共获得了{stats.allCm}次转发，其中最多的一条微博，竟然获得了{stats.maxCm}次转发，还记得你发了什么内容吗！{stats.maxCmWb}是这个哟，很辩证的话题呢！
                    </div> : statsLoading ? <div>  "加载中..."  </div> : null
                }
              </div>
            </div>
          </div >
        </section>
      </div >
    )
  }
}
