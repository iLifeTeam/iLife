import React, { Component } from 'react'
import axios from 'axios';
import { createBrowserHistory } from 'history'
import DoubanMovies from '../../douban/DoubanMovies';
import {Divider} from "antd";

export default class DbMovieContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      iLifeId:"",
      show:false,
      doubanId: null,
      username: "",
      password: "",
      activities: null
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
        localStorage.setItem("iLifeId",response.data.id);
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

  crawl=()=> {
    let config = {
      method: 'get',
      url: 'http://121.36.196.234:8484/douban/crawlMovie?userId=' + this.state.doubanId + '&limit=2&type=movie',
      headers: {
        withCredentials: true,
      }
    };
    let that =this;
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
    const { activities } = this.state;
    return (
      <div className="content-wrapper">
        <section className="content">
          < div className="row" >
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <Divider orientation="left" style={{ color: '#333', fontWeight: 'normal' }}> <h3 className="box-title">用户{this.state.doubanId}的电影数据</h3></Divider>
                  <p className="btn btn-primary" onClick={this.crawl}>更新数据</p>
                  <p className="btn btn-primary" onClick={()=>{this.setState({show:!this.state.show})}}>绑定账户</p>
                  {this.state.show?
                    <p><input id="changeId"/><button onClick={this.changeId}>确认</button></p>:null}
                </div>
                <div className="box-body">
                  {<DoubanMovies activities={activities} />}
                </div>
              </div>
            </div>
          </div >
        </section >
      </div >
    )
  }
}
