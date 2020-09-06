import React, {Component} from "react";
import axios from "../../axios";
import {createBrowserHistory} from "history";
import {
    Button,
    Divider,
    Typography,
    Popconfirm,
    message,
    Row,
    Col,
    Card,
} from "antd";
import "antd/dist/antd.css";

const {Meta} = Card;
const {Text, Paragraph} = Typography;
const text = (
    <div>
        <Paragraph>
            '进行推荐前，请确保你已经绑定了豆瓣账户且进行了书籍和电影数据的获取，否则推荐结果将不太准确。
        </Paragraph>
        <Paragraph>所有数据和图片均来自网上公开资料，请放心食用。</Paragraph>
        <Paragraph>推荐将会花费一段时间，请耐心等待~</Paragraph>
    </div>
);
export default class PeopleContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            finish: false,
            pop: false,
            show: false,
            weiboId: null,
            biliId:null,
            username: "",
            password: "",
            movies: null,
            books: null,
            statsLoading: false,
            statsReady: false,
            stats: null,
            rcmd: null,
        };
    }

    componentWillMount = () => {
        //<meta name="referrer" content="no-referrer" />
        this.appendMeta();
    };
    appendMeta = () => {
        let oMeta = document.createElement("meta");
        oMeta.referrer = "no-referrer";
        document.getElementsByTagName("head")[0].appendChild(oMeta);
    };

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
        this.getInfo(username);
    }

    async getInfo(username) {
        var config = {
            method: "get",
            url: "http://18.166.111.161:8686/auth/getByAccount?account=" + username,
            headers: {
                withCredentials: true,
            },
        };

        const user = await axios(config)
            .then(function (response) {
                console.log("get the user information : ",user);
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
                return null;
            });

        this.setState({
            weiboId:user.weiboId,
            biliId:user.biliId
        });

    }

    async getStoredRcmd(doubanId) {
        const that = this;
        var config = {
            method: "get",
            url: "http://121.36.196.234:8383/douban/getStoredRcmd?userId=" + doubanId,
            headers: {
                withCredentials: true,
            },
        };
        const storedRcmd = await axios(config)
            .then(function (response) {
                console.log(response.data);
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        if (storedRcmd !== "") {
            console.log("stored recommendation retrieved!");
            that.setState({finish: true, rcmd: storedRcmd});
        }else{
            console.log("can not find stored recommendation");
        }
    }

    /* end 文案 here*/
    async getMovies(doubanId) {
        var config = {
            method: "get",
            url: "http://121.36.196.234:8383/douban/getBooks?userId=" + doubanId,
            headers: {
                withCredentials: true,
            },
        };
        const movies = await axios(config)
            .then(function (response) {
                console.log(response.data);
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        this.setState({books: movies});
        this.getBooks(doubanId);
    }
    confirm = () => {
        message.loading({
            content: "正在进行娱乐推荐，请稍作等待！",
            style: {marginTop: "40px"},
            duration: 0,
        });
        this.getMovies(this.state.doubanId);
        return null;
    };
    changeId = async (e) => {
        let userId = window.sessionStorage.getItem("iLifeId");
        let dbId = document.getElementById("changeId").value;

        let data1 = {
            userId: userId,
            dbId: dbId,
        };
        var config = {
            method: "post",
            data: data1,
            url: "http://18.166.111.161:8686/auth/updateDbId",
            headers: {
                withCredentials: true,
            },
        };

        const doubanId = await axios(config)
            .then(function (response) {
                console.log(JSON.stringify(response.data));
                return response;
            })
            .catch(function (error) {
                console.log(error);
            });
        if (doubanId) this.setState({doubanId: dbId});
        this.componentDidMount();
    };

    render() {
        const {activities, stats, statsReady, statsLoading, userId} = this.state;
        return (
            <div className="content-wrapper">
                <section className="content">
                    <div className="row">
                        <div className="col-xs-12">
                            <div className="box">
                                <div className="box-header">
                                    <Divider
                                        orientation="left"
                                        style={{color: "#333", fontWeight: "normal"}}
                                    >
                                        <h3 className="box-title">
                                            {this.state.doubanId === null
                                                ? "请先点击“绑定账户”绑定豆瓣用户！"
                                                : "用户" + this.state.doubanId + "的娱乐推荐"}
                                        </h3>
                                    </Divider>
                                    <Popconfirm
                                        placement="bottomLeft"
                                        title={text}
                                        onConfirm={this.confirm}
                                        okText="开始推荐"
                                        cancelText="再想想"
                                    >
                                        <p className="btn btn-danger">开始推荐</p>
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
                                        <p>
                                            <input id="changeId"/>
                                            <button onClick={this.changeId}>确认</button>
                                        </p>
                                    ) : null}
                                </div>
                                {this.state.finish ? (
                                    <div className="box-body">
                                        <div className="row">
                                            <div className="col-xs-6">
                                            </div>
                                        </div>
                                    </div>
                                ) : null}
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        );
    }
}
