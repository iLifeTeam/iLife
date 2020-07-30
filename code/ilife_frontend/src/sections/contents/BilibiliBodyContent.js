import React, { Component } from 'react'
import axios from 'axios'
import QRCode from 'qrcode.react'
import BilibiliHistorty from '../../bilibili/BilibiliHistorty';
export default class BilibiliBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      oauthKey: null,
      QRCodeurl: null,
      needQRCode: false,
      userId: null,
      name: "",
      loading: false,
      SESSDATA: null,
      islogin: false,
      histories: null,
    }
    this.QRcodeLogin = this.QRcodeLogin.bind(this);
    this.getResponse = this.getResponse.bind(this);
    this.getHistories = this.getHistories.bind(this);
    this.getUserId = this.getUserId.bind(this);
    this.updateHistories = this.updateHistories.bind(this);
  }

  componentDidMount() {

  }

  async QRcodeLogin() {
    const QRcode = await axios.get("http://47.97.206.169:8848/bili/getloginurl", { headers: { withCredentials: true } })
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        if (response.data.status) return response.data.data;
        else return null;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });

    if (QRcode) {
      this.setState({
        oauthKey: QRcode.oauthKey,
        QRCodeurl: QRcode.url,
        needQRCode: true,
        loading: true,
      })
      this.interval = setInterval(() => this.getResponse(), 2000);
    }

    else alert("请求失败，请重新尝试一下。");
  }

  async getResponse() {
    var config = {
      method: 'post',
      url: 'http://47.97.206.169:8848/bili/loginconfirm?oauthKey=' + this.state.oauthKey,
      headers: { withCredentials: true }
    };

    const ans = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        if (response.data) return response.data;
        return null;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });

    if (ans) {
      this.getUserId(ans);
      clearInterval(this.interval);
      this.setState({
        loading: false,
        SESSDATA: ans,
        islogin: true,
      })
    }

  }

  async getUserId(ans) {
    var config = {
      method: 'get',
      url: 'http://47.97.206.169:8848/bili/userinform?SESSDATA=' + ans,
      headers: { withCredentials: true }
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
      })
    }

  }

  async getHistories(userId) {
    var config = {
      method: 'post',
      url: 'http://47.97.206.169:8848/bili/gethistory?mid=' + userId + '&page=0&size=100',
      headers: { withCredentials: true }
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

    if (histories) this.setState({
      histories
    })

  }
  updateHistories() {
    var config = {
      method: 'get',
      url: 'http://47.97.206.169:8848/bili/updatehistory?SESSDATA=' + this.state.SESSDATA,
      headers: { withCredentials: true }
    };

    axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        alert("刷新浏览记录成功！")
      })
      .catch(function (error) {
        console.log(error);
        alert("刷新失败！");
      });
  }

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  render() {

    return (
      <div className="content-wrapper">
        <section className="content">
          <div className="row">
            <div className="col-md-6" >
              <div className="box box-primary" style={{ height: 200 }}>
                <div className="box-header with-border">
                  <h3 className="box-title">二维码登录</h3>
                </div>
                <form role="form">
                  <div className="box-body">
                    <div className="form-group">
                      <p id="QRcodeButton" className="btn btn-primary" onClick={this.QRcodeLogin}>点击以获取二维码</p>
                    </div>
                    {this.state.SESSDATA ? <div className="form-group">
                      <p id="Update" className="btn btn-primary" onClick={this.updateHistories}>更新浏览记录</p>
                    </div> : null}
                  </div>
                </form>
              </div>
            </div>
            {this.state.needQRCode && !this.state.islogin ?
              <div className="col-md-6" >
                <div className="box box-primary" style={{ height: 200 }}>
                  <div className="box-header with-border">
                    <h3 className="box-title">请打开bilibli手机端扫描二维码</h3>
                  </div>

                  <div className="box-body">
                    <div className="form-group">
                      <QRCode value={this.state.QRCodeurl} />
                    </div>
                  </div>
                </div></div>
              : null
            }
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">哔哩哔哩 (゜-゜)つロ 浏览记录</h3>
                </div>
                <div className="box-body">
                  {<BilibiliHistorty histories={this.state.histories} />}
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>

    )
  }
}