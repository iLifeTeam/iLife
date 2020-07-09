import React, { Component } from 'react'
import Notification from '../components/Notification'
import Task from '../components/Task'
import Account from '../components/Account'

export default class Header extends Component {
  render() {
    return (
      <div>
        <header className="main-header">
          {/* Logo */}
          <a className="logo">
            {/* mini logo for sidebar mini 50x50 pixels */}
            <span className="logo-mini"><b>iLife</b></span>
            {/* logo for regular state and mobile devices */}
            <span className="logo-lg"><b>iLife</b></span>
          </a>
          {/* Header Navbar: style can be found in header.less */}
          <nav className="navbar navbar-static-top">
            {/* Sidebar toggle button*/}
            <a href="fake_url" className="sidebar-toggle" data-toggle="push-menu" role="button">
              <span className="sr-only">Toggle navigation</span>
            </a>
            {/* Navbar Right Menu */}
            <div className="navbar-custom-menu">
              <ul className="nav navbar-nav">
                <Notification />
                <Task />
                <Account />
                {/* Control Sidebar Toggle Button */}
                <li>
                  <a href="fake_url" data-toggle="control-sidebar"><i className="fa fa-gears" /></a>
                </li>
              </ul>
            </div>
          </nav>
        </header>
      </div>

    )
  }
}