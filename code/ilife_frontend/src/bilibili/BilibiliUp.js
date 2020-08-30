import React, { Component } from "react";
import echarts from "echarts";
// 调试页面回打印出点击的节点名称

var size = 80; //节点大小

var listdata = [];
var links = [];
var legendes = ["Up主", "mid"];
var texts = [];

var upInfo = [
  {
    data: {
      mid: 5970160,
      name: "小潮院长",
      sex: "男",
      face:
        "http://i1.hdslb.com/bfs/face/834eb0de8d2f470bf03e4ea92831b14f3824c863.jpg",
      sign: "传递简单的快乐！就是个臭做视频的崽！",
      rank: 10000,
      level: 6,
      birthday: "06-21",
      official: {
        title: "bilibili 2019百大UP主、知名UP主",
      },
      vip: {
        nickname_color: "#FB7299",
      },
      is_followed: false,
      top_photo:
        "http://i0.hdslb.com/bfs/space/cb1c3ef50e22b6096fde67febe863494caefebad.png",
    },
  },
  {
    data: {
      mid: 5970160,
      name: "小潮院长",
      sex: "男",
      face:
        "http://i1.hdslb.com/bfs/face/834eb0de8d2f470bf03e4ea92831b14f3824c863.jpg",
      sign: "传递简单的快乐！就是个臭做视频的崽！",
      rank: 10000,
      level: 6,
      birthday: "06-21",
      official: {
        title: "bilibili 2019百大UP主、知名UP主",
      },
      vip: {
        nickname_color: "#FB7299",
      },
      is_followed: false,
      top_photo:
        "http://i0.hdslb.com/bfs/space/cb1c3ef50e22b6096fde67febe863494caefebad.png",
    },
  },
  {
    data: {
      mid: 5970160,
      name: "小潮院长",
      sex: "男",
      face:
        "http://i1.hdslb.com/bfs/face/834eb0de8d2f470bf03e4ea92831b14f3824c863.jpg",
      sign: "传递简单的快乐！就是个臭做视频的崽！",
      rank: 10000,
      level: 6,
      birthday: "06-21",
      official: {
        title: "bilibili 2019百大UP主、知名UP主",
      },
      vip: {
        nickname_color: "#FB7299",
      },
      is_followed: false,
      top_photo:
        "http://i0.hdslb.com/bfs/space/cb1c3ef50e22b6096fde67febe863494caefebad.png",
    },
  },
  {
    data: {
      mid: 5970160,
      name: "小潮院长",
      sex: "男",
      face:
        "http://i1.hdslb.com/bfs/face/834eb0de8d2f470bf03e4ea92831b14f3824c863.jpg",
      sign: "传递简单的快乐！就是个臭做视频的崽！",
      rank: 10000,
      level: 6,
      birthday: "06-21",
      official: {
        title: "bilibili 2019百大UP主、知名UP主",
      },
      vip: {
        nickname_color: "#FB7299",
      },
      is_followed: false,
      top_photo:
        "http://i0.hdslb.com/bfs/space/cb1c3ef50e22b6096fde67febe863494caefebad.png",
    },
  },
  {
    data: {
      mid: 5970160,
      name: "小潮院长",
      sex: "男",
      face:
        "http://i1.hdslb.com/bfs/face/834eb0de8d2f470bf03e4ea92831b14f3824c863.jpg",
      sign: "传递简单的快乐！就是个臭做视频的崽！",
      rank: 10000,
      level: 6,
      birthday: "06-21",
      official: {
        title: "bilibili 2019百大UP主、知名UP主",
      },
      vip: {
        nickname_color: "#FB7299",
      },
      is_followed: false,
      top_photo:
        "http://i0.hdslb.com/bfs/space/cb1c3ef50e22b6096fde67febe863494caefebad.png",
    },
  },
  {
    data: {
      mid: 5970160,
      name: "小潮院长",
      sex: "男",
      face:
        "http://i1.hdslb.com/bfs/face/834eb0de8d2f470bf03e4ea92831b14f3824c863.jpg",
      sign: "传递简单的快乐！就是个臭做视频的崽！",
      rank: 10000,
      level: 6,
      birthday: "06-21",
      official: {
        title: "bilibili 2019百大UP主、知名UP主",
      },
      vip: {
        nickname_color: "#FB7299",
      },
      is_followed: false,
      top_photo:
        "http://i0.hdslb.com/bfs/space/cb1c3ef50e22b6096fde67febe863494caefebad.png",
    },
  },
];

var User = {
  mid: 39238459,
  url:
    "http://i1.hdslb.com/bfs/face/a3b38288f0b486753ec981c043e64edb81fdcf32.jpg",
};

function setDataPerson(json, n) {
  listdata.push({
    x: 50,
    y: 100,
    name: "mid",
    showName: json.mid,
    symbol: "image://" + json.url,
    symbolSize: 70,
    category: n,
    draggable: "false",
    formatter: function (params) {
      return params.data.showName;
    },
    label: {
      position: "bottom",
    },
  });
}
function setDataPhone(json, n) {
  var i = 0;
  for (var p in json) {
    listdata.push({
      x: i * 50,
      y: size + i * 10,
      name: p,
      showName: json[p].data.name,
      symbol: "image://" + json[p].data.face,
      symbolSize: size,
      category: n,
      draggable: "false",
      formatter: function (params) {
        return params.data.showName;
      },
      label: {
        position: "bottom",
      },
    });
    i++;
  }
}
function setLinkData(json, title) {
  for (var p in json) {
    links.push({
      source: p,
      target: title,
      value: Math.random() * 10,
      lineStyle: {
        normal: {
          color: "source",
        },
      },
    });
  }
}

export default class BilibiliUp extends Component {
  constructor(props) {
    super(props);
    this.state = {
      initdone: false,
    };
  }
  componentDidMount() {
    //初始化图表
    this.initChart();
  }
  componentWillReceiveProps(nextProps) {
    //更新图表
    //this.initChart(nextProps);
  }
  /*生成图表，做了判断，如果不去判断dom有没有生成，
  每次更新图表都要生成一个dom节点*/
  initChart(props) {
    for (var i = 0; i < legendes.length; i++) {
      texts.push({
        name: legendes[i],
      });
    }

    setDataPhone(upInfo, 0);
    setDataPerson(User, 1);

    setLinkData(upInfo, legendes[1]);

    var option = {
      tooltip: {
        formatter: "{b}",
      },

      backgroundColor: "#ffffff",
      animationDuration: 1000,
      animationEasingUpdate: "quinticInOut",
      series: [
        {
          type: "graph",
          layout: "force",
          force: {
            repulsion: 30,
            gravity: 0,
            edgeLength: [40, 60],
            layoutAnimation: true,
          },
          data: listdata,
          links: links,
          categories: texts,
          roam: false,
          nodeScaleRatio: 0,
          focusNodeAdjacency: false,
          lineStyle: {
            normal: {
              opacity: 0.5,
              width: 1.5,
              curveness: 0,
            },
          },
          label: {
            normal: {
              show: true,
              position: "inside",
              textStyle: {
                color: "#000000",
                fontWeight: "normal",
                fontSize: "12", //字体大小
              },
              formatter: function (params) {
                return params.data.showName;
              },
              fontSize: 18,
              fontStyle: "600",
            },
          },
        },
      ],
      color: [
        "#e8b842",
        "#41b1ef",
        "#667aed",
        "#347fbb",
        "#772cdc",
        "#ff69b4",
        "#ba55d3",
        "#cd5c5c",
        "#ffa500",
        "#40e0d0",
      ],
    };

    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.getInstanceByDom(
      document.getElementById("Up-echarts")
    );
    if (myChart === undefined) {
      myChart = echarts.init(document.getElementById("Up-echarts"));
    }
    // 绘制图表，option设置图表格式及源数据
    myChart.setOption(option);
    if (!this.state.initdone) {
      window.onresize = function () {
        myChart.resize();
      };

      myChart.on("click", this.clickFun);
    }
  }

  clickFun = (param) => {
    console.log(param.name);
  };

  render() {
    return (
      //width和height可由属性值传入
      <div
        id="Up-echarts"
        style={{ width: "100%", height: "100%", minHeight: 400 }}
      ></div>
    );
  }
}