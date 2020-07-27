import React, { Component } from 'react'
import MenuItem from './components/MenuItem'

import {
  Link
} from "react-router-dom";

export default class Menu extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: localStorage.getItem("username"),
    }
  }

  render() {
    console.log(this.state.username);
    const User = this.state.username === null ? <div className="pull-left info">
      <p ><Link to={"/login"}>登录</Link></p>
    </div> :
      <div className="pull-left info">
        <p>{this.state.username}</p>
        <a><i className="fa fa-circle text-success" /> Online</a>
      </div>;
    return (
      <div>{/* Left side column. contains the logo and sidebar */}
        <div className="main-sidebar">
          {/* sidebar: style can be found in sidebar.less */}
          <div className="sidebar">
            {/* Sidebar user panel */}
            <div className="user-panel">
              <div className="pull-left image">
                <img src="../../dist/img/user2-160x160.jpg" className="img-circle" alt="User" />
              </div>
              {User}
            </div>
            {/* search form */}
            <div action="#" method="get" className="sidebar-form">
              <div className="input-group">
                <input type="text" name="q" className="form-control" placeholder="Search..." />
                <span className="input-group-btn">
                  <button type="submit" name="search" id="search-btn" className="btn btn-flat">
                    <i className="fa fa-search" />
                  </button>
                </span>
              </div>
            </div>
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
          </div>
          {/* /.sidebar */}
        </div>
      </div>

    )
  }
}


const menuItems = [
  {
    itemURL: "/home/alipay",
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
    itemURL: "/home/weibo",
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
    itemURL: "/home/zhihu",
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
  }, {
    itemURL: "/home/wyy",
    itemName: "网易云",
    childItems: [{
      name: "听歌记录",
      url: "/history"
    },
    {
      name: "分析",
      url: "/analyse"
    }
    ]
  },
  {
    itemURL: "/home/bilibili",
    itemName: "b站",
    childItems: [{
      name: "浏览记录",
      url: "/history"
    },
    {
      name: "分析",
      url: "/analyse"
    }
    ]
  },
  {
    itemURL: "/home/taobao",
    itemName: "淘宝",
    childItems: [{
      name: "浏览记录",
      url: "/bills"
    },
    {
      name: "分析",
      url: "/analyse"
    }
    ]
  },
  {
    itemURL: "/home/jingdong",
    itemName: "京东",
    childItems: [{
      name: "消费记录",
      url: "/bills"
    },
      {
        name: "分析",
        url: "/analyse"
      }
    ]
  }
]