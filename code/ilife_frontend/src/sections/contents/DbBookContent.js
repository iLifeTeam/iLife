import React, { Component } from 'react'
import axios from 'axios';
import { createBrowserHistory } from 'history'
import DoubanBooks from '../../douban/DoubanBooks';
import { Button, Typography } from 'antd';
import 'antd/dist/antd.css';
const { Text, Paragraph } = Typography;
export default class DbBookContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            doubanId: null,
            username: "",
            password: "",
            activities: null,
            statsLoading: false,
            statsReady: false,
            stats: null,
        }
        //this.login = this.login.bind(this);
        this.getMovies = this.getMovies.bind(this);
        this.crawl = this.crawl.bind(this);
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
        this.getMovies(username);
    }

    async getMovies(username) {
        var config = {
            method: 'get',
            url: 'http://18.166.111.161:8686/auth/getByAccount?account=' + username,
            headers: {
                withCredentials: true
            }
        };

        const doubanId = await axios(config)
            .then(function (response) {
                console.log(JSON.stringify(response.data));
                return response.data.doubanid;
            })
            .catch(function (error) {
                console.log(error);
                return null;
            });

        this.setState({ doubanId })
        console.log(doubanId);
        var config = {
            method: 'get',
            url: 'http://121.36.196.234:8383/douban/getBooks?userId=' + doubanId,
            headers: {
                withCredentials: true,
            }
        };

        const activities = await axios(config)
            .then(function (response) {
                console.log(JSON.stringify(response.data));
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        this.setState({ activities: activities });
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

    crawl() {
        var config = {
            method: 'get',
            url: 'http://121.36.196.234:8484/douban/crawlMovie?userId=' + this.state.doubanId + '&limit=2&type=book',
            headers: {
                withCredentials: true,
            }
        };

        axios(config)
            .then(function (response) {
                console.log(JSON.stringify(response.data));
                alert("更新成功！")
            })
            .catch(function (error) {
                console.log(error);
            });

    }

    /* start 文案 here <- bad comment style*/
    doubanServer = "http://121.36.196.234:8383";
    doubanCrawlServer = "http://18.166.24.220:8484";
    fetchStats = (userId) => {

        const config = {
            method: 'get',
            url: this.doubanServer + '/douban/getBookStats?userId=' + userId,
            headers: {
                'Content-Type': 'application/json'
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
    }


    /* end 文案 here*/


    render() {
        const { activities, stats, statsReady, statsLoading, userId } = this.state;
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
                    < div className="row">
                        <div className="col-xs-12">
                            <div className="box">
                                <div className="box-header">
                                    <h3 className="box-title">用户{this.state.doubanId}的微博图书数据</h3>
                                    <p className="btn btn-primary" onClick={this.crawl}>更新数据</p>
                                </div>
                                <div className="box-body">
                                    {<DoubanBooks activities={activities} />}
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <section>
                    < div className="row">
                        <div className="col-xs-12">
                            <div className="box">
                                <div className="box-header">
                                    <h3 className="box-title">用户{this.state.weiboId}的豆瓣读书报表</h3>
                                    <Button
                                        loading={statsLoading}
                                        onClick={() => {
                                            this.fetchStats(this.state.doubanId)
                                        }}
                                    >
                                        生成报表
                                    </Button>
                                </div>
                                {statsReady ?
                                    <div className="box-body">
                                        <Paragraph>你是一个爱看书的人，从你踏入豆瓣的小世界以来，你已经阅读了{stats.allBook}本书，书海无涯，知识作舟，要继续保持哦。</Paragraph>
                                        <Paragraph>在你阅读的书籍中：</Paragraph>
                                        <Paragraph>平均评分是<Text mark strong>{stats.avgRanking}</Text>，评分最高的是<Text mark
                                            strong>{stats.maxRankingBook.name}</Text>，达到了<Text
                                                mark strong>{stats.maxRanking}</Text>分，这是一本由<Text mark
                                                    strong>{stats.maxRankingBook.author}</Text>所著的书，不知道你是否欣喜与它相遇呢？</Paragraph>
                                        <Paragraph>平均价格是<Text mark strong>{stats.avgPrice}</Text>，价格最高的是<Text mark
                                            strong>{stats.maxPriceBook.name}</Text>，需要<Text
                                                mark strong>{stats.maxPrice}</Text>大洋，这是一本由<Text mark
                                                    strong>{stats.maxPriceBook.author}</Text>所著的书，想必它一定有你所中意之处吧？</Paragraph>
                                        <Paragraph>平均热度是<Text mark strong>{stats.avgHot}</Text>，热度最高的是<Text mark
                                            strong>{stats.maxHotBook.name}</Text>，共有<Text
                                                mark strong>{stats.maxHot}</Text>看过，这是一本由<Text mark
                                                    strong>{stats.maxHotBook.author}</Text>所著的书，这样的爆款书目，读起来一定畅快淋漓吧？</Paragraph>
                                        <Paragraph>热度最低的是<Text mark strong>{stats.minHotBook.name}</Text>，共有<Text mark
                                            strong>{stats.minHot}</Text>看过，这是一本由<Text
                                                mark
                                                strong>{stats.minHotBook.author}</Text>所著的书，愿意读小众书籍的人，运气都不会太差！</Paragraph>
                                        <Paragraph>你最喜欢的作者是<Text mark strong>{stats.preAuthor}</Text>，读一个人的著作，也是和人心灵沟通的一种方式。</Paragraph>
                                    </div> : statsLoading ? <div> "加载中..." </div> : null
                                }
                            </div>
                        </div>
                    </div>
                </section>

            </div>
        )
    }
}
