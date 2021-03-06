import React, {Component} from "react";
import axios from "../../axios";
import {createBrowserHistory} from "history";
import WeiboInfo from "../../weibo/WeiboInfo";
import {
    Carousel,
    Button,
    Typography,
    DatePicker,
    Space,
    Divider,
    message,
    Input,
    Popconfirm, Card,
} from "antd";
import "antd/dist/antd.css";
import moment from "moment";

import {configConsumerProps} from "antd/lib/config-provider";

const contentStyle = {
    height: '300px',
    color: '#fff',
    lineHeight: '160px',
    textAlign: 'center',
    background: '#364d79',
};
const {RangePicker} = DatePicker;
const {Text, Paragraph} = Typography;
export default class WeiboBodyContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            iLifeId: null,
            weiboId: null,
            username: "",
            password: "",
            activities: null,
            /* hardcode parameter, need to be passed in */
            startTime: "Mon May 28 09:51:52 GMT 2019",
            endTime: "Mon October 29 09:51:52 GMT 2020",
            startDate: "2020-07-01",
            endDate: "now",
            stats: null,
            statsReady: false,
            statsLoading: false,
        };
        //this.login = this.login.bind(this);
        this.getWeiboId = this.getWeiboId.bind(this);
    }

    componentDidMount() {
        let arr,
            reg = new RegExp("(^| )" + "username" + "=([^;]*)(;|$)");
        let username = "";
        if ((arr = document.cookie.match(reg))) {
            username = unescape(arr[2]);
        } else {
            username = null;
        }
        this.setState({
            username: username,
        });

        if (!username) {
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
            method: "get",
            url: "http://18.166.111.161:8686/auth/getByAccount?account=" + username,
            headers: {
                withCredentials: true,
            },
        };

        const user = await axios(config)
            .then(function (response) {
                //console.log(JSON.stringify(response.data));
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
                return null;
            });
        if (!user) return;
        //console.log(weiboId);
        var config = {
            method: "get",
            url: "http://121.36.196.234:8787/weibo/getWeibos?userId=" + user.weibid,
            headers: {
                withCredentials: true,
            },
        };
        this.setState({
            weiboId: user.weibid,
            iLifeId: user.id,
        });

        const activities = await axios(config)
            .then(function (response) {
                //console.log(JSON.stringify(response.data));
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
                return null;
            });

        if (activities) {
            this.setState({
                activities,
            });
        }
    }

    nameOnChange(val) {
        this.setState({
            username: val.target.value,
        });
    }

    psdOnChange(val) {
        this.setState({
            password: val.target.value,
        });
    }

    /* start 文案 here <- bad comment style*/
    weiboServer = "http://121.36.196.234:8787";
    fetchStats = (userId, startTime, endTime) => {
        const config = {
            method: "get",
            url: this.weiboServer + "/weibo/getStats",
            headers: {
                "Content-Type": "application/json",
            },
            params: {
                userId: userId,
                startTime: startTime,
                endTime: endTime,
            },
            withCredentials: true,
        };
        this.setState({
            statsLoading: true,
        });
        axios(config).then((response) => {
            console.log(response.data);
            this.setState({
                stats: response.data,
                statsLoading: false,
                statsReady: true,
            });
        });
    };

    changeId = async (e) => {
        let userId = this.state.iLifeId;
        let wbId = document.getElementById("changeId").value;
        let data1 = {
            userId: userId,
            wbId: wbId,
        };
        var config = {
            method: "post",
            data: data1,
            url: "http://18.166.111.161:8686/auth/updateWbId",
            headers: {
                withCredentials: true,
            },
        };
        const that=this;
        const weiboId = await axios(config)
            .then(function (response) {
                console.log(JSON.stringify(response.data));
                message.success("更新成功！");
                that.componentDidMount();
                return response;
            })
            .catch(function (error) {
                console.log(error);
            });
        if (weiboId) this.setState({weiboId: wbId, show: false});
    };

    /* end 文案 here*/

    crawl = () => {
        message.loading({
            content: "正在更新微博数据，请稍作等待！",
            style: {marginTop: "40px"},
            duration: 0,
        });
        let config = {
            method: "get",
            url:
                "http://121.36.196.234:8585/weibo/crawlWeibo?userId=" +
                this.state.weiboId +
                "&startDate=" + this.state.startDate + "&endDate=" + this.state.endDate,
            headers: {
                withCredentials: true,
            },
            timeout: "10000ms"
        };
        let that = this;
        axios(config)
            .then(function (response) {
                message.destroy();
                message.success({
                    content: "微博数据更新成功！请重新进入页面查看",
                    style: {marginTop: "40px"},
                });
                that.componentDidMount();
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    render() {
        const {
            activities,
            stats,
            statsReady,
            statsLoading,
            weiboId,
            startTime,
            endTime,
        } = this.state;
        return (
            <div className="content-wrapper">
                <section className="content" id="info">
                    <div className="row">
                        <div className="col-xs-12">
                            <div className="box">
                                <div className="box-header">
                                    <Divider
                                        orientation="left"
                                        style={{color: "#333", fontWeight: "normal"}}
                                    >
                                        <h3 className="box-title">用户{this.state.weiboId}的微博数据</h3>
                                    </Divider>
                                    <Popconfirm
                                        placement="bottomLeft"
                                        title={<div>
                                            <Paragraph>所有数据和图片均来自网上公开资料，请放心食用。</Paragraph>
                                            <Paragraph>数据更新将会花费一段时间，请耐心等待~</Paragraph>
                                            希望获取什么时间段的数据？
                                            <RangePicker
                                                ranges={{
                                                    Today: [moment(), moment()],
                                                    "This Month": [moment().startOf("month"), moment().endOf("month")],
                                                }}
                                                format="YYYY-MM-DD"
                                                onChange={(dates) => {
                                                    let day0 = dates[0].date() < 10 ? "0" + dates[0].date() : dates[0].date();
                                                    let day1 = dates[1].date() < 10 ? "0" + dates[1].date() : dates[1].date();
                                                    let month0 = (dates[0].month() + 1) < 10 ? "0" + (dates[0].month() + 1) : (dates[0].month() + 1);
                                                    let month1 = (dates[1].month() + 1) < 10 ? "0" + (dates[1].month() + 1) : (dates[1].month() + 1);
                                                    let q0 = dates[0].year() + "-" + month0 + "-" + day0;
                                                    let q1 = dates[1].year() + "-" + month1 + "-" + day1;
                                                    console.log(q0);
                                                    console.log(q1);
                                                    this.setState({startDate: q0, endDate: q1})
                                                    // let GMT = new (dates[0]._d);-
                                                    // let GMT1 = new Date(dates[1]._d);
                                                    //this.setState({ startDate: GMT.toUTCString(), endDate: GMT1.toUTCString() })
                                                }}
                                                style={{marginLeft: "20px"}}
                                            />
                                        </div>}
                                        onConfirm={this.crawl}
                                        okText="更新数据"
                                        cancelText="再想想"
                                    >
                                        <p className="btn btn-danger">更新数据</p>
                                    </Popconfirm>

                                    <p
                                        className="btn btn-primary"
                                        onClick={() => {
                                            this.setState({show: !this.state.show});
                                        }}
                                    >
                                        绑定账户
                                    </p>
                                    {this.state.show ? (
                                        <Input
                                            id="changeId"
                                            style={{marginTop: 10}}
                                            placeholder={"输入微博用户主页中浏览器地址栏处的用户ID"}
                                            suffix={<Button onClick={this.changeId}>确认</Button>}
                                        />
                                    ) : null}
                                </div>
                                <div className="box-body">
                                    <WeiboInfo activities={activities}/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="row" id="analyse">
                        <div className="col-xs-12">
                            <div className="box">
                                <div className="box-header">
                                    <Divider
                                        orientation="left"
                                        style={{color: "#333", fontWeight: "bold"}}
                                    >
                                        <h3 className="box-title">您的微博报表</h3>
                                    </Divider>
                                    <Button
                                        size={"large"}
                                        loading={statsLoading}
                                        onClick={() => {
                                            this.fetchStats(weiboId, startTime, endTime);
                                        }}
                                    >
                                        生成报表
                                    </Button>

                                    <Space direction="vertical" size={12}>
                                        <RangePicker
                                            ranges={{
                                                Today: [moment(), moment()],
                                                "This Month": [
                                                    moment().startOf("month"),
                                                    moment().endOf("month"),
                                                ],
                                            }}
                                            showTime
                                            format="YYYY/MM/DD HH:mm:ss"
                                            onChange={(dates) => {
                                                var GMT = new Date(dates[0]._d);
                                                var GMT1 = new Date(dates[1]._d);
                                                this.setState({
                                                    startTime: GMT.toUTCString(),
                                                    endTime: GMT1.toUTCString(),
                                                });
                                            }}
                                            style={{marginLeft: "20px"}}
                                        />
                                    </Space>
                                </div>
                                <div style={{marginLeft: "10px", fontSize: "18px"}}>
                                    {/*<div className="report-display" style={{border: '5px solid light-yellow', borderRadius: '10px',elevation:10 }}>*/}
                                    {statsReady ? (
                                        <span className="box-body" copyable>
                                                                      <Card style={{
                                                                          width: "95%",
                                                                          marginLeft: "40px",
                                                                          borderWidth: "3px"
                                                                      }}
                                                                            onClick={this.handlePrev}
                                                                      >

                      <Carousel ref='img' style={{fontSize: "18px", textAlign: "center", background: 'white'}}  >

                        <div style={{background: 'white'}}>
                                                <Paragraph>
                        {" "}
                                                    {stats.avgWb > 1
                                                        ? "你是一个爱发微博,爱展示自己的人.\n"
                                                        : "你是一个不太爱发微博,很有神秘感的人.\n"}
                      </Paragraph>
                      <Paragraph>
                        {" "}
                          从{" "}
                          <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {startTime}
                        </span>
                        到{" "}
                          <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {endTime}{" "}
                        </span>
                        这一段时间内，你每天平均发{" "}
                          <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {stats.avgWb}
                        </span>
                        条微博，总共已经发了
                        <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {stats.allWb}
                        </span>{" "}
                          条微博呢！
                      </Paragraph>
                                                <Paragraph>
                        {" "}
                                                    <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {stats.maxDate}
                        </span>{" "}
                                                    是一个特殊的日子，那天你发了
                        <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {stats.maxWb}
                        </span>{" "}
                                                    条微博，是这些天你发微博最多的一天，一定有很让你兴奋的事情吧！
                      </Paragraph>
                        </div>
                        <div>
                     <Paragraph>
                        {" "}
                         你的微博质量很高呢，平均每条微博都有{" "}
                         <span style={{fontSize: "22px", fontFamily: 'Georgia'}}>
                          {stats.avgUp}
                        </span>{" "}
                         个赞，总共获得了
                        <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {stats.allUp}
                        </span>
                        个赞，其中最多的一条微博，竟然获得了
                        <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {stats.maxUp}
                        </span>{" "}
                         个赞，还记得你发了什么内容吗！
                                                 </Paragraph>
                                                        <Paragraph>
                        <span style={{fontSize: "24px", fontFamily: 'Georgia'}}>
                          "{stats.maxUpWb}"
                        </span>{" "}
                                                </Paragraph>
                                                        <Paragraph>
                         是这个哟，写得确实很出彩！
                      </Paragraph>
                        </div>
                        <div>
                                                <Paragraph>
                        {" "}
                                                    有很多人转过你的微博，平均每条微博都有
                        <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {stats.avgRt}
                        </span>{" "}
                                                    次转发，总共获得了
                        <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {stats.allRt}
                        </span>{" "}
                                                    次转发，其中最多的一条微博，竟然获得了
                        <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {stats.maxRt}
                        </span>{" "}
                                                    次转发，还记得你发了什么内容吗！
                                                     </Paragraph>
                                                    <Paragraph>
                        <span style={{fontSize: "24px", fontFamily: 'Georgia'}}>
                          "{stats.maxRtWb}"
                        </span>{" "}
                        </Paragraph>
                                                        <Paragraph>
                                                    是这个哟，果然很有传播力！
                      </Paragraph>
                        </div>
                        <div>
                     <Paragraph>
                        {" "}
                         你的微博下讨论很热烈，平均每条微博都有
                        <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {stats.avgCm}
                        </span>{" "}
                         次转发，总共获得了
                        <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {stats.allCm}
                        </span>{" "}
                         次转发，其中最多的一条微博，竟然获得了
                        <span style={{fontSize: "28px", fontFamily: 'Georgia'}}>
                          {stats.maxCm}
                        </span>{" "}
                         个评论，还记得你发了什么内容吗！
                                                 </Paragraph>
                                                        <Paragraph>
                        <span style={{fontSize: "24px", fontFamily: 'Georgia'}}>
                          "{stats.maxCmWb}"
                        </span>{" "}
                                                </Paragraph>
                                                        <Paragraph>
                         是这个哟，很辩证的话题呢！
                      </Paragraph>
                        </div>
                      </Carousel>
                                                                      </Card>



                    </span>
                                    ) : statsLoading ? (
                                        <div> "加载中..." </div>
                                    ) : null}
                                </div>
                                {/*</div>*/}
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        );
    }
    handlePrev = ()=>{
        this.refs.img.next(); //ref = img
    }
}
