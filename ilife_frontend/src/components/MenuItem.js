import React, { Component } from 'react'

export default class MenuItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      itemURL: "/alipay",
      itemName: "支付宝",
      chileItems: [{
        name: "账单信息",
        url: "/bills"
      },
      {
        name: "趋势分析",
        url: "/analyse"
      }
      ]
    }
  }
  render() {
    const { chileItems } = this.state;
    return (
      <li className="active treeview menu-open">
        <a href="fake_url">
          <i className="fa fa-dashboard" /> <span>{this.state.itemName}</span>
          <span className="pull-right-container">
            <i className="fa fa-angle-left pull-right" />
          </span>
        </a>
        <ul className="treeview-menu">
          {chileItems.map((childItem, index) => (
            <li key={index}><a href={childItem.url}><i className="fa fa-circle-o" />{childItem.name}</a></li>
          ))}
          <li className="active"><a href="index2.html"><i className="fa fa-circle-o" /> Dashboard v2</a></li>
        </ul>
      </li>
    )
  }
}
