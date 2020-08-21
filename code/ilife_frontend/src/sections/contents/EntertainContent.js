import React, { Component } from 'react'
import axios from 'axios';
import { createBrowserHistory } from 'history'
import {Button, Divider, Typography, Popconfirm, message} from 'antd';
import 'antd/dist/antd.css';
const { Text, Paragraph } = Typography;
const text = <div><Paragraph>'进行推荐前，请确保你已经绑定了豆瓣账户且进行了书籍和电影数据的获取，否则推荐结果将不太准确。</Paragraph>
    <Paragraph>所有数据和图片均来自网上公开资料，请放心食用。</Paragraph>
    <Paragraph>推荐将会花费一段时间，请耐心等待~</Paragraph></div>;
export default class EntertainContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            finish:false,
            pop:false,
            show:false,
            doubanId: null,
            username: "",
            password: "",
            movies:null,
            books:null,
            statsLoading: false,
            statsReady: false,
            stats: null,
        };
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
        this.getInfo(username);
    }

    async getInfo(username) {
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
    }

    /* end 文案 here*/
    async getMovies(doubanId) {
        var config = {
            method: 'get',
            url: 'http://121.36.196.234:8383/douban/getBooks?userId=' + doubanId,
            headers: {
                withCredentials: true,
            }
        };
        const movies = await axios(config)
            .then(function (response) {
                console.log(response.data);
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        this.setState({ books: movies });
        this.getBooks(doubanId);
    }
    getBooks=async (doubanId)=>{
        var config = {
            method: 'get',
            url: 'http://121.36.196.234:8383/douban/getMovies?userId=' + doubanId,
            headers: {
                withCredentials: true,
            }
        };

        const books = await axios(config)
            .then(function (response) {
                console.log(JSON.stringify(response.data));
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        this.setState({movies: books});
        this.getAttitude(doubanId);
    };
    getAttitude=async (doubanId)=>{
        let data=[];
        for(let movie of this.state.movies){
            data.push(movie.name)
        }
        for(let book of this.state.books){
            data.push(book.name)
        }
        console.log(data);
        //通过百度AI获取书籍和电影的情感倾向
        var config = {
            method: 'post',
            data:data,
            url: 'http://18.166.111.161:8188/baiduapi/analysis',
            headers: {
                withCredentials: true,
            }
        };
        const result = await axios(config)
            .then(function (response) {
                console.log(response.data);
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        let attitude;
        if(result.negative===0) attitude=1;
        else if(result.positive===0||result.positive/result.negative<=5) attitude=0;
        else attitude=1;
        var configRcmd = {
            method: 'get',
            url: 'http://121.36.196.234:8383/douban/getRcmd?userId=' + doubanId,
            headers: {
                withCredentials: true,
            }
        };
        const resultRcmd = await axios(configRcmd)
            .then(function (response) {
                console.log(response.data);
                return response.data;
            })
            .catch(function (error) {
                console.log(error);
            });
        let hashTag = resultRcmd.preAuthor.length*resultRcmd.preAuthor.length*this.state.movies.length;
        if (hashTag%7===0) hashTag+=1;
        let data2={
          "bookTagList":resultRcmd.bookTagList,
          "preAuthor":resultRcmd.preAuthor,
            "movieTagList":resultRcmd.movieTagList,
            "musicTag":resultRcmd.musicTag,
            "gameTag":resultRcmd.gameTag,
            "attitude":attitude,
            "hashTag":hashTag
        };
        const that = this;
        var config2 = {
            method: 'post',
            data:data2,
            url: 'http://121.36.196.234:8484//douban/crawlRcmd',
            headers: {
                withCredentials: true,
            }
        };
        const finalResult = await axios(config2)
            .then(function (response) {
                console.log(response.data);
                message.destroy();
                message.success({
                    content: "娱乐推荐完毕！请重新进入页面查看",
                    style: { marginTop: '40px' },
                });
                that.setState({finish:true});
                return response.data;

            })
            .catch(function (error) {
                console.log(error);
            });
        
    };
    confirm=()=>{
        message.loading({
            content: "正在进行娱乐推荐，请稍作等待！",
            style: { marginTop: '40px' },
            duration: 0
        });
        this.getMovies(this.state.doubanId);
        return null;
    };
    changeId=async (e)=>{
        let userId=localStorage.getItem("iLifeId");
        let dbId=document.getElementById("changeId").value;

        let data1={
            "userId":userId,
            "dbId":dbId
        };
        var config = {
            method: 'post',
            data:data1,
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
        if(doubanId) this.setState({doubanId:dbId})

    };
    render() {
        const { activities, stats, statsReady, statsLoading, userId } = this.state;
        return (
            <div className="content-wrapper">
                <section className="content">
                    < div className="row">
                        <div className="col-xs-12">
                            <div className="box">
                                <div className="box-header">
                                    <Divider orientation="left" style={{ color: '#333', fontWeight: 'normal' }}> <h3 className="box-title">{this.state.doubanId===null?"请先点击“绑定账户”绑定豆瓣用户！":"用户"+this.state.doubanId+"的娱乐推荐"}</h3></Divider>
                                    <Popconfirm
                                        placement="bottomLeft"
                                        title={text}
                                        onConfirm={this.confirm}
                                        okText="开始推荐"
                                        cancelText="再想想"
                                    >
                                        <p className="btn btn-danger" >开始推荐</p>
                                    </Popconfirm>
                                    <p className="btn btn-primary" onClick={()=>{this.setState({show:!this.state.show})}}>绑定账户</p>
                                    {this.state.show?
                                        <p><input id="changeId"/><button onClick={this.changeId}>确认</button></p>:null}
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        )
    }
}
