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

export default class BilibiliEcharts extends Component {
  constructor(props) {
    super(props);
    this.state = {
      Tag: [],
      PopVideo: null,
    };
    this.clickFun = this.clickFun.bind(this);
  }
  componentDidMount() {
    //初始化图表
    console.log(this.props);
    this.initChart(this.props);
  }

  componentDidUpdate(prevProps, prevState) {
    //更新图表
    console.log(this.props);
    this.initChart(this.props);
  }

  initChart(props) {
    var datas = [];
    const { Tag } = props;
    if (!Tag) return;
    for (var i = 0; i < Math.min(Tag.length, 5); i++) {
      var item = Tag[i];
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
      document.getElementById("bilibili-echarts")
    );
    if (myChart === undefined) {
      myChart = echarts.init(document.getElementById("bilibili-echarts"));
    }
    // 绘制图表，option设置图表格式及源数据

    myChart.setOption(option);
    window.onresize = function () {
      myChart.resize();
    };

    myChart.on("click", this.clickFun);
  }

  async clickFun(param) {
    var config = {
      method: "get",
      url:
        "http://18.166.111.161:8000/bilibili/bili/getPopVideo?tag=" +
        param.data.name,
      headers: { withCredentials: true },
    };

    const PopVideo = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data.data));
        return response.data.data;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });
    this.setState({ PopVideo: PopVideo });
  }
  clickPic(param) {
    window.open("https://www.bilibili.com/video/" + encodeURIComponent(param));
  }

  render() {
    return (
      //width和height可由属性值传入
      <>
        <div
          id="bilibili-echarts"
          style={{ width: "100%", height: "100%", minHeight: 400 }}
        ></div>
        <Row gutter={24}>
          {this.state.PopVideo
            ? this.state.PopVideo.map((video, index) => {
                if (index > 6) return;
                //console.log(video.title);
                return (
                  <Col
                    key={video.aid}
                    className="gutter-row"
                    span={24}
                    style={{ padding: 16 }}
                    onClick={() => this.clickPic(video.bvid)}
                  >
                    <Card
                      hoverable
                      style={{ Width: 320 }}
                      cover={
                        <Image
                          referrerPolicy="no-referrer"
                          src={video.pic}
                          fallback="data:image/png;base64,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"
                        />
                      }
                    >
                      <Card.Meta
                        title={video.title}
                        description={video.author}
                      />
                    </Card>
                  </Col>
                );
              })
            : null}
        </Row>
      </>
    );
  }
}
