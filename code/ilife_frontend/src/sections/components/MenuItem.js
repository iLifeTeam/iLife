import React, { Component } from 'react'
import { Link } from 'react-router-dom'

export default class MenuItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
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
    }
  }

  componentDidMount() {
    this.setState(
      {
        itemURL: this.props.itemURL,
        itemName: this.props.itemName,
        childItems: this.props.childItems
      });
  }
  /*
    static getDerivedStateFromProps(props, state) {
      if (props.itemName !== state.itemName) {
        return {
          itemURL: props.itemURL,
          itemName: props.itemName,
          childItems: props.childItems
        };
      }
    }
  */
  render() {
    const { childItems } = this.state;
    return (
      <li className="treeview">
        <a href="fake_url">
          <i className="fa fa-dashboard" /> <span>{this.state.itemName}</span>
          <span className="pull-right-container">
            <i className="fa fa-angle-left pull-right" />
          </span>
        </a>
        {childItems === undefined ? null :
          <ul className="treeview-menu">
            {childItems.map((childItem, index) => (
              <li key={index}><Link to={this.state.itemURL}><i className="fa fa-circle-o" />{childItem.name}</ Link></li>
            ))}
            <li className="active"><a href="index2.html"><i className="fa fa-circle-o" /> Dashboard v2</a></li>
          </ul>
        }
      </li>
    )
  }
}
