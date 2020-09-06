import React, { Component } from "react";
import axios from "../../axios";
import WyyHistory from "../../wyy/WyyHistory";
import { Divider, Spin, message } from "antd";
import { Modal, Button } from "antd";
import { createBrowserHistory } from "history";
import WyyFavor from "../../wyy/WyyFavor";
import WyySingers from "../../wyy/WyySingers";
export default class WyyBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: null,
      iLifeId: null,
      account: "",
      password: "",
      histories: null,
      islogin: false,
      visible: false,
      confirmLoading: false,
      updating: false,
      FavorSong: null,
      singers: null,
    };
    this.updateHistory = this.updateHistory.bind(this);
    this.getHistory = this.getHistory.bind(this);
    this.getSingers = this.getSingers.bind(this);
    this.login = this.login.bind(this);
    this.handleOk = this.handleOk.bind(this);
    this.updateId = this.updateId.bind(this);
  }

  async componentDidMount() {
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

    const user = await this.getilifeId(username);
    this.getHistory(user.wyyid);
    this.getSingers(user.wyyid);
  }

  async getSingers(id) {
    var config = {
      method: "post",
      url:
        "http://18.166.111.161:8000/music-service/music/getFavorSingers?uid=" +
        id,
      headers: {
        withCredentials: true,
      },
    };

    const singers = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });
    if (singers) {
      this.setState({
        singers: singers,
      });
    }
    return singers;
  }

  async getilifeId(username) {
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

    if (user) {
      this.setState({
        iLifeId: user.id,
        id: user.wyyid,
      });
    }
    return user;
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

  updateHistory(id) {
    if (id === null) {
      alert("您尚未登录，请先登录！");
      return;
    }

    var config = {
      method: "post",
      url:
        "http://18.166.111.161:8000/music-service/music/updatehistorybyid?id=" +
        id,
      headers: { withCredentials: true },
    };

    axios(config)
      .then(function (response) {
        //console.log(JSON.stringify(response.data));
        if (response.data === true) {
          message.success("听歌历史刷新成功！");
        }
      })
      .catch(function (error) {
        console.log(error);
        var response = error.response;
        if (response.status === 401) {
          alert("用户未授权或登录已过期！");
        } else message.error("刷新失败！");
      });
  }

  async getHistory(id) {
    if (id === null) {
      alert("您尚未登录，请先登录！");
      return;
    }
    var FavorSong = [];
    var config = {
      method: "post",
      url:
        "http://18.166.111.161:8000/music-service/music/gethistorybyid?page=0&size=100&id=" +
        id,
      headers: { withCredentials: true },
    };

    var histories;
    await axios(config)
      .then(function (response) {
        console.log(response.data.content);
        histories = response.data.content;
        for (var i = 0; i < histories.length; i++) {
          if (histories[i].score > 80) FavorSong.push(histories[i]);
        }
        console.log(FavorSong);
      })
      .catch(function (error) {
        console.log(error);
      });

    this.setState({
      FavorSong: FavorSong,
      histories: histories,
    });
  }

  async login() {
    var config = {
      method: "post",
      url:
        "http://18.166.111.161:8000/music-service/music/getid?ph=" +
        this.state.account +
        "&pw=" +
        this.state.password,
      headers: { withCredentials: true },
    };
    const id = await axios(config)
      .then(function (response) {
        console.log(response);
        return response.data;
      })
      .catch(function (error) {
        console.log(error.response);
        return null;
      });

    this.setState({
      id: id,
      islogin: true,
    });
    return id;
  }

  async updateId() {
    var data = { userId: this.state.iLifeId, wyyId: this.state.id };

    var config = {
      method: "post",
      url: "http://18.166.111.161:8686/auth/updateWyyId",
      headers: {
        withCredentials: true,
      },
      data: data,
    };

    axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        message.success("更新成功！");
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        message.error("更新失败！");
      });

    this.getHistory(this.state.id);
  }

  handleOk() {
    if (this.state.islogin) {
      this.updateId();
    }
    this.setState({
      visible: false,
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
          <div className="row" id="history">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <Divider
                    orientation="left"
                    style={{ color: "#333", fontWeight: "normal" }}
                  >
                    <h3 className="box-title">网易云听歌历史</h3>
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
                  {this.state.id ? (
                    <Spin
                      spinning={this.state.updating}
                      style={{ width: "100%" }}
                    >
                      <Button
                        type="primary"
                        onClick={() => this.updateHistory(this.state.id)}
                      >
                        更新本账号记录
                      </Button>
                    </Spin>
                  ) : null}
                </div>
                <Modal
                  title="网易云帐号登录"
                  visible={this.state.visible}
                  onOk={this.handleOk}
                  onCancel={this.handleCancel}
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
                      <Button type="primary" onClick={this.login}>
                        登录
                      </Button>
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

            <div className="col-xs-12" id="analyse">
              <div className="box">
                <div className="box-header">
                  <Divider
                    orientation="left"
                    style={{ color: "#333", fontWeight: "normal" }}
                  >
                    <h3 className="box-title">网易云偏好分析</h3>
                  </Divider>
                </div>
                <div className="box-body">
                  <div className="col-xs-8">
                    <p>
                      <b>偏好歌曲推荐</b>
                      <br />
                      点击以获取相似歌曲~
                    </p>
                    <WyyFavor
                      Songs={this.state.FavorSong}
                      mid={this.state.id}
                    />
                  </div>

                  <div className="col-xs-1">
                    <Divider
                      type="vertical"
                      style={{ height: "100%", minHeight: 500 }}
                    />
                  </div>
                  <div className="col-xs-3">
                    <p>你的专属歌手</p>
                    <WyySingers singers={this.state.singers} />
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
