import React, { Component } from "react";
import echarts from "echarts";
import { Image, Card, Row, Col } from "antd";
import axios from "axios";
// 调试页面回打印出点击的节点名称

var size = 60; //节点大小

var listdata = [];
var links = [];
var legendes = ["Simi", "mid", "song"];
var texts = [];

var User = {
  mid: 39238459,
  sname: "我",
  url:
    "http://i1.hdslb.com/bfs/face/834eb0de8d2f470bf03e4ea92831b14f3824c863.jpg",
};

function setDataPerson(json, n) {
  listdata.push({
    x: 50,
    y: 100,
    name: "mid",
    showName: json.sname,
    symbol: "image://" + json.url,
    symbolSize: 70,
    category: n,
    draggable: "false",
    label: {
      position: "bottom",
    },
  });
}

async function getImg(mid) {
  var config = {
    method: "get",
    url:
      "http://18.166.111.161:8000/music-service/music/getSongImage?mid=" + mid,
    headers: { withCredentials: true },
  };

  const pic = await axios(config)
    .then(function (response) {
      //console.log(JSON.stringify(response.data));
      return response.data;
    })
    .catch(function (error) {
      console.log(error);
      return "data:image/png;base64,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";
    });
  return pic;
}

async function setDataPhone(json, n) {
  var i = 0;
  for (var p in json) {
    const url = await getImg(json[p].musics.mid);
    console.log(url);
    listdata.push({
      x: i * 50,
      y: size + i * 10,
      name: p,
      mid: json[p].musics.mid,
      singer: json[p].musics.singers[0].sname,
      sid: json[p].musics.singers[0].sid,
      showName: json[p].musics.mname,
      symbol: "image://" + url,
      symbolSize: size,
      category: n,
      draggable: "false",
      url: json[p].musics.singers[0].sid,
      label: {
        position: "bottom",
      },
    });
    i++;
  }
  //console.log(listdata);
  return 1;
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

export default class WyyFavor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      initdone: false,
      Songs: [],
      mid: this.props.mid,
      sname: this.props.sname,
      PopVideo: null,
    };
    this.clickFun = this.clickFun.bind(this);
  }
  componentDidMount() {
    //初始化图表
    listdata = [];
    links = [];
    texts = [];
    if (this.props.Songs) this.initChart(this.props);
  }
  componentWillReceiveProps(nextProps) {
    //更新图表
    listdata = [];
    links = [];
    texts = [];
    if (nextProps.Songs) this.initChart(nextProps);
  }
  /*生成图表，做了判断，如果不去判断dom有没有生成，
  每次更新图表都要生成一个dom节点*/
  async initChart(props) {
    console.log("111");
    const { Songs, mid } = props;
    //console.log(Songs);
    for (var i = 0; i < legendes.length; i++) {
      texts.push({
        name: legendes[i],
      });
    }

    await setDataPhone(Songs, 2);
    User.mid = mid;
    //console.log(sname);
    setDataPerson(User, 1);

    setLinkData(Songs, legendes[1]);

    //for(var i = 0; i < Songs.length)

    var option = {
      tooltip: {
        trigger: "item",
        enterable: true,
        position: "right",
        formatter: function (params) {
          if (params.data.category === 1) {
            //console.log(params.data.showName);
            return "(你自己）";
          }
          return (
            params.data.showName + "<br />" + params.data.singer + "<br />"
          );
        },
        extraCssText:
          "box-shadow: 0 0 3px rgba(0, 0, 0, 0.3);text-align:center;",
      },

      backgroundColor: "#ffffff",
      animationDuration: 1000,
      animationEasingUpdate: "quinticInOut",
      series: [
        {
          type: "graph",
          layout: "force",
          force: {
            repulsion: 10,
            gravity: 0,
            edgeLength: [50, 75],
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
      document.getElementById("Songs-echarts")
    );
    if (myChart === undefined) {
      myChart = echarts.init(document.getElementById("Songs-echarts"));
    }
    // 绘制图表，option设置图表格式及源数据
    myChart.setOption(option);
    window.onresize = function () {
      myChart.resize();
    };

    myChart.on("click", this.clickFun);
  }

  async clickFun(param) {
    if (param.data.category === 0) {
      window.open(
        "https://music.163.com/#/artist?id=" +
          encodeURIComponent(param.data.url)
      );
      return;
    }
    if (param.data.category === 1) return;
    var config = {
      method: "get",
      url:
        "http://18.166.111.161:8000/music-service/music/getSimiSongs?mid=" +
        param.data.mid,
      headers: {
        withCredentials: true,
      },
    };

    await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        var simiSongs = response.data;
        var simi = [];
        for (var p in listdata) {
          simi.push(listdata[p]);
        }
        for (var i = 0; i < simiSongs.length; i++) {
          simi.push({
            x: i * 50,
            y: size + i * 10,
            name: JSON.stringify(i + 20),
            mid: null,
            singer: simiSongs[i].artlists[0].name,
            sid: simiSongs[i].artlists[0].id,
            showName: simiSongs[i].name,
            symbol: "image://" + simiSongs[i].album.picUrl,
            symbolSize: size,
            category: 0,
            draggable: "false",
            url: simiSongs[i].artlists[0].id,
            label: {
              position: "bottom",
            },
          });
        }

        var newlinks = [];

        for (var p in links) {
          newlinks.push(links[p]);
        }
        for (var i = 0; i < simiSongs.length; i++) {
          newlinks.push({
            source: JSON.stringify(i + 20),
            target: param.data.name,
            value: Math.random() * 10,
            lineStyle: {
              normal: {
                color: "source",
              },
            },
          });
        }
        console.log(simi);
        console.log(newlinks);
        var option = {
          tooltip: {
            trigger: "item",
            enterable: true,
            position: "right",
            formatter: function (params) {
              if (params.data.category === 1) {
                //console.log(params.data.showName);
                return "(你自己）";
              }
              return (
                params.data.showName + "<br />" + params.data.singer + "<br />"
              );
            },
            extraCssText:
              "box-shadow: 0 0 3px rgba(0, 0, 0, 0.3);text-align:center;",
          },

          backgroundColor: "#ffffff",
          animationDuration: 1000,
          animationEasingUpdate: "quinticInOut",
          series: [
            {
              type: "graph",
              layout: "force",
              force: {
                repulsion: 10,
                gravity: 0,
                edgeLength: [50, 75],
                layoutAnimation: true,
              },
              data: simi,
              links: newlinks,
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
          document.getElementById("Songs-echarts")
        );
        if (myChart === undefined) {
          myChart = echarts.init(document.getElementById("Songs-echarts"));
        }
        // 绘制图表，option设置图表格式及源数据
        myChart.setOption(option);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  render() {
    return (
      //width和height可由属性值传入
      <>
        <div
          id="Songs-echarts"
          style={{ width: "100%", height: "100%", minHeight: 600 }}
        ></div>
        <Row gutter={24}>
          {this.state.PopVideo
            ? this.state.PopVideo.map((video) => {
                //console.log(video.title);
                return (
                  <Col
                    key={video.aid}
                    className="gutter-row"
                    span={12}
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
                          fallback="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMIAAADDCAYAAADQvc6UAAABRWlDQ1BJQ0MgUHJvZmlsZQAAKJFjYGASSSwoyGFhYGDIzSspCnJ3UoiIjFJgf8LAwSDCIMogwMCcmFxc4BgQ4ANUwgCjUcG3awyMIPqyLsis7PPOq3QdDFcvjV3jOD1boQVTPQrgSkktTgbSf4A4LbmgqISBgTEFyFYuLykAsTuAbJEioKOA7DkgdjqEvQHEToKwj4DVhAQ5A9k3gGyB5IxEoBmML4BsnSQk8XQkNtReEOBxcfXxUQg1Mjc0dyHgXNJBSWpFCYh2zi+oLMpMzyhRcASGUqqCZ16yno6CkYGRAQMDKMwhqj/fAIcloxgHQqxAjIHBEugw5sUIsSQpBobtQPdLciLEVJYzMPBHMDBsayhILEqEO4DxG0txmrERhM29nYGBddr//5/DGRjYNRkY/l7////39v///y4Dmn+LgeHANwDrkl1AuO+pmgAAADhlWElmTU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAAqACAAQAAAABAAAAwqADAAQAAAABAAAAwwAAAAD9b/HnAAAHlklEQVR4Ae3dP3PTWBSGcbGzM6GCKqlIBRV0dHRJFarQ0eUT8LH4BnRU0NHR0UEFVdIlFRV7TzRksomPY8uykTk/zewQfKw/9znv4yvJynLv4uLiV2dBoDiBf4qP3/ARuCRABEFAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghggQAQZQKAnYEaQBAQaASKIAQJEkAEEegJmBElAoBEgghgg0Aj8i0JO4OzsrPv69Wv+hi2qPHr0qNvf39+iI97soRIh4f3z58/u7du3SXX7Xt7Z2enevHmzfQe+oSN2apSAPj09TSrb+XKI/f379+08+A0cNRE2ANkupk+ACNPvkSPcAAEibACyXUyfABGm3yNHuAECRNgAZLuYPgEirKlHu7u7XdyytGwHAd8jjNyng4OD7vnz51dbPT8/7z58+NB9+/bt6jU/TI+AGWHEnrx48eJ/EsSmHzx40L18+fLyzxF3ZVMjEyDCiEDjMYZZS5wiPXnyZFbJaxMhQIQRGzHvWR7XCyOCXsOmiDAi1HmPMMQjDpbpEiDCiL358eNHurW/5SnWdIBbXiDCiA38/Pnzrce2YyZ4//59F3ePLNMl4PbpiL2J0L979+7yDtHDhw8vtzzvdGnEXdvUigSIsCLAWavHp/+qM0BcXMd/q25n1vF57TYBp0a3mUzilePj4+7k5KSLb6gt6ydAhPUzXnoPR0dHl79WGTNCfBnn1uvSCJdegQhLI1vvCk+fPu2ePXt2tZOYEV6/fn31dz+shwAR1sP1cqvLntbEN9MxA9xcYjsxS1jWR4AIa2Ibzx0tc44fYX/16lV6NDFLXH+YL32jwiACRBiEbf5KcXoTIsQSpzXx4N28Ja4BQoK7rgXiydbHjx/P25TaQAJEGAguWy0+2Q8PD6/Ki4R8EVl+bzBOnZY95fq9rj9zAkTI2SxdidBHqG9+skdw43borCXO/ZcJdraPWdv22uIEiLA4q7nvvCug8WTqzQveOH26fodo7g6uFe/a17W3+nFBAkRYENRdb1vkkz1CH9cPsVy/jrhr27PqMYvENYNlHAIesRiBYwRy0V+8iXP8+/fvX11Mr7L7ECueb/r48eMqm7FuI2BGWDEG8cm+7G3NEOfmdcTQw4h9/55lhm7DekRYKQPZF2ArbXTAyu4kDYB2YxUzwg0gi/41ztHnfQG26HbGel/crVrm7tNY+/1btkOEAZ2M05r4FB7r9GbAIdxaZYrHdOsgJ/wCEQY0J74TmOKnbxxT9n3FgGGWWsVdowHtjt9Nnvf7yQM2aZU/TIAIAxrw6dOnAWtZZcoEnBpNuTuObWMEiLAx1HY0ZQJEmHJ3HNvGCBBhY6jtaMoEiJB0Z29vL6ls58vxPcO8/zfrdo5qvKO+d3Fx8Wu8zf1dW4p/cPzLly/dtv9Ts/EbcvGAHhHyfBIhZ6NSiIBTo0LNNtScABFyNiqFCBChULMNNSdAhJyNSiECRCjUbEPNCRAhZ6NSiAARCjXbUHMCRMjZqBQiQIRCzTbUnAARcjYqhQgQoVCzDTUnQIScjUohAkQo1GxDzQkQIWejUogAEQo121BzAkTI2agUIkCEQs021JwAEXI2KoUIEKFQsw01J0CEnI1KIQJEKNRsQ80JECFno1KIABEKNdtQcwJEyNmoFCJAhELNNtScABFyNiqFCBChULMNNSdAhJyNSiECRCjUbEPNCRAhZ6NSiAARCjXbUHMCRMjZqBQiQIRCzTbUnAARcjYqhQgQoVCzDTUnQIScjUohAkQo1GxDzQkQIWejUogAEQo121BzAkTI2agUIkCEQs021JwAEXI2KoUIEKFQsw01J0CEnI1KIQJEKNRsQ80JECFno1KIABEKNdtQcwJEyNmoFCJAhELNNtScABFyNiqFCBChULMNNSdAhJyNSiECRCjUbEPNCRAhZ6NSiAARCjXbUHMCRMjZqBQiQIRCzTbUnAARcjYqhQgQoVCzDTUnQIScjUohAkQo1GxDzQkQIWejUogAEQo121BzAkTI2agUIkCEQs021JwAEXI2KoUIEKFQsw01J0CEnI1KIQJEKNRsQ80JECFno1KIABEKNdtQcwJEyNmoFCJAhELNNtScABFyNiqFCBChULMNNSdAhJyNSiEC/wGgKKC4YMA4TAAAAABJRU5ErkJggg=="
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
