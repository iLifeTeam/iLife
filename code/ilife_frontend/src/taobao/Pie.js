import React, { Component } from "react";
import echarts from "echarts";


const sourceDataOrigin = [{'name': '澳门特别行政区', 'value': 30, 'extData': [89, '#ccfd66', 0, '']},
    {'name': '西藏自治区', 'value': 33, 'extData': [367, '#cdf967', 0, '']},
    {'name': '青海省', 'value': 38, 'extData': [809, '#cff169', 0, '']},
    {'name': '宁夏回族自治区', 'value': 40, 'extData': [1023, '#cfee69', 0, '']},
    {'name': '香港特别行政区', 'value': 40, 'extData': [1070, '#d0ed6a', 0, '']},
    {'name': '海南省', 'value': 41, 'extData': [1179, '#d0eb6a', 0, '']},
    {'name': '天津市', 'value': 51, 'extData': [2105, '#d3db6d', 0, '']},
    {'name': '新疆维吾尔自治区', 'value': 53, 'extData': [2364, '#d4d76e', 0, '']},
    {'name': '上海市', 'value': 59, 'extData': [2965, '#d6cd70', 0, '']},
    {'name': '北京市', 'value': 62, 'extData': [3237, '#d7c871', 0, '']},
    {'name': '甘肃省', 'value': 63, 'extData': [3375, '#d7c671', 0, '']},
    {'name': '贵州省', 'value': 64, 'extData': [3467, '#d8c472', 0, '']},
    {'name': '吉林省', 'value': 67, 'extData': [3744, '#d9bf73', 0, '']},
    {'name': '重庆市', 'value': 70, 'extData': [4005, '#dabb74', 0, '']},
    {'name': '内蒙古自治区', 'value': 70, 'extData': [4040, '#daba74', 0, '']},
    {'name': '云南省', 'value': 75, 'extData': [4510, '#dbb275', 0, '']},
    {'name': '黑龙江省', 'value': 77, 'extData': [4777, '#dcae76', 0, '']},
    {'name': '广西壮族自治区', 'value': 80, 'extData': [5024, '#ddaa77', 0, '']},
    {'name': '陕西省', 'value': 81, 'extData': [5174, '#dea778', 0, '']},
    {'name': '山西省', 'value': 81, 'extData': [5179, '#dea778', 0, '']},
    {'name': '福建省', 'value': 84, 'extData': [5463, '#dfa279', 0, '']},
    {'name': '江西省', 'value': 84, 'extData': [5469, '#dfa279', 0, '']},
    {'name': '辽宁省', 'value': 93, 'extData': [6317, '#e1947b', 0, '']},
    {'name': '安徽省', 'value': 94, 'extData': [6445, '#e2917c', 0, '']},
    {'name': '湖北省', 'value': 95, 'extData': [6583, '#e28f7c', 0, '']},
    {'name': '湖南省', 'value': 101, 'extData': [7172, '#e4857e', 0, '']},
    {'name': '河北省', 'value': 115, 'extData': [8533, '#e96e83', 0, '']},
    {'name': '浙江省', 'value': 123, 'extData': [9370, '#ec6086', 0, '']},
    {'name': '四川省', 'value': 124, 'extData': [9461, '#ec5e86', 0, '']},
    {'name': '河南省', 'value': 126, 'extData': [9612, '#ed5c87', 0, '']},
    {'name': '江苏省', 'value': 131, 'extData': [10140, '#ee5388', 0, '']},
    {'name': '山东省', 'value': 145, 'extData': [11558, '#f33b8d', 0, '']},
    {'name': '广东省', 'value': 152, 'extData': [12260, '#f62f90', 0, '']}]
let sourceData = []
const graphicData = [{
    type: 'group',
    left: 'center',
    top: '60%',
    bounding: 'raw', //重要，否则内容无法超过组
    z: 100,
    children: []
}]
const graphicScatter = {
    type: 'circle',
    shape: {
        r: 2
    },
    style: {
        fill: 'white'
    },
    z: 100
}
const graphicText = [{
    type: 'text',
    // left: 'center',
    // top: 10,
    z: 100,
    style: {
        fill: 'white',
        text: '韩国',
        font: 'bold 16px Microsoft YaHei',
        textAlign: 'center'
    }
}, {
    type: 'text',
    // left: 'center',
    // top: 40,
    z: 100,
    style: {
        fill: 'white',
        text: '5766例',
        font: 'bold 14px Microsoft YaHei',
        textAlign: 'center',

    }
}, {
    type: 'text',
    // left: 'center',
    // top: 70,
    z: 100,
    style: {
        fill: 'white',
        text: '死亡36例',
        font: 'bold 12px Microsoft YaHei',
        textAlign: 'center'
    }
}]
const graphicChildren = {
    type: 'group',
    // left: 'center',
    // top: 'center',
    position: [],
    bounding: 'raw',
    z: 100,
    children: []

}

let totalNumber = 0;
function convertData1() {
    const graphic_total_Text = [{
        type: 'text',
        right: -220,
        bottom: 500,
        z: 100,
        style: {
            fill: 'black',
            text: '数据时间2020年',
            font: 'bold 12px Microsoft YaHei',
            textAlign: 'right'
        }
    }]
    var maxScale = 1
    var minScale = 0.1
    var stepRadius = 2 * Math.PI / sourceData.length
    var stepScale = (maxScale - minScale) / sourceData.length
    for (var i = 0; i < sourceData.length; i++) {
        sourceData[i].itemStyle = {
            color: sourceData[i]["extData"][1],
            borderWidth: 0
        }
        sourceData[i].label = {
            show: i > 6 ? false : false,
            position: i <= 5 ? "outer" : "inside",
            // alignTo: "labelLine",
            align: "right",
            borderWidth: 1,
            borderColor: "red",
            alignTo: "edge",
            margin: 650,
            formatter: (a) => {
                return a.data.extData[3]
            }
        }
        sourceData[i].labelLine = {
            show: i > 6 ? false : false,
            // length: i <= 5 ? 5 * (i - 5) : 0,
            // length2: i <= 5 ? 0 : 0,
            lineStyle: {
                type: "dashed"
            }
        }
        var curRadius = (i + 0.5) * stepRadius
        // var curArclen = 1
        var curScale = i > 11 ? minScale + stepScale * (i) : 0.6 + stepScale * (i)
        var startR = i > 11 ? sourceData[i]["value"] * 450 / sourceData.slice(-1)[0]["value"] * 0.95 : ((
            sourceData[i]["value"] * 460 / sourceData.slice(-1)[0]["value"] + 20))
        var curR = [startR, startR * (i > 33 ? 0.95 : (i > 11 ? 0.75 : 1.12)), startR * (i > 33 ? 0.95 : (i >
        11 ? 0.8 : 1.17)) * (i > 33 ? 0.95 :
            (i > 11 ? 0.8 : 1.17))]
        var curPositions = []
        var curChilds = []
        var curCircles = []
        for (var j = 0; j < 3; j++) {
            var curX = Math.sin(curRadius) * curR[j]
            var curY = -Math.cos(curRadius) * curR[j]
            curPositions.push([curX, curY])
            var curChild = JSON.stringify(graphicChildren)
            curChild = JSON.parse(curChild)
            curChild.position = [curX, curY]
            curChild.rotation = i > 33 ? -((i + 0.5) / sourceData.length * 2 * Math.PI) : (i <= 11 ? -((i +
                0.5) / sourceData.length * 2 * Math.PI) + Math.PI / 2 : 0)
            curChild.scale = [curScale, curScale]
            let curgraphicText = JSON.parse(JSON.stringify(graphicText[j]))
            // curgraphicText.style.text = j == 0 ? sourceData[i]["name"] : (j == 1 ? (i > 11 ? sourceData[i][
            //     "extData"
            //     ][0] + "网点" : "") : (j == 2 && i > 33 ? (sourceData[i][
            //     "extData"
            //     ][2] + "个") : (i <= 11 ? sourceData[i][
            //     "extData"
            //     ][3] : "")))
            curgraphicText.style.text = j == 0 ? sourceData[i]["name"] : (j == 1 ? (i > 11 ? "" : "") : (j == 2 && i > 33 ? "" : (i <= 11 ? "" : "")))
            if (i <= 11) {
                curgraphicText.style.fill = "black"
                curgraphicText.style.textAlign = "left"
            }
            curChild.children = [curgraphicText]
            curChilds.push(curChild)
            // var curCircle = JSON.parse(JSON.stringify(graphicScatter))
            // curCircle.position = [curX, curY]
            // curCircles.push(curCircle)
            // graphicData[0].children.push(curCircle)
            graphicData[0].children.push(curChild)
        }
        console.log(i, sourceData[i]["name"], curRadius, curR, curPositions)
    }
    for (var m = 0; m < 3 * 2; m++) {
        var cur_total_text = graphic_total_Text[m]
        graphicData[0].children.push(cur_total_text)
    }
    return sourceData
}

const option = {
    title: {
        text: '淘宝购物数据分布',
        left: 'center',
        textStyle: {
            //color: "red",
            fontSize: 40
        },
        //backgroundColor: "rgb(199,16,16)",
        top: "2%"
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    toolbox: {
        show: true,
        feature: {
            mark: {
                show: true
            },
            dataView: {
                show: true,
                readOnly: false
            },
            magicType: {
                show: true,
                type: ['pie', 'funnel']
            },
            restore: {
                show: true
            },
            saveAsImage: {
                show: true
            }
        }
    },
    graphic: graphicData,
    series: [{
        name: '该类型购物总数',
        type: 'pie',
        radius: [20, 450],
        center: ['50%', '60%'],
        label: {
            show: true
        },
        roseType: 'area',
        data: convertData1(),
        rich: {
            a: {
                color: 'white',
                lineHeight: 10
            },
            b: {
                backgroundColor: {
                    image: 'xxx/xxx.jpg'
                },
                height: 40
            },
            x: {
                fontSize: 18,
                fontFamily: 'Microsoft YaHei',
                borderColor: '#449933',
                borderRadius: 4
            }
        }
    }]
};


export default class Pie extends Component {
    constructor(props) {
        super(props);
        this.state = {
            initdone: false,

        };
    }
    componentDidMount() {
        //初始化图表
        this.initChart(this.props);
    }
    /*生成图表，做了判断，如果不去判断dom有没有生成，
    每次更新图表都要生成一个dom节点*/
    initChart(props) {
        console.log(props)
        if (props == null) return
        const {stats} = props
        console.log(stats)
        const keys = Object.keys(stats.categories)
        console.log(keys)
        const categories = []
        for (const key of keys){
            categories.push({
                "key": key,
                "orderIds": stats.categories[key].orderIds,
                "orderNum": stats.categories[key].orderIds.length,
                "totalExpense": stats.categories[key].expense,
            })
        }
        categories.sort((a,b) => a.orderNum - b.orderNum)
        for (const [index,category] of categories.entries()){
            totalNumber += category.orderNum
            sourceData.push({
                "name": category.key,
                "value": category.orderNum,
                "extData": sourceDataOrigin[index]
            })
        }
        console.log(totalNumber)

        // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.getInstanceByDom(
            document.getElementById("pie-echarts")
        );
        if (myChart === undefined) {
            myChart = echarts.init(document.getElementById("pie-echarts"));
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
                id="pie-echarts"
                style={{ width: "100%", height: "100%", minHeight: 1000 }}
            ></div>
        );
    }
}
