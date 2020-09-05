import React, { Component } from "react";

const $ = require("jquery");
$.DataTable = require("datatables.net");

export default class BilibiliHistorty extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    this.$el = $(this.el);

    this.$el.DataTable({
      data: this.props.histories ? this.props.histories : null,
      oLanguage: {
        sProcessing: "正在查询中......",
        sZeroRecords: "您尚未绑定账户/没有发表过动态信息...",
        oPaginate: {
          sFirst: "首页",
          sPrevious: "上一页",
          sNext: "下一页",
          sLast: "末页",
        },
      },
      columns: [
        { data: "hisid" },
        { data: "video.title" },
        { data: "video.auther_name" },
        { data: "video.tag_name" },
      ],
    });
  }
  componentDidUpdate(prevProps, prevState) {
    if (this.props.histories) {
      var table = $("#biliTable").DataTable();
      table.clear();
      //向table中添加数据
      table.rows.add(this.props.histories);
      //重新绘画表格
      table.draw();
    }
  }

  componentWillUnmount() {
    this.$el.DataTable().destroy(true);
  }

  render() {
    return (
      <table
        id="biliTable"
        ref={(el) => (this.el = el)}
        className="table table-bordered table-striped"
      >
        <thead>
          <tr>
            <th>历史记录id</th>
            <th>视频名称</th>
            <th>up主</th>
            <th>标签</th>
          </tr>
        </thead>
      </table>
    );
  }
}
