import React, { Component } from 'react';
import AlipaySDK from 'alipay-sdk';

export default class AlipayOauth extends Component {
  constructor(props) {
    super(props);
    this.state = {
      token: "",
      url: ""
    }
  }

  alipayOauth() {
    // 普通公钥模式
    const fs = require('fs');
    const fileUrl = new URL('./private_key.pem');
    const alipaySdk = new AlipaySdk({
      // 参考下方 SDK 配置
      appId: '2016123456789012',
      privateKey: fs.readFileSync(fileUrl, 'ascii'),
    });
  }

  render() {
    return (
      <div>

      </div>
    )
  }
}