
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
        { data: "type" },
        { data: "language" },
        { data: "ranking" },
        { data: "hot" },

      ]
    });

  }
  componentDidUpdate(prevProps, prevState) {
    if (this.props.activities) {
      var table = $('#MovieTable').DataTable()
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
      <table id="MovieTable" ref={el => this.el = el} className="table table-bordered table-striped" >
        <thead>
          <tr>
            <th>电影名</th>
            <th>标签</th>
            <th>语言</th>
            <th>评分</th>
            <th>热度</th>
          </tr>
        </thead>
      </table>
    )
  }
}
