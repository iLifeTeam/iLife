import React, { Component } from 'react'
import axios from 'axios'
import QRCode from 'qrcode.react'

export default class BilibiliBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      oauthKey: "",
      QRCodeurl: ""
    }
    this.QRcodeLogin = this.QRcodeLogin.bind(this);
  }

  componentDidMount() {

  }

  async QRcodeLogin() {
    var QRcode;
    var config = {
      method: 'get',
      url: 'http://passport.bilibili.com/qrcode/getLoginUrl',
      headers: {
        "Access-Control-Allow-Origin": "*",
      }
    };

    await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        if (response.data.status) {
          QRcode = response.data.data;
        }
      })


    this.setState({
      oauthKey: QRcode.oauthKey,
      QRCodeurl: QRcode.QRCodeurl,
    })
  }

  render() {
    return (
      <div className="content-wrapper">
        <section className="content">
          <div className="box box-primary">
            <div className="box-header with-border">
              <h3 className="box-title" onClick={this.QRcodeLogin}>二维码登录</h3>
            </div>
          </div>
          <span>
            <QRCode value={this.state.QRCodeurl} />
          </span>
        </section>
      </div>

    )
  }
}
