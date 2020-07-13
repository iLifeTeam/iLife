import React, { Component } from 'react'
import MenuItem from '../components/MenuItem'

import {
  Link
} from "react-router-dom";

export default class Menu extends Component {
  render() {
    return (
      <div>{/* Left side column. contains the logo and sidebar */}
        <aside className="main-sidebar">
          {/* sidebar: style can be found in sidebar.less */}
          <section className="sidebar">
            {/* Sidebar user panel */}
            <div className="user-panel">
              <div className="pull-left image">
                <img src="dist/img/user2-160x160.jpg" className="img-circle" alt="User" />
              </div>
              <div className="pull-left info">
                <p>Alexander Pierce</p>
                <a><i className="fa fa-circle text-success" /> Online</a>
              </div>
            </div>
            {/* search form */}
            <form action="#" method="get" className="sidebar-form">
              <div className="input-group">
                <input type="text" name="q" className="form-control" placeholder="Search..." />
                <span className="input-group-btn">
                  <button type="submit" name="search" id="search-btn" className="btn btn-flat">
                    <i className="fa fa-search" />
                  </button>
                </span>
              </div>
            </form>
            {/* /.search form */}
            {/* sidebar menu: : style can be found in sidebar.less */}
            <ul className="sidebar-menu" data-widget="tree">
              <li className="header">导航栏</li>
              {menuItems.map((menuItem, index) =>
                <MenuItem key={index} itemURL={menuItem.itemURL} itemName={menuItem.itemName} childItems={menuItem.childItems} />
              )}
              <li className="header">LABELS</li>
              <li><a href="fake_url"><i className="fa fa-circle-o text-red" /> <span>Important</span></a></li>
              <li><a href="fake_url"><i className="fa fa-circle-o text-yellow" /> <span>Warning</span></a></li>
              <li><a href="fake_url"><i className="fa fa-circle-o text-aqua" /> <span>Information</span></a></li>
            </ul>
          </section>
          {/* /.sidebar */}
        </aside>
      </div>

    )
  }
}


const menuItems = [
  {
    itemURL: "/alipay",
    itemName: "支付宝",
    childItems: [{
      name: "账单信息",
      url: "/bills"
    },
    {
      name: "趋势分析",
      url: "/analyse"
    }
    ]
  },
  {
    itemURL: "/weibo",
    itemName: "微博",
    childItems: [{
      name: "浏览信息",
      url: "/info"
    },
    {
      name: "趋势分析",
      url: "/analyse"
    }
    ]
  },
  {
    itemURL: "/zhihu",
    itemName: "知乎",
    childItems: [{
      name: "动态信息",
      url: "/info"
    },
    {
      name: "分析",
      url: "/analyse"
    }
    ]
  }
]