import React, { Component } from "react";
import { Link } from "react-router-dom";
import { createBrowserHistory } from "history";
import axios from "../../axios";
import storageUtils from "../../storageUtils";

export default class Account extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: storageUtils.getUser(),
    };
  }

  logoff() {
    const history = createBrowserHistory();
    storageUtils.deleteUser();

    let username = document.cookie;
    var cookie_pos = username.indexOf("username");

    if (cookie_pos != -1) {
      document.cookie =
        "username" + "=" + " " + ";expires=" + new Date().toGMTString();
    }

    var config = {
      method: "post",
      url: "http://18.162.168.229:8686/logout",
      headers: { withCredentials: true },
    };

    axios(config).then(function (response) {
      console.log(JSON.stringify(response.data));
    });

    history.push("/login");
    window.location.reload();
  }

  render() {
    if (this.state.username === null)
      return (
        <li className="user user-menu">
          <Link to={"/login"}>登录</Link>
        </li>
      );
    return (
      /* User Account: style can be found in dropdown.less */
      <li className="dropdown user user-menu">
        <a href="fake_url" className="dropdown-toggle" data-toggle="dropdown">
          <img
            src="../../dist/img/user2-160x160.jpg"
            className="user-image"
            alt="User"
          />
          <span className="hidden-xs">{this.state.username}</span>
        </a>
        <ul className="dropdown-menu">
          {/* User image */}
          <li className="user-header">
            <img
              src="../../dist/img/user2-160x160.jpg"
              className="img-circle"
              alt="User"
            />
            <p>
              {this.state.username}
              <small>Member since Nov. 2012</small>
            </p>
          </li>
          <li className="user-footer">
            <div className="pull-left">
              <a href="/setting" className="btn btn-default btn-flat">
                设置
              </a>
            </div>
            <div className="pull-right">
              <p onClick={this.logoff} className="btn btn-default btn-flat">
                注销
              </p>
            </div>
          </li>
        </ul>
      </li>
    );
  }
}
