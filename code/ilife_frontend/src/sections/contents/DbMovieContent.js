import React, {Component} from 'react'
import axios from 'axios';
import {createBrowserHistory} from 'history'
import DoubanMovies from '../../douban/DoubanMovies';
import {Button, Divider, Typography} from "antd";

const {Text, Paragraph} = Typography;

export default class DbMovieContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            iLifeId: "",
            show: false,
            doubanId: null,
            username: "",
            password: "",
            activities: null,
            statsLoading: false,
            statsReady: false,
            stats: null,
        };
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
                localStorage.setItem("iLifeId", response.data.id);
                return response.data.doubanid;
            })
            .catch(function (error) {
                console.log(error);
                return null;
            });

        this.setState({doubanId})
        var config = {
            method: 'get',
            url: 'http://121.36.196.234:8383/douban/getMovies?userId=' + doubanId,
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
        this.setState({activities: activities});
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

    crawl = () => {
        let config = {
            method: 'get',
            url: 'http://121.36.196.234:8484/douban/crawlMovie?userId=' + this.state.doubanId + '&limit=2&type=movie',
            headers: {
                withCredentials: true,
            }
        };
        let that = this;
        axios(config)
            .then(function (response) {
                console.log(JSON.stringify(response.data));
                alert("电影数据更新成功！请重新进入页面查看");
                that.forceUpdate();
            })
            .catch(function (error) {
                console.log(error);
            });

    };

    fetchStats = (userId) => {

        const config = {
            method: 'get',
            url: 'http://121.36.196.234:8383/douban/getMovieStats?userId=' + userId,
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
                console.log(response.data);
                this.setState({
                    stats: response.data,
                    statsLoading: false,
                    statsReady: true,
                })
            })
    };
    changeId = async (e) => {
        let userId = localStorage.getItem("iLifeId");
        let dbId = document.getElementById("changeId").value;

        let data1 = {
            "userId": userId,
            "dbId": dbId
        };
        var config = {
            method: 'post',
            data: data1,
            url: 'http://18.166.111.161:8686/auth/updateDbId',
            headers: {
                withCredentials: true,
            }
        };

        const doubanId = await axios(config)
            .then(function (response) {
                console.log(JSON.stringify(response.data));
                return response;
            })
            .catch(function (error) {
                console.log(error);
            });
        if (doubanId) this.setState({doubanId: dbId})

    };

    render() {
        const {activities, stats, statsReady, statsLoading, doubanId} = this.state;
        return (
            <div className="content-wrapper">
                <section className="content">
                    < div className="row">
                        <div className="col-xs-12">
                            <div className="box">
                                <div className="box-header">
                                    <Divider orientation="left" style={{color: '#333', fontWeight: 'normal'}}><h3
                                        className="box-title">用户{this.state.doubanId}的电影数据</h3></Divider>
                                    <p className="btn btn-primary" onClick={this.crawl}>更新数据</p>
                                    <p className="btn btn-primary" onClick={() => {
                                        this.setState({show: !this.state.show})
                                    }}>绑定账户</p>
                                    {this.state.show ?
                                        <p><input id="changeId"/>
                                            <button onClick={this.changeId}>确认</button>
                                        </p> : null}
                                </div>
                                <div className="box-body">
                                    {<DoubanMovies activities={activities}/>}
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
                                    <h3 className="box-title">用户{this.state.doubanId}的豆瓣电影报表</h3>
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

                                        <Paragraph>让一个人置身于变幻无穷的环境中，让他与数不尽或远或近的人物错身而过，让他与整个世界发生关系：这就是电影的意义。——安德烈·塔可夫斯基</Paragraph>
                                        <Paragraph>你是一个爱看电影的人，从你踏入豆瓣的小世界以来，你已经观看了<Text mark
                                                                                       strong>{stats.allMovie}</Text>部电影，书海无涯，知识作舟，要继续保持哦。</Paragraph>
                                        <Paragraph>在你观看的电影中：</Paragraph>
                                        <Paragraph>平均评分是<Text mark strong>{stats.avgRanking}</Text>，评分最高的是<Text mark
                                                                                                                strong>{stats.maxRankingMovie.name}</Text>，达到了<Text
                                            mark strong>{stats.maxRanking}</Text>分，这部电影的类型是<Text mark
                                                                                                 strong>{stats.maxRankingMovie.type}</Text>，有
                                            <Text mark strong>{stats.maxRankingMovie.hot}</Text>人与你一起看过它，
                                            不知道你是否欣喜与它相遇呢？</Paragraph>
                                        {/*<Paragraph>平均价格是<Text mark strong>{stats.avgPrice}</Text>，价格最高的是<Text mark*/}
                                        {/*                                                                      strong>{stats.maxPriceBook.name}</Text>，需要<Text*/}
                                        {/*    mark strong>{stats.maxPrice}</Text>大洋，这是一本由<Text mark*/}
                                        {/*                                                     strong>{stats.maxPriceBook.author}</Text>所著的书，想必它一定有你所中意之处吧？</Paragraph>*/}
                                        <Paragraph>平均热度是<Text mark strong>{stats.avgHot}</Text>，热度最高的是<Text mark
                                                                                                            strong>{stats.maxHotMovie.name}</Text>，共有<Text
                                            mark strong>{stats.maxHot}</Text>人看过，这部电影的类型是<Text mark
                                                                                               strong>{stats.maxHotMovie.type}</Text>，它不仅是一部红遍大江南北的电影，更拥有者不俗的
                                            <Text mark strong>{stats.maxHotMovie.ranking}</Text>的评分呢！</Paragraph>
                                        <Paragraph>热度最低的是<Text mark strong>{stats.minHotMovie.name}</Text>，共有
                                            <Text mark strong>{stats.minHot}</Text>人看过，这部电影的类型是<Text mark strong>{stats.minHotMovie.type}</Text>，它不仅是一部红遍大江南北的电影，更拥有者不俗的
                                            <Text mark strong>{stats.maxHot}</Text>的热度值呢！</Paragraph>
                                        你最喜欢的作品语言是<Text mark strong>{stats.preLanguage}</Text>，喜欢{stats.preLanguage}的人，品位都不差哦！
                                        你最喜欢的电影类型是<Text mark strong>{stats.preType}</Text>，喜欢{stats.preType}的人，品位都不差哦！
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
