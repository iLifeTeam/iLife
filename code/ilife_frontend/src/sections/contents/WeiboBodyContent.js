import React, { Component } from 'react'
import axios from 'axios';
import { createBrowserHistory } from 'history'
import WeiboInfo from '../../weibo/WeiboInfo';
import { Button, Typography, Row, Col, Divider, message, Input } from 'antd';
import 'antd/dist/antd.css';

const { Text, Paragraph } = Typography;
export default class WeiboBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      show: false,
      weiboId: null,
      username: "",
      password: "",
      activities: null,
      /* hardcode parameter, need to be passed in */
      startTime: "Mon May 28 09:51:52 GMT 2019",
      endTime: "Mon July 29 09:51:52 GMT 2020",
      stats: null,
      statsReady: false,
      statsLoading: false,
    };
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
        localStorage.setItem("iLifeId", response.data.id);
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
  weiboServer = "http://121.36.196.234:8787"
  fetchStats = (userId, startTime, endTime) => {

    const config = {
      method: 'get',
      url: this.weiboServer + '/weibo/getStats',
      headers: {
        'Content-Type': 'application/json'
      },
      params: {
        userId: userId,
        startTime: startTime,
        endTime: endTime
      },
      withCredentials: true,
    };
    this.setState({
      statsLoading: true
    });
    axios(config)
      .then(response => {
        console.log(response.data)
        this.setState({
          stats: response.data,
          statsLoading: false,
          statsReady: true,
        })
      })
  };
  changeId = async (e) => {
    let userId = localStorage.getItem("iLifeId");
    let wbId = document.getElementById("changeId").value;
    let data1 = {
      "userId": userId,
      "wbId": wbId
    };
    var config = {
      method: 'post',
      data: data1,
      url: 'http://18.166.111.161:8686/auth/updateWbId',
      headers: {
        withCredentials: true,
      }
    };

    const weiboId = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    if (weiboId) this.setState({ weiboId: wbId })

  };

  /* end 文案 here*/

  crawl = () => {
    message.loading({
      content: "正在更新微博数据，请稍作等待！",
      style: { marginTop: '40px' },
      duration: 0
    });
    let config = {
      method: 'get',
      url: 'http://121.36.196.234:8585/weibo/crawlWeibo?userId=' + this.state.weiboId + '&startDate=2020-07-01&endDate=now',
      headers: {
        withCredentials: true,
      }
    };
    let that = this;
    axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        message.destroy();
        message.success({
          content: "微博数据更新成功！请重新进入页面查看",
          style: { marginTop: '40px' },
        });
        that.forceUpdate();

      })
      .catch(function (error) {
        console.log(error);
      });

  };


  render() {
    const { activities, stats, statsReady, statsLoading, weiboId, startTime, endTime } = this.state;
    return (
      <div className="content-wrapper">
        <section className="content">
          <div className="row" >
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <Divider orientation="left" style={{ color: '#333', fontWeight: 'normal' }}><h3 className="box-title">您的微博数据</h3></Divider>
                  <p className="btn btn-danger" onClick={this.crawl}>更新数据</p>
                  <p className="btn btn-primary" onClick={() => { this.setState({ show: !this.state.show }) }}>绑定账户</p>
                  {this.state.show ?
                    <Input id="changeId" style={{ marginTop: 10 }} placeholder={"输入微博用户主页中浏览器地址栏处的用户ID"} suffix={<Button onClick={this.changeId}>确认</Button>} /> : null}
                </div>
                <div className="box-body">
                  <WeiboInfo activities={activities} />
                </div>
              </div>
            </div>
          </div >
          < div className="row" >
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <Divider orientation="left" style={{ color: '#333', fontWeight: 'bold' }}><h3 className="box-title">您的微博报表</h3>
                  </Divider>
                  <Button
                    size={"large"}
                    loading={statsLoading}
                    onClick={() => {
                      this.fetchStats(weiboId, startTime, endTime)
                    }}
                  >
                    生成报表
                  </Button>
                </div >
                <div style={{ marginLeft: "10px" }}>
                  {/*<div className="report-display" style={{border: '5px solid light-yellow', borderRadius: '10px',elevation:10 }}>*/}
                  {statsReady ?
                    <Text className="box-body" copyable >
                      <Paragraph>  {stats.avgWb > 1 ? "你是一个爱发微博,爱展示自己的人.\n" : "你是一个不太爱发微博,很有神秘感的人.\n"}</Paragraph>
                      <Paragraph> 从 <Text mark strong>{startTime}</Text>到 <Text mark strong>{endTime} </Text>这一段时间内，你每天平均发 <Text mark strong>{stats.avgWb}</Text>条微博，总共已经发了<Text mark strong>{stats.allWb}</Text> 条微博呢！</Paragraph>
                      <Paragraph> <Text mark strong>{stats.maxDate}</Text> 是一个特殊的日子，那天你发了<Text mark strong>{stats.maxWb}</Text> 条微博，是这些天你发微博最多的一天，一定有很让你兴奋的事情吧！</Paragraph>
                      <Paragraph> 你的微博质量很高呢，平均每条微博都有 <Text mark strong>{stats.avgUp}</Text> 个赞，总共获得了<Text mark strong>{stats.allUp}</Text>个赞，其中最多的一条微博，竟然获得了<Text
                        mark strong>{stats.maxUp}</Text> 个赞，还记得你发了什么内容吗！<Text mark strong>"{stats.maxUpWb}"</Text> 是这个哟，写得确实很出彩！</Paragraph>
                      <Paragraph> 有很多人转过你的微博，平均每条微博都有<Text mark strong>{stats.avgRt}</Text> 次转发，总共获得了<Text mark strong>{stats.allRt}</Text> 次转发，其中最多的一条微博，竟然获得了<Text
                        mark strong>{stats.maxRt}</Text> 次转发，还记得你发了什么内容吗！<Text mark strong>"{stats.maxRtWb}"</Text> 是这个哟，果然很有传播力！</Paragraph>
                      <Paragraph> 你的微博下讨论很热烈，平均每条微博都有<Text mark strong>{stats.avgCm}</Text> 次转发，总共获得了<Text mark strong>{stats.allCm}</Text> 次转发，其中最多的一条微博，竟然获得了<Text
                        mark strong>{stats.maxCm}</Text> 个评论，还记得你发了什么内容吗！<Text mark strong>"{stats.maxCmWb}"</Text> 是这个哟，很辩证的话题呢！</Paragraph>
                    </Text> : statsLoading ? <div> "加载中..." </div> : null
                  }
                </div>
                {/*</div>*/}
              </div>
            </div>
          </div >
        </section>
      </div >
    )
  }
}
