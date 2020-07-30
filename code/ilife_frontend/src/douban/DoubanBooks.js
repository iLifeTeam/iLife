
import React, { Component } from 'react'

const $ = require('jquery');
$.DataTable = require('datatables.net')

export default class DoubanMovies extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    this.$el = $(this.el);

    this.$el.DataTable({
      data: this.props.activities ? this.props.activities : null,
      columns: [
        { data: "name" },
        { data: "author" },
        { data: "price" },
        { data: "ranking" },
        { data: "hot" },

      ]
    });

  }
  componentDidUpdate(prevProps, prevState) {
    if (this.props.activities) {
      var table = $('#BookTable').DataTable()
      table.clear();
      //向table中添加数据
      table.rows.add(this.props.activities);
      //重新绘画表格
      table.draw();
    }
  }

  componentWillUnmount() {
    this.$el.DataTable().destroy(true);
  }

  render() {
    return (
      <table id="BookTable" ref={el => this.el = el} className="table table-bordered table-striped" >
        <thead>
          <tr>
            <th>书籍名</th>
            <th>作者</th>
            <th>单价</th>
            <th>评分</th>
            <th>热度</th>
          </tr>
        </thead>
      </table>
    )
  }
}
