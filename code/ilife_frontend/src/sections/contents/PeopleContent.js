import React, {Component} from "react";
import axios from "../../axios";
import {refList} from "../Settings"
import {biliList} from "../Settings";
import {createBrowserHistory} from "history";
import {
    Button,
    Divider,
    Typography,
    Popconfirm,
    message,
    Row,
    Col,
    Card, Image,
} from "antd";
import "antd/dist/antd.css";
import WeiboInfo from "../../weibo/WeiboInfo";

const {Meta} = Card;


const {Text, Paragraph} = Typography;
const text = (
    <div>
        <Paragraph>
            进行推荐前，请确保你已经绑定了微博账户且进行了微博数据的获取，否则推荐结果将不太准确。
        </Paragraph>
        <Paragraph>所有数据和图片均来自网上公开资料，请放心食用。</Paragraph>
        <Paragraph>推荐将会花费一段时间，请耐心等待~</Paragraph>
    </div>
);
export default class PeopleContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activities: null,
            biliInfo: null,
            refInfo: null,
            finish2: false,
            finish: false,
            pop: false,
            show: false,
            weiboId: null,
            biliId: null,
            username: "",
            password: "",
            movies: null,
            books: null,
            statsLoading: false,
            statsReady: false,
            stats: null,
            rcmd: null,
            weibos: null,
            bilis: null,
            PopVideo: null,
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
        console.log(username)

        if (!username) {
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
                console.log("get the user information : ", response.data);
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
                return null;
            });

        this.setState({
            weiboId: user.weibid,
            biliId: user.biliid
        })

    }

    async getBilis() {
        let biliId = this.state.biliId;
        let order = biliId % biliList.length;
        var config = {
            method: "get",
            url: "http://18.166.111.161:8000/bilibili/bili/getUp?mid=" + biliList[order],
            headers: {
                withCredentials: true,
            },
        };
        const that = this;
        const userInfo = await axios(config)
            .then(function (response) {
                console.log(response.data);
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        this.setState({biliInfo: userInfo});
        this.getVideos(biliList[order]);
    }

    async getVideos() {
        let biliId = this.state.biliId;
        let order = biliId % biliList.length;
        var config = {
            method: "get",
            url: "http://18.166.111.161:8000/bilibili/bili/getUpVideo?mid=" + biliList[order],
            headers: {
                withCredentials: true,
            },
        };
        const that = this;
        const movies = await axios(config)
            .then(function (response) {
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        console.log(movies)
        this.setState({PopVideo: movies.data.list.vlist, finish2: true});
    }

    /* end 文案 here*/
    async getMovies() {
        let weiboId = this.state.weiboId;
        let order = weiboId % refList.length;
        var config = {
            method: "get",
            url: "http://121.36.196.234:8787/user/getById?userId=" + refList[order],
            headers: {
                withCredentials: true,
            },
        };
        const that = this;
        const userInfo = await axios(config)
            .then(function (response) {
                console.log(response.data);
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        this.setState({refInfo: userInfo})
        this.getWeibos(userInfo.id);
    }

    getWeibos = async (weibid) => {
        var config = {
            method: "get",
            url: "http://121.36.196.234:8787/weibo/getWeibos?userId=" + weibid,
            headers: {
                withCredentials: true,
            },
        };
        const activities = await axios(config)
            .then(function (response) {
                //console.log(JSON.stringify(response.data));
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
                return null;
            });
        this.setState({weibos: activities});
        console.log(this.state.weibos);
        this.setState({finish: true});
    };
    confirm = () => {
        // message.loading({
        //     content: "正在进行娱乐推荐，请稍作等待！",
        //     style: {marginTop: "40px"},
        //     duration: 0,
        // });
        this.getMovies();
        return null;
    };
    confirm2 = () => {
        // message.loading({
        //     content: "正在进行娱乐推荐，请稍作等待！",
        //     style: {marginTop: "40px"},
        //     duration: 0,
        // });
        this.getBilis();
        return null;
    };
    clickPic = (param) => {
        window.open("https://www.bilibili.com/video/" + encodeURIComponent(param));
    };

    render() {
        const {weibos} = this.state;
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
                                            博主推荐
                                        </h3>
                                    </Divider>
                                    <Popconfirm
                                        placement="bottomLeft"
                                        title={text}
                                        onConfirm={this.confirm}
                                        okText="开始推荐"
                                        cancelText="再想想"
                                    >
                                        <p className="btn btn-danger">微博推荐</p>
                                    </Popconfirm>
                                    <Popconfirm
                                        placement="bottomRight"
                                        title={text}
                                        onConfirm={this.confirm2}
                                        okText="开始推荐"
                                        cancelText="再想想"
                                    >
                                        <p className="btn btn-primary">up主推荐</p>
                                    </Popconfirm>
                                </div>

                                <div className="box-body">
                                    <div className="row">
                                        <div className="col-xs-12">
                                            {this.state.finish ? (
                                                <Card style={{width: "95%"}}>
                                                    <p style={{fontSize: "25px", color: "red"}}>
                                                        推荐博主：
                                                    </p>
                                                    <div className="row">
                                                        <div className="col-xs-9">
                                                            <p style={{fontSize: "20px"}}>
                                                                <span style={{fontWeight: "bold"}}>
                                                                    博主名称：
                                                                </span>
                                                                {this.state.refInfo.nickname}
                                                            </p>
                                                            <p style={{fontSize: "20px"}}>
                                                                <span style={{fontWeight: "bold"}}>
                                                                    粉丝数：
                                                                </span>
                                                                {this.state.refInfo.followers}
                                                            </p>
                                                            <p style={{fontSize: "20px"}}>
                                                                <span style={{fontWeight: "bold"}}>
                                                                    关注数：
                                                                </span>
                                                                {this.state.refInfo.following}
                                                            </p>
                                                            <p style={{fontSize: "20px"}}>
                                                                <span style={{fontWeight: "bold"}}>
                                                                    微博数：
                                                                </span>
                                                                {this.state.refInfo.weibo_num}
                                                            </p>
                                                            <p style={{fontSize: "20px"}}>
                                                                <span style={{fontWeight: "bold"}}>
                                                                    个人描述：
                                                                </span>
                                                                {this.state.refInfo.description}
                                                            </p>

                                                        </div>
                                                        <div className="col-xs-3">
                                                            <img
                                                                referrerpolicy="no-referrer"
                                                                id="img2"
                                                                src={this.state.refInfo.avatar}
                                                                alt={"正在加载中..."}
                                                                style={{width: "100%", marginTop: "-50px"}}
                                                            />
                                                        </div>
                                                    </div>

                                                </Card>
                                            ) : null}
                                            <p></p>
                                            <WeiboInfo activities={this.state.weibos}/>
                                            {this.state.finish2 ? (
                                                <Card style={{width: "95%"}}>
                                                    <p style={{fontSize: "25px", color: "red"}}>
                                                        推荐up主：
                                                    </p>
                                                    <div className="row">
                                                        <div className="col-xs-9">
                                                            <p style={{fontSize: "20px"}}>
                                                                <span style={{fontWeight: "bold"}}>
                                                                    up主名称：
                                                                </span>
                                                                {this.state.biliInfo.data.name}
                                                            </p>
                                                            <p style={{fontSize: "20px"}}>
                                                                <span style={{fontWeight: "bold"}}>
                                                                    B站等级：
                                                                </span>
                                                                {this.state.biliInfo.data.level}
                                                            </p>
                                                            <p style={{fontSize: "20px"}}>
                                                                <span style={{fontWeight: "bold"}}>
                                                                    性别：
                                                                </span>
                                                                {this.state.biliInfo.data.sex}
                                                            </p>
                                                            <p style={{fontSize: "20px"}}>
                                                                <span style={{fontWeight: "bold"}}>
                                                                    个人简介：
                                                                </span>
                                                                {this.state.biliInfo.data.sign}
                                                            </p>
                                                        </div>
                                                        <div className="col-xs-3">
                                                            <img
                                                                referrerpolicy="no-referrer"
                                                                id="img2"
                                                                src={this.state.biliInfo.data.face}
                                                                alt={"正在加载中..."}
                                                                style={{width: "100%", marginTop: "-50px"}}
                                                            />
                                                        </div>
                                                    </div>
                                                    <Row gutter={24}>
                                                        {this.state.PopVideo
                                                            ? this.state.PopVideo.map((video) => {
                                                                console.log(video.title);
                                                                return (
                                                                    <Col
                                                                        key={video.aid}
                                                                        className="gutter-row"
                                                                        span={12}
                                                                        style={{padding: 16}}
                                                                        onClick={() => this.clickPic(video.bvid)}
                                                                    >
                                                                        <Card
                                                                            hoverable
                                                                            style={{Width: 320}}
                                                                            cover={
                                                                                <Image
                                                                                    referrerPolicy="no-referrer"
                                                                                    src={video.pic}
                                                                                    fallback="data:image/png;base64,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"
                                                                                />
                                                                            }
                                                                        >
                                                                            <Card.Meta
                                                                                title={video.title}
                                                                                description={video.author}
                                                                            />
                                                                        </Card>
                                                                    </Col>
                                                                );
                                                            })
                                                            : null}
                                                    </Row>
                                                </Card>

                                            ) : null}
                                            <p></p>

                                        </div>

                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </section>
            </div>
        );
    }
}
