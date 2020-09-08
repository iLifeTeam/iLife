import React, { Component } from 'react'
import { withRouter } from 'react-router-dom'

class MenuItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      itemURL: "/home/fake_url",
      itemName: "支付宝",
      childItems: [{
        name: "账单信息",
        url: "bills"
      },
      {
        name: "趋势分析",
        url: "analyse"
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

  router(childitem) {
    if (window.location.href.toLowerCase().match(this.state.itemURL)) {
      this.props.history.push(this.state.itemURL + "#" + childitem.id);
      //console.log(childitem.id)
      let anchorElement = document.getElementById(childitem.id);
      //console.log(anchorElement);
      if (anchorElement) {
        anchorElement.scrollIntoView({ behavior: 'smooth' });
      }
    }
    else {
      this.props.history.push(this.state.itemURL + "#" + childitem.id);
      let anchorElement = document.getElementById(childitem.id);
      //console.log(anchorElement);
      if (anchorElement) {
        anchorElement.scrollIntoView({ behavior: 'smooth' });
      }
    }
  }

  render() {
    const { childItems } = this.state;
    return (
      <li className="treeview">
        <a >
          <i className="fa fa-dashboard" /> <span>{this.state.itemName}</span>
          <span className="pull-right-container">
            <i className="fa fa-angle-left pull-right" />
          </span>
        </a>
        {childItems === undefined ? null :
          <ul className="treeview-menu">
            {childItems.map((childItem, index) => (
              <li key={index}><a onClick={() => this.router(childItem)}><i className="fa fa-circle-o" />{childItem.name}</a></li>
            ))}
          </ul>
        }
      </li>
    )
  }
}

export default withRouter(MenuItem);