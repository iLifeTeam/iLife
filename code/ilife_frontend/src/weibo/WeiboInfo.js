import React, { Component } from 'react'

const $ = require('jquery');
$.DataTable = require('datatables.net')

export default class WeiboInfo extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    this.$el = $(this.el);

    this.$el.DataTable({
      data: this.props.activities ? this.props.activities : null,
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
    var table = $('#weiboTable').DataTable()
    table.clear();
    //向table中添加数据
    table.rows.add(this.props.activities ? this.props.activities : null);
    //重新绘画表格
    table.draw();
  }

  componentWillUnmount() {
    this.$el.DataTable().destroy(true);
  }

  render() {
    return (
      <table id="weiboTable" ref={el => this.el = el} className="table table-bordered table-striped" >
        <thead>
          <tr>
            <th>微博id</th>
            <th>发表时间</th>
            <th>内容</th>
            <th>转发次数</th>
            <th>评论数</th>
            <th>获赞数</th>
          </tr>
        </thead>
      </table>
    )
  }
}
