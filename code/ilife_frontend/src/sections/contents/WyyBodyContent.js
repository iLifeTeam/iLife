import React, { Component } from "react";
import axios from "../../axios";
import WyyHistory from "../../wyy/WyyHistory";
import { Divider, Spin, message } from "antd";
import { Modal, Button } from "antd";
import { createBrowserHistory } from "history";
export default class WyyBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: null,
      account: "",
      password: "",
      histories: null,
      islogin: false,
      visible: false,
      confirmLoading: false,
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

  handleCancel = () => {
    this.setState({
      visible: false,
    });
  };
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
                  <div className="box-body"></div>
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
                  <Divider
                    orientation="left"
                    style={{ color: "#333", fontWeight: "normal" }}
                  >
                    <h3 className="box-title">听歌历史</h3>
                  </Divider>
                </div>
                <div style={{ margin: 10 }}>
                  <Button
                    type="primary"
                    onClick={() =>
                      this.setState({ islogin: false, visible: true })
                    }
                  >
                    网易云登录入口
                  </Button>
                  {this.state.islogin ? (
                    <Spin
                      spinning={this.state.updating}
                      style={{ width: "100%" }}
                    >
                      <Button type="primary" onClick={this.updateHistories}>
                        更新浏览记录
                      </Button>
                    </Spin>
                  ) : null}
                </div>
                <Modal
                  title="网易云帐号登录"
                  visible={this.state.visible}
                  onOk={this.login}
                  confirmLoading={this.state.confirmLoading}
                  onCancel={this.handleCancel}
                  style={{
                    textAlign: "center",
                  }}
                >
                  {this.state.islogin ? (
                    <p>是否需要绑定帐号信息？</p>
                  ) : (
                    <>
                      <div className="form-group">
                        <label htmlFor="exampleInputEmail1">
                          网易云音乐账号
                        </label>
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
                    </>
                  )}
                </Modal>
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
