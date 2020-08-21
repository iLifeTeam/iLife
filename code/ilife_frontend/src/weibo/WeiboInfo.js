import React, { Component } from 'react'

const $ = require('jquery');
$.DataTable = require('datatables.net')

function parseData(data) {

  data.forEach(element => {
    if (element.publish_time) {
      var str = JSON.stringify(element.publish_time);
      element.publish_time = str.split('\"')[1].split('T')[0] + " " + str.split('T')[1].split(':')[0] + ':' + str.split('T')[1].split(':')[1];
    }
  });

  return data;
}

export default class WeiboInfo extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    this.$el = $(this.el);

    this.$el.DataTable({
      data: this.props.activities ? parseData(this.props.activities) : null,
      columns: [
        { data: "id" },
        { data: "publish_time" },
        { data: "content" },
        { data: "retweet_num" },
        { data: "comment_count" },
        { data: "up_num" },

      ]
    });

  }
  componentDidUpdate(prevProps, prevState) {
    if (this.props.activities && this.props.activities != prevProps.activities) {
      var table = $('#weiboTable').DataTable()
      table.clear();
      //向table中添加数据
      table.rows.add(parseData(this.props.activities));
      //重新绘画表格
      table.draw();
    }
  }

  componentWillUnmount() {
    console.log("unmount");
    this.setState = () => false;
    this.$el.DataTable().destroy(true);
  }


  render() {
    return (
      <table id="weiboTable" ref={el => this.el = el} className="table table-bordered table-striped" >
        <thead>
          <tr>
            <th style={{ width: "8%" }}>微博id</th>
            <th style={{ width: "20%" }}>发表时间</th>
            <th style={{ width: "48%" }}>内容</th>
            <th style={{ width: "8%" }}>转发次数</th>
            <th style={{ width: "8%" }}>评论数</th>
            <th style={{ width: "8%" }}>获赞数</th>
          </tr>
        </thead>
      </table>
    )
  }
}
