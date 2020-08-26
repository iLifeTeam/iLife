import React, { Component } from 'react'
import axios from 'axios';
import { createBrowserHistory } from 'history'
import DoubanMovies from '../../douban/DoubanMovies';
import {Button, Divider, message, Popconfirm, Typography,Input} from "antd";
const {Text, Paragraph} = Typography;
const text = <div>
    <Paragraph>所有数据和图片均来自网上公开资料，请放心食用。</Paragraph>
    <Paragraph>数据更新将会花费一段时间，请耐心等待~</Paragraph>
    希望获取几页数据? <input id="bookInput" />
</div>;
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
        const username = window.sessionStorage.getItem("username");

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
                window.sessionStorage.setItem("iLifeId", response.data.id);
                return response.data.doubanid;
            })
            .catch(function (error) {
                console.log(error);
                return null;
            });

        this.setState({ doubanId })
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

    crawl = () => {
        let bookInput = document.getElementById('bookInput');
        console.log(bookInput.value);
        if(bookInput.value===null||bookInput.value ===""||bookInput.value===0){
            bookInput.value = 2;
        }
        message.loading({
            content: "正在更新电影数据，请稍作等待！",
            style: { marginTop: '40px' },
            duration: 0
        });
        let config = {
            method: 'get',
            url: 'http://121.36.196.234:8484/douban/crawlMovie?userId=' + this.state.doubanId + '&limit='+bookInput.value+'&type=movie',
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
                    content: "电影数据更新成功！请重新进入页面查看",
                    style: { marginTop: '40px' },
                });
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
        let userId = window.sessionStorage.getItem("iLifeId");
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
        if (doubanId) this.setState({ doubanId: dbId })

    };
    recommend = (e) => {
        if (e === "动画") {
            return (
                <div className="row">
                    <div className="col-xs-4">
                        <img style={{ width: '500px', heigth: '500px', float: "left" }} src="https://img3.doubanio.com/view/photo/raw/public/p456676352.jpg" alt="" />
                    </div>
                    <div className="col-xs-8">
                        <p style={{ float: "right", marginLeft: '30px' }}>琪琪今年13岁了，按照魔法界的规矩，魔法少女年满13岁就要出外进行为期一年的修行。所以琪琪带着宠物黑猫吉吉踏上了修行之旅。然而，修行之旅开始得并不顺利，当琪琪来到海边一座大城市时，人们并没有欢迎她的到来，人人都不搭理她。幸亏琪琪有一颗善良的心，当她拾到了一件别人的失物时，热心的琪琪找到了失主。她的善良赢得了面包店老板娘的好感，琪琪就在面包店开始了她用飞行魔法为客人服务的快递业务。琪琪很快适应了新环境，一次，一个热衷于飞机制造的男孩邀请琪琪去参加飞行俱乐部的聚会，途中琪琪因为帮一位老大娘送东西而被雨淋了。从此，琪琪突然发现自己的魔法正在一天天变弱。</p>
                    </div>
                </div>
            )
        }
        if (e === "爱情") {
            return (
                <div className="row">
                    <div className="col-xs-4">
                        <img style={{ width: '500px', heigth: '500px' }} src="https://img9.doubanio.com/view/photo/raw/public/p2515437624.jpg" alt="" />
                    </div>
                    <div className="col-xs-7">
                        <p style={{ marginLeft: '30px' }}>富田多满子（新垣结衣 饰）的母亲是一名十分优秀的乒乓球运动员，在母亲的严厉教导下，多满子的整个童年都在天才乒乓球少女的荣光之下度过。然而，多满子却并不是真心喜爱这项运动，于是，母亲过世后，她便彻底放飞了自我，就此远离了球桌。
                            随着时间的推移，多满子平凡的长大成为了一名OL，并且遇见了名为江岛晃彦（濑户康史 饰）的男子，两人走到了一起。然而，美梦是短暂的，第三者的出现让多满子和江岛之间的恋情画上了句号，与此同时，多满子辞掉了工作，灰头土脸的返回家乡，在那里，母亲留下的经营不善的乒乓球俱乐部等待着她的接手</p>
                    </div>
                </div>
            )
        }
    };

    render() {
        const { activities, stats, statsReady, statsLoading, doubanId } = this.state;
        return (
            <div className="content-wrapper">
                <section className="content">
                    < div className="row">
                        <div className="col-xs-12">
                            <div className="box">
                                <div className="box-header">
                                    <Divider orientation="left" style={{ color: '#333', fontWeight: 'normal' }}><h3
                                        className="box-title">用户{this.state.doubanId}的电影数据</h3></Divider>
                                    <Popconfirm
                                        placement="bottomLeft"
                                        title={text}
                                        onConfirm={this.crawl}
                                        okText="更新数据"
                                        cancelText="再想想"
                                    >
                                        <p className="btn btn-danger">更新数据</p>
                                    </Popconfirm>
                                    <p className="btn btn-primary" onClick={() => {
                                        this.setState({ show: !this.state.show })
                                    }}>绑定账户</p>
                                    {this.state.show ?
                                        <Input id="changeId" style={{ marginTop: 10 }} placeholder={"输入豆瓣用户主页中浏览器地址栏处的用户ID"} suffix={<Button onClick={this.changeId}>确认</Button>} /> : null}
                                </div>
                                <div className="box-body">
                                    {<DoubanMovies activities={activities} />}
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
                                        size={"large"}
                                        loading={statsLoading}
                                        onClick={() => {
                                            this.fetchStats(this.state.doubanId)
                                        }}
                                    >
                                        生成报表
                                    </Button>
                                </div>
                                {statsReady ?
                                    <div className="box-body" style={{ fontSize: '18px' }}>

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
                                            <Text mark strong>{stats.minHot}</Text>人看过，这部电影的类型是<Text mark
                                                strong>{stats.minHotMovie.type}</Text>，它不仅是一部红遍大江南北的电影，更拥有者不俗的
                                            <Text mark strong>{stats.minHotMovie.ranking}</Text>的评分呢！</Paragraph>
                                        你最喜欢的作品语言是<Text mark strong>{stats.preLanguage}</Text>，最喜欢的电影类型是<Text mark
                                            strong>{stats.preType}</Text>，
                                        喜欢{stats.preType}和{stats.preLanguage}的人，品位都不差哦！

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
