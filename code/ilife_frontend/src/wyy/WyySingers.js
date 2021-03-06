import React, { Component } from "react";
import echarts from "echarts";
import { Image, Card, Row, Col } from "antd";
import axios from "axios";

var datalist = [
  {
    offset: [50, 50],
    symbolSize: 100,
    opacity: 0.9,
    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
      {
        offset: 0,
        color: "#29c0fb",
      },
      {
        offset: 1,
        color: "#2dc5b9",
      },
    ]),
  },
  {
    offset: [38, 70],
    symbolSize: 80,
    opacity: 0.9,
    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
      {
        offset: 0,
        color: "#35d17e",
      },
      {
        offset: 1,
        color: "#49ddb2",
      },
    ]),
  },
  {
    offset: [23, 43],
    symbolSize: 70,
    opacity: 0.9,
    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
      {
        offset: 0,
        color: "#e5d273",
      },
      {
        offset: 1,
        color: "#e4a37f",
      },
    ]),
  },
  {
    offset: [44, 26],
    symbolSize: 70,
    opacity: 0.9,
    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
      {
        offset: 0,
        color: "#277aec",
      },
      {
        offset: 1,
        color: "#57c5ec",
      },
    ]),
  },
  {
    offset: [80, 58],
    symbolSize: 60,
    opacity: 0.9,
    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
      {
        offset: 0,
        color: "#e54948",
      },
      {
        offset: 1,
        color: "#f08456",
      },
    ]),
  },
];

export default class WyySingers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      Singer: null,
    };
  }
  componentDidMount() {
    //初始化图表
    console.log(this.props);
    if (this.props.singers) this.initChart(this.props);
  }

  componentDidUpdate(prevProps, prevState) {
    //更新图表
    console.log(this.props);
    if (this.props.singers) this.initChart(this.props);
  }

  initChart(props) {
    var datas = [];
    const { singers } = props;
    for (var i = 0; i < Math.min(singers.length, 5); i++) {
      if (singers[i].times < 5) break;
      var item = singers[i].name;
      var itemToStyle = datalist[i];
      console.log(item);
      datas.push({
        name: item,
        value: itemToStyle.offset,
        symbolSize: itemToStyle.symbolSize,
        label: {
          normal: {
            textStyle: {
              fontSize: 18,
              fontWeight: 700,
              lineHeight: 18,
            },
          },
        },
        itemStyle: {
          normal: {
            color: itemToStyle.color,
            opacity: itemToStyle.opacity,
          },
        },
      });
    }
    var option = {
      backgroundColor: "transparent",
      grid: {
        show: false,
        top: 10,
        bottom: 10,
      },
      xAxis: [
        {
          gridIndex: 0,
          type: "value",
          show: false,
          min: 0,
          max: 100,
          nameLocation: "middle",
          nameGap: 5,
        },
      ],
      yAxis: [
        {
          gridIndex: 0,
          min: 0,
          show: false,
          max: 100,
          nameLocation: "middle",
          nameGap: 30,
        },
      ],
      series: [
        {
          type: "effectScatter",
          // symbol: 'circle',
          // symbolSize: 120,
          showEffectOn: "render",
          rippleEffect: {
            period: 3, //波纹秒数
            brushType: "fill", //stroke(涟漪)和fill(扩散)，两种效果
            scale: 1.8, //波纹范围
          },
          hoverAnimation: true,
          label: {
            normal: {
              show: true,
              formatter: "{b}",
              color: "#fff",
              textStyle: {
                fontSize: "18",
              },
            },
          },
          itemStyle: {
            normal: {
              color: "#00acea",
            },
          },
          data: datas,
        },
      ],
    };
    // 基于准备好的dom，初始化echarts实例

    let myChart = echarts.getInstanceByDom(
      document.getElementById("wyy-echarts")
    );
    if (myChart === undefined) {
      myChart = echarts.init(document.getElementById("wyy-echarts"));
    }
    // 绘制图表，option设置图表格式及源数据

    myChart.setOption(option);
    window.onresize = function () {
      myChart.resize();
    };
  }

  render() {
    return (
      <div
        id="wyy-echarts"
        style={{ width: "100%", height: "100%", minHeight: 400 }}
      ></div>
    );
  }
}
