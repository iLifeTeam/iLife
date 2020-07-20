import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { createBrowserHistory } from 'history'

export default class Account extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: localStorage.getItem("username"),
    }
  }

  logoff() {
    const history = createBrowserHistory();
    localStorage.clear();
    history.push("/login");
    window.location.reload();
  }

  render() {
    if (this.state.username === null) return (
      <li className="user user-menu">
        <Link to={"/login"}>登录</Link>
      </li>
    )
    return (
      /* User Account: style can be found in dropdown.less */
      <li className="dropdown user user-menu">
        <a href="fake_url" className="dropdown-toggle" data-toggle="dropdown">
          <img src="../../dist/img/user2-160x160.jpg" className="user-image" alt="User" />
          <span className="hidden-xs">Alexander Pierce</span>
        </a>
        <ul className="dropdown-menu">
          {/* User image */}
          <li className="user-header">
            <img src="../../dist/img/user2-160x160.jpg" className="img-circle" alt="User" />
            <p>
              Alexander Pierce - Web Developer
      <small>Member since Nov. 2012</small>
            </p>
          </li>
          <li className="user-footer">
            <div className="pull-left">
              <a href="/setting" className="btn btn-default btn-flat">设置</a>
            </div>
            <div className="pull-right">
              <p onClick={this.logoff} className="btn btn-default btn-flat">注销</p>
            </div>
          </li>
        </ul>
      </li>

    )
  }
}
