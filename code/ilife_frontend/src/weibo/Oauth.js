import React, { Component } from 'react'
import axios from 'axios'

export default class Oauth extends Component {
  constructor(props) {
    super(props);
    this.state = {
      token: "",
      url: ""
    }
  }
  oauthLink() {
    console.log("isdkugfsd");
    axios.post('http://49.234.125.131:80/weibo/wiki/Oauth2/authorize',
      {
        client_id: '2181274558',
        redirect_uri: 'https://api.weibo.com/oauth2/default.html',

      })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);

      })
  }
  render() {
    return (
      <button onClick={this.oauthLink} ><b>WeiboLink</b></button>
    )
  }
}
