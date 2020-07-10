import React, { Component } from 'react'

export default class Account extends Component {
  render() {
    return (
      /* User Account: style can be found in dropdown.less */
      <li className="dropdown user user-menu">
        <a href="fake_url" className="dropdown-toggle" data-toggle="dropdown">
          <img src="dist/img/user2-160x160.jpg" className="user-image" alt="User" />
          <span className="hidden-xs">Alexander Pierce</span>
        </a>
        <ul className="dropdown-menu">
          {/* User image */}
          <li className="user-header">
            <img src="dist/img/user2-160x160.jpg" className="img-circle" alt="User" />
            <p>
              Alexander Pierce - Web Developer
      <small>Member since Nov. 2012</small>
            </p>
          </li>
          {/* Menu Body 
        <li className="user-body">
          <div className="row">
            <div className="col-xs-4 text-center">
              <a href="fake_url">Followers</a>
            </div>
            <div className="col-xs-4 text-center">
              <a href="fake_url">Sales</a>
            </div>
            <div className="col-xs-4 text-center">
              <a href="fake_url">Friends</a>
            </div>
          </div>
           /.row 
        </li>*/}
          {/* Menu Footer*/}
          <li className="user-footer">
            <div className="pull-left">
              <a href="/setting" className="btn btn-default btn-flat">设置</a>
            </div>
            <div className="pull-right">
              <a href="#" className="btn btn-default btn-flat">注销</a>
            </div>
          </li>
        </ul>
      </li>

    )
  }
}
