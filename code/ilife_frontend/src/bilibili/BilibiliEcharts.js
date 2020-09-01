import React, { Component } from "react";
import echarts from "echarts";

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
export default class BilibiliEcharts extends Component {
  constructor(props) {
    super(props);
    this.state = {
      initdone: false,
      plantCap: [
        {
          name: "番剧",
        },
        {
          name: "动漫",
        },
        {
          name: "美妆",
        },
        {
          name: "直播",
        },
        {
          name: "游戏",
        },
      ],
    };
  }
  componentDidMount() {
    //初始化图表
    this.initChart();
  }
  componentWillReceiveProps(nextProps) {
    //更新图表
    this.initChart(nextProps);
  }
  /*生成图表，做了判断，如果不去判断dom有没有生成，
  每次更新图表都要生成一个dom节点*/
  initChart(props) {
    var datas = [];
    const { plantCap } = this.state;
    for (var i = 0; i < plantCap.length; i++) {
      var item = plantCap[i];
      var itemToStyle = datalist[i];
      datas.push({
        name: item.name,
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
      document.getElementById("bilibili-echarts")
    );
    if (myChart === undefined) {
      myChart = echarts.init(document.getElementById("bilibili-echarts"));
    }
    // 绘制图表，option设置图表格式及源数据
    myChart.setOption(option);
    if (!this.state.initdone)
      window.onresize = function () {
        myChart.resize();
      };
  }

  render() {
    return (
      //width和height可由属性值传入
      <div
        id="ilibili-echartsb"
        style={{ width: "100%", height: "100%", minHeight: 300 }}
      ></div>
    );
  }
}
