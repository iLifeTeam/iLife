import React, { Component } from 'react'

const $ = require('jquery');
$.DataTable = require('datatables.net')

export default class WyyHistory extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    this.$el = $(this.el);

    this.$el.DataTable({
      data: this.props.histories ? this.props.histories : null,
      columns: [
        { data: "hisid" },
        { data: "musics.mname" },
        { data: "musics.singers[0].sname" },
      ]
    });

  }
  componentDidUpdate(prevProps, prevState) {
    this.$el = $(this.el);
    this.$el.DataTable({
      data: this.props.histories ? this.props.histories : null,
      columns: [
        { data: "hisid" },
        { data: "musics.mname" },
        { data: "musics.singers[0].sname" },
      ]
    });
  }

  componentWillUnmount() {
    this.$el.DataTable().destroy(true);
  }

  render() {
    return (
      <table id="example0" ref={el => this.el = el} className="table table-bordered table-striped" >
        <thead>
          <tr>
            <th>序号</th>
            <th>歌曲名</th>
            <th>歌手</th>
          </tr>
        </thead>
      </table>
    )
  }
}
