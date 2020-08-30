import React, { Component } from 'react'
import MenuItem from './components/MenuItem'

import {
  withRouter
} from "react-router-dom";

class Menu extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: window.sessionStorage.getItem("username"),
    }
  }

  login() {
    this.props.history.push('/login')
  }

  render() {
    console.log(this.state.username);
    const User = this.state.username === null ? <div className="pull-left info">
      <span onClick={() => this.login()}><p >登录</p></span>
    </div> :
      <div className="pull-left info">
        <p>{this.state.username}</p>
        <a><i className="fa fa-circle text-success" /> Online</a>
      </div>;
    return (
      <div >{/* Left side column. contains the logo and sidebar */}
        <div className="main-sidebar" >
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
              <li className="header">分析</li>
              <li><a href="/home/entertain"><i className="fa fa-circle-o text-red" /> <span>娱乐推荐</span></a></li>
              <li><a href="fake_url"><i className="fa fa-circle-o text-yellow" /> <span>博主推荐</span></a></li>
            </ul>
          </div>
          {/* /.sidebar */}
        </div>
      </div>

    )
  }
}
export default withRouter(Menu);

const menuItems = [
  {
    itemURL: "/home/weibo",
    itemName: "微博",
    childItems: [{
      name: "浏览信息",
      id: "info"
    },
    {
      name: "趋势分析",
      id: "analyse"
    }
    ]
  },
  {
    itemURL: "/home/zhihu",
    itemName: "知乎",
    childItems: [{
      name: "动态信息",
      id: "info"
    },
    {
      name: "分析",
      id: "analyse"
    }
    ]
  }, {
    itemURL: "/home/wyy",
    itemName: "网易云",
    childItems: [{
      name: "听歌记录",
      id: "history"
    },
    {
      name: "分析",
      id: "analyse"
    }
    ]
  },
  {
    itemURL: "/home/bilibili",
    itemName: "b站",
    childItems: [{
      name: "浏览记录",
      id: "history"
    },
    {
      name: "分析",
      id: "analyse"
    }
    ]
  },
  {
    itemURL: "/home/taobao",
    itemName: "淘宝",
    childItems: [{
      name: "浏览记录",
      id: "bills"
    },
    {
      name: "分析",
      id: "analyse"
    }
    ]
  },
  {
    itemURL: "/home/jingdong",
    itemName: "京东",
    childItems: [{
      name: "消费记录",
      id: "bills"
    },
    {
      name: "分析",
      id: "analyse"
    }
    ]
  },
  {
    itemURL: "/home/book",
    itemName: "书籍",
    childItems: [{
      name: "豆瓣图书记录",
      id: "records"
    },
    {
      name: "豆瓣图书分析",
      id: "analyse"
    }
    ]
  },
  {
    itemURL: "/home/movie",
    itemName: "电影",
    childItems: [{
      name: "豆瓣观影记录",
      id: "records"
    },
    {
      name: "豆瓣观影分析",
      id: "analyse"
    }
    ]
  }
];