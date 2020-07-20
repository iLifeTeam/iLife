import React, { Component } from 'react'

export default class Notification extends Component {
  constructor(props) {
    super(props);
    this.state = {
      noteTotal: 10,
      notifications: [],
    }
  }

  render() {
    return (
      /* Notifications: style can be found in dropdown.less */
      <li className="dropdown notifications-menu">
        <a href="fake_url" className="dropdown-toggle" data-toggle="dropdown">
          <i className="fa fa-bell-o" />
          <span className="label label-warning">{this.state.noteTotal}</span>
        </a>
        <ul className="dropdown-menu">
          <li className="header">你有{this.state.noteTotal}条未读消息</li>
          <li>
            {/* inner menu: contains the actual data */}
            <ul className="menu">
              {notifications.map((notification, index) => (
                <li key={index}>
                  <a >
                    <i className={notification.className} /> {notification.content}
                  </a>
                </li>
              ))}


            </ul>
          </li>
          <li className="footer"><a href="fake_url">查看所有消息</a></li>
        </ul>
      </li>

    )
  }
}


const notifications = [
  {
    href: "fake_url",
    className: "fa fa-users text-aqua",
    content: "5 new members joined today"
  },
  {
    href: "fake_url",
    className: "fa fa-warning text-yellow",
    content: "Very long description here that may not fit into the page and may cause design problems"
  },
  {
    href: "fake_url",
    className: "fa fa-users text-red",
    content: "5 new members joined"
  },
  {
    href: "fake_url",
    className: "fa fa-shopping-cart text-green",
    content: "25 sales made"
  },
  {
    href: "fake_url",
    className: "fa fa-user text-red",
    content: "You changed your username"
  }
]