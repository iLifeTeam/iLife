import React, { Component } from 'react'
import axios from 'axios';
import { createBrowserHistory } from 'history'
import DoubanBooks from '../../douban/DoubanBooks';
import {Button, Divider, Typography} from 'antd';
import 'antd/dist/antd.css';
const { Text, Paragraph } = Typography;
export default class EntertainContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            pop:false,
            show:false,
            doubanId: null,
            username: "",
            password: "",
            activities: null,
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
                                    <p className="btn btn-danger" onClick={()=>{this.setState({pop:true})}}>开始推荐</p>
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
