import React, { Component } from "react";
import axios from "../../axios";
import WyyHistory from "../../wyy/WyyHistory";
import storageUtils from "../../storageUtils";
import { createBrowserHistory } from "history";
export default class WyyBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: null,
      account: "",
      password: "",
      histories: null,
    };
    this.updateHistory = this.updateHistory.bind(this);
    this.getHistory = this.getHistory.bind(this);
    this.login = this.login.bind(this);
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
  }

  accountOnChange(val) {
    this.setState({
      account: val.target.value,
    });
  }

  psdOnChange(val) {
    this.setState({
      password: val.target.value,
    });
  }

  updateHistory() {
    if (this.state.id === null) {
      alert("您尚未登录，请先登录！");
      return;
    }

    var config = {
      method: "post",
      url:
        "http://18.166.111.161:8808/wyy/music/updatehistorybyid?id=" +
        this.state.id,
      headers: { withCredentials: true },
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
        } else alert("刷新失败！");
      });
  }

  async getHistory() {
    if (this.state.id === null) {
      alert("您尚未登录，请先登录！");
      return;
    }
    var config = {
      method: "post",
      url:
        "http://18.166.111.161:8808/wyy/music/gethistorybyid?page=0&size=100&id=" +
        this.state.id,
      headers: { withCredentials: true },
    };

    var histories;
    await axios(config)
      .then(function (response) {
        console.log(response.data.content);
        histories = response.data.content;
      })
      .catch(function (error) {
        console.log(error);
      });

    this.setState({
      histories: histories,
    });
  }

  async login() {
    var data;
    var config = {
      method: "post",
      url:
        "http://18.166.111.161:8808/wyy/music/getid?ph=" +
        this.state.account +
        "&pw=" +
        this.state.password,
      headers: { withCredentials: true },
    };
    await axios(config)
      .then(function (response) {
        console.log(response);
        data = response.data;
      })
      .catch(function (error) {
        console.log(error.response);
        alert(error.response);
      });

    this.setState({
      id: data,
    });
  }

  render() {
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
                      <input
                        type="email"
                        className="form-control"
                        id="exampleInputEmail1"
                        placeholder="Enter email"
                        onChange={(val) => this.accountOnChange(val)}
                      />
                    </div>
                    <div className="form-group">
                      <label htmlFor="exampleInputPassword1">密码</label>
                      <input
                        type="password"
                        className="form-control"
                        id="exampleInputPassword1"
                        placeholder="Password"
                        onChange={(val) => this.psdOnChange(val)}
                      />
                    </div>
                  </div>
                </form>
                {/* /.box-body */}
                <div className="box-footer">
                  <p className="btn btn-primary" onClick={this.login}>
                    提交
                  </p>
                </div>
              </div>
            </div>
            <div className="col-md-3">
              <div className="box box-primary">
                <div className="box-header with-border">
                  <h3 className="box-title">用户操作</h3>
                </div>

                <form role="form">
                  <div className="box-body">
                    <div className="form-group">
                      <p
                        className="btn btn-primary"
                        onClick={this.updateHistory}
                      >
                        重新获取听歌历史
                      </p>
                      <p className="btn btn-primary" onClick={this.getHistory}>
                        查看当前听歌历史
                      </p>
                    </div>
                  </div>
                </form>
                <div className="box-footer"></div>
              </div>
            </div>
          </div>

          <div className="row" id="history">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">听歌历史</h3>
                </div>
                <div className="box-body">
                  {this.state.histories ? (
                    <WyyHistory histories={this.state.histories}></WyyHistory>
                  ) : null}
                </div>
              </div>
            </div>
          </div>

          <div className="row" id="analyse"></div>
        </section>
      </div>
    );
  }
}
