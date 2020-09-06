import React, { Component } from "react";
import axios from "../../axios";
import QRCode from "qrcode.react";
import BilibiliHistorty from "../../bilibili/BilibiliHistorty";
import BilibiliEcharts from "../../bilibili/BilibiliEcharts";
import BilibiliUp from "../../bilibili/BilibiliUp";
import { Divider, Spin, message } from "antd";
import { Modal, Button } from "antd";
import { createBrowserHistory } from "history";
export default class BilibiliBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: null,
      iLifeId: null,
      oauthKey: null,
      QRCodeurl: null,
      needQRCode: false,
      userId: null,
      name: "",
      loading: false,
      SESSDATA: null,
      islogin: false,
      histories: null,
      updating: false,
      ModalText: "Content of the modal",
      visible: false,
      confirmLoading: false,
      FavorUp: null,
      FavorTag: null,
    };
    this.QRcodeLogin = this.QRcodeLogin.bind(this);
    this.getloginurl = this.getloginurl.bind(this);
    this.getResponse = this.getResponse.bind(this);
    this.getHistories = this.getHistories.bind(this);
    this.updateHistories = this.updateHistories.bind(this);
    this.getUserId = this.getUserId.bind(this);
    this.updateUserId = this.updateUserId.bind(this);
    this.handleOk = this.handleOk.bind(this);
    this.getFavorTag = this.getFavorTag.bind(this);
    this.getFavorUp = this.getFavorUp.bind(this);
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

    if (!username) {
      const history = createBrowserHistory();
      history.push("/login");
      window.location.reload();
    }

    this.setState({
      username: JSON.stringify(username),
    });

    const user = await this.getilifeId(username);
    if (user) {
      await this.getHistories(user.biliid);
      await this.getFavorUp(user.biliid);
      await this.getFavorTag(user.biliid);
    }
  }

  async getFavorUp(biliid) {
    var config = {
      method: "get",
      url: "http://18.166.111.161:8000/bilibili/bili/getFavorUp?mid=" + biliid,
      headers: { withCredentials: true },
    };

    const Up = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });
    this.setState({
      FavorUp: Up,
    });
    return Up;
  }

  async getFavorTag(biliid) {
    var config = {
      method: "get",
      url: "http://18.166.111.161:8000/bilibili/bili/getFavortag?mid=" + biliid,
      headers: { withCredentials: true },
    };

    const Tag = await axios(config)
      .then(function (response) {
        //console.log(JSON.stringify(response.data));
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });
    this.setState({
      FavorTag: Tag,
    });
    return Tag;
  }

  async QRcodeLogin() {
    this.setState({ islogin: false, SESSDATA: null });
    const QRcode = await this.getloginurl();
    //console.log(QRcode);
    if (QRcode) {
      this.setState({
        oauthKey: QRcode.oauthKey,
        QRCodeurl: QRcode.url,
        needQRCode: true,
        visible: true,
      });
      this.interval = setInterval(() => this.getResponse(), 2000);
    } else
      message.success({
        content: "请求失败，请重新查询！",
        style: { marginTop: "40px" },
      });
  }

  // 获取二维码，返回QRcode信息，失败返回null
  async getloginurl() {
    this.setState({ loading: true });
    const QRcode = await axios
      .get("http://18.166.111.161:8000/bilibili/bili/getloginurl", {
        headers: { withCredentials: true },
      })
      .then(function (response) {
        //console.log(JSON.stringify(response.data));
        if (response.data.status) return response.data.data;
        else return null;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });
    this.setState({ loading: false });

    return QRcode;
  }

  async getResponse() {
    var config = {
      method: "post",
      url:
        "http://18.166.111.161:8000/bilibili/bili/loginconfirm?oauthKey=" +
        this.state.oauthKey,
      headers: { withCredentials: true },
    };

    const ans = await axios(config)
      .then(function (response) {
        //console.log(JSON.stringify(response.data));
        if (response.data) return response.data;
        return null;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });

    if (ans) {
      sessionStorage.setItem("SESSDATA", ans);
      clearInterval(this.interval);
      const user = await this.getUserId(ans);
      const FavorUp = await this.getFavorUp(user.mid);
      const FavorTag = await this.getFavorTag(user.mid);
      this.setState({
        islogin: true,
        FavorTag: FavorTag,
        FavorUp: FavorUp,
        SESSDATA: ans,
      });
    }
  }

  async getUserId(ans) {
    var config = {
      method: "get",
      url:
        "http://18.166.111.161:8000/bilibili/bili/userinform?SESSDATA=" + ans,
      headers: { withCredentials: true },
    };

    const user = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });

    if (user) {
      this.getHistories(user.mid);

      this.setState({
        userId: user.mid,
        name: user.uname,
      });
    }
    return user;
  }

  async getHistories(userId) {
    var config = {
      method: "post",
      url:
        "http://18.166.111.161:8000/bilibili/bili/gethistory?mid=" +
        userId +
        "&page=0&size=100",
      headers: { withCredentials: true },
    };

    const histories = await axios(config)
      .then(function (response) {
        //console.log(JSON.stringify(response.data));
        return response.data.content;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });

    if (histories)
      this.setState({
        histories,
      });
  }

  async updateHistories() {
    this.setState({
      updating: true,
    });
    var config = {
      method: "get",
      url:
        "http://18.166.111.161:8000/bilibili/bili/updatehistory?SESSDATA=" +
        this.state.SESSDATA,
      headers: { withCredentials: true },
    };

    const ans = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        message.success({
          content: "刷新浏览记录成功！",
          style: { marginTop: "40px" },
        });
        return true;
      })
      .catch(function (error) {
        console.log(error);
        message.error({
          content: "数据更新失败！",
          style: { marginTop: "40px" },
        });
        return true;
      });

    if (ans) {
      this.setState({
        updating: false,
      });
      await this.getHistories(this.state.userId);
    }
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
        console.log(JSON.stringify(response.data));
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });

    if (user) {
      this.setState({
        iLifeId: user.id,
      });
    }
    return user;
  }

  async updateUserId() {
    let userId = this.state.iLifeId;
    let biliId = this.state.userId;
    console.log(biliId);
    let data1 = {
      userId: userId,
      biliId: biliId,
    };
    var config = {
      method: "post",
      data: data1,
      url: "http://18.166.111.161:8686/auth/updateBiliId",
      headers: {
        withCredentials: true,
      },
    };

    await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        message.success("更新成功！");
        return response;
      })
      .catch(function (error) {
        console.log(error);
        message.error("更新失败！");
      });
  }

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  async handleOk() {
    if (this.state.islogin) {
      await this.getilifeId(this.state.username);
      await this.updateUserId();
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
    const { visible, confirmLoading, ModalText } = this.state;
    return (
      <div className="content-wrapper">
        <section className="content" id="history">
          <div className="row">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <Divider
                    orientation="left"
                    style={{ color: "#333", fontWeight: "normal" }}
                  >
                    <h3 className="box-title">哔哩哔哩 (゜-゜)つロ 浏览记录</h3>
                  </Divider>
                </div>
                <div style={{ margin: 10 }}>
                  <Button type="primary" onClick={this.QRcodeLogin}>
                    二维码登录
                  </Button>
                  {this.state.SESSDATA ? (
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
                  title="二维码登录"
                  visible={visible}
                  onOk={this.handleOk}
                  confirmLoading={confirmLoading}
                  onCancel={this.handleCancel}
                  style={{
                    textAlign: "center",
                  }}
                >
                  {this.state.needQRCode ? (
                    !this.state.islogin ? (
                      <div className="form-group">
                        <p>请打开bilibli手机端扫描二维码</p>
                        <QRCode value={this.state.QRCodeurl} />
                      </div>
                    ) : (
                      <p>是否需要绑定帐号信息？</p>
                    )
                  ) : null}
                </Modal>
                <div className="box-body">
                  {<BilibiliHistorty histories={this.state.histories} />}
                </div>
              </div>
            </div>

            <div className="col-xs-12" id="analyse">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">哔哩哔哩 (゜-゜)つロ 个性分析</h3>
                </div>
                <div className="box-body">
                  <div className="col-xs-8">
                    <p>点击以展示up主的代表视频哦~</p>
                    <BilibiliUp
                      Up={this.state.FavorUp}
                      mid={this.state.userId}
                      sname={this.state.name}
                    />
                  </div>

                  <div className="col-xs-1">
                    <Divider
                      type="vertical"
                      style={{ height: "100%", minHeight: 500 }}
                    />
                  </div>
                  <div className="col-xs-3">
                    <p>你的专属标签</p>
                    <BilibiliEcharts Tag={this.state.FavorTag} />
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

//<img class="img-circle" src="http://i1.hdslb.com/bfs/archive/282cd207e78d24998a14d3c94370fde21faffaca.jpg" />
