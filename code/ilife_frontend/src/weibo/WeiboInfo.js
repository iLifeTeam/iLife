import React, { Component } from "react";
import $ from "jquery";
require("datatables.net");
require("datatables.net-dt/css/jquery.dataTables.min.css");

export default class WeiboInfo extends Component {
  componentDidMount() {
    this.$el = $(this.el);

    this.$el.DataTable({
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
        { data: "id" },
        { data: "publish_time" },
        { data: "content" },
        { data: "retweet_num" },
        { data: "comment_count" },
        { data: "up_num" },
      ],
    });
  }
  parseData(data) {
    //console.log(data)
    data.forEach((element) => {
      if (element.publish_time) {
        var str = JSON.stringify(element.publish_time);
        element.publish_time =
          str.split('"')[1].split("T")[0] +
          " " +
          str.split("T")[1].split(":")[0] +
          ":" +
          str.split("T")[1].split(":")[1];
      }
    });

    return data;
  }
  componentDidUpdate(prevProps, prevState) {
    if (
      this.props.activities &&
      this.props.activities !== prevProps.activities
    ) {
      var table = $("#weiboTable").DataTable();
      table.clear();
      //向table中添加数据
      table.rows.add(this.parseData(this.props.activities));
      //重新绘画表格
      table.draw();
    }
  }

  componentWillUnmount() {
    console.log("unmount");
    this.setState = () => false;
    $("#weiboTable").DataTable().destroy(true);
  }

  render() {
    return (
      <table
        id="weiboTable"
        ref={(el) => (this.el = el)}
        className="table table-bordered table-striped"
      >
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
    );
  }
}
