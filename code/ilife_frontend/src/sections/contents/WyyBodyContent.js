import React, { Component } from 'react'
import axios from 'axios'

export default class WyyBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: 3220012996,
      account: "",
      password: "",
      histories: null,
    }
    this.updateHistory = this.updateHistory.bind(this);
    this.getHistory = this.getHistory.bind(this);
    this.login = this.login.bind(this);
  }
  componentDidMount() {
    const script = document.createElement("script");

    script.src = "../../dist/js/content.js";
    script.async = true;
    document.body.appendChild(script);

  }

  accountOnChange(val) {
    this.setState({
      account: val.target.value,
    })
  }

  psdOnChange(val) {
    this.setState({
      password: val.target.value,
    })
  }

  updateHistory() {
    if (this.state.id === null) {
      alert("您尚未登录，请先登录！");
      return;
    }

    var config = {
      method: 'post',
      url: 'http://47.97.206.169:8888/music/updatehistorybyid?id=' + this.state.id,
      headers: {}
    };

    axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        if (response.data === true) {
          alert("听歌历史刷新成功！");
        }
      })
      .catch(function (error) {
        console.log(error);
        var response = error.response;
        if (response.status === 401) {
          alert("用户未授权或登录已过期！");
        }
        else alert("刷新失败！");
      });

  }

  async getHistory() {
    if (this.state.id === null) {
      alert("您尚未登录，请先登录！");
      return;
    }
    var config = {
      method: 'post',
      url: 'http://47.97.206.169:8888/music/gethistorybyid?page=0&size=10&id=' + this.state.id,
      headers: {}
    };

    var histories;
    await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        histories = response.data.content;
      })
      .catch(function (error) {
        console.log(error);
      });

    this.setState({
      histories: histories
    })
  }

  async login() {
    var data;
    var config = {
      method: 'post',
      url: 'http://47.97.206.169:8888/music/getid?ph=' + this.state.account + '&pw=' + this.state.password,
      headers: {
        'Content-Type': 'application/json'
      },
    };
    await axios(config)
      .then(function (response) {
        console.log(response);
        data = response.data;
      })
      .catch(function (error) {
        console.log(error.response);
        alert(error.response);
      })

    this.setState({
      id: data
    })
  }

  render() {
    const { histories } = this.state;

    const body = histories ? histories.map((history, index) => (<tr>
      <td>{history.hisid}</td>
      <td>{history.musics.mname}</td>
      <td>{history.musics.singers.sname}</td>
    </tr>)) : null;


    return (
      <div className="content-wrapper">
        <section className="content">
          <div className="row">
            <div className="col-md-9">
              <div className="box box-primary">
                <div className="box-header with-border">
                  <h3 className="box-title">登录</h3>
                </div>
                {/* form start */}
                <form role="form">
                  <div className="box-body">
                    <div className="form-group">
                      <label htmlFor="exampleInputEmail1">网易云音乐账号</label>
                      <input type="email" className="form-control" id="exampleInputEmail1" placeholder="Enter email"
                        onChange={(val) => this.accountOnChange(val)} />
                    </div>
                    <div className="form-group">
                      <label htmlFor="exampleInputPassword1">密码</label>
                      <input type="password" className="form-control" id="exampleInputPassword1" placeholder="Password"
                        onChange={(val) => this.psdOnChange(val)} />
                    </div>
                  </div>
                </form>
                {/* /.box-body */}
                <div className="box-footer">
                  <p className="btn btn-primary" onClick={this.login}>提交</p>
                </div>
              </div>
            </div>
            <div className="col-md-3" >
              <div className="box box-primary">
                <div className="box-header with-border">
                  <h3 className="box-title">用户操作</h3>
                </div>

                <form role="form">
                  <div className="box-body">
                    <div className="form-group">
                      <p className="btn btn-primary" onClick={this.updateHistory}>重新获取听歌历史</p>
                      <p className="btn btn-primary" onClick={this.getHistory}>查看当前听歌历史</p>
                    </div>
                  </div>
                </form>
                <div className="box-footer">
                </div>
              </div>
            </div>
          </div>

          <div className="row">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">听歌历史</h3>
                </div>
                <div className="box-body">
                  <table id="example1" className="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>序号</th>
                        <th>歌曲名</th>
                        <th>歌手</th>
                      </tr>
                    </thead>
                    <tbody>
                      {body}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div >
    )
  }
}
