import React, { Component } from "react";
import echarts from "echarts";

let data = []
let color = [ "#fec101", "#b5b8cd ", "#ff6226","#f60000","#2cc78f","#2ca7f9"]
// 这步主要是为了让小圆点的颜色和饼状图的块对应，如果圆点的颜色是统一的，只需要把itemStyle写在series里面
let setLabel = (data)=>{
    let opts = [];
    for(let i=0;i<data.length;i++){
        let item = {};
        item.name = data[i].name;
        item.value = data[i].value;
        item.label = {
            normal:{
                //控制引导线上文字颜色和位置,此处a是显示文字区域，b做一个小圆圈在引导线尾部显示
                show:true,
                //a和b来识别不同的文字区域
                formatter:[
                    '{a|{d}%  {b}}',//引导线上面文字
                    '{b|}' //引导线下面文字
                ].join('\n'), //用\n来换行
                rich:{
                    a:{
                        left:20,
                        padding:[0,-80,-15,-80]
                    },
                    b:{
                        height:5,
                        width:5,
                        lineHeight: 5,
                        marginBottom: 10,
                        padding:[0,-5],
                        borderRadius:5,
                        backgroundColor:color[i], // 圆点颜色和饼图块状颜色一致
                    }
                },

            }
        }

        opts.push(item)
    }
    return opts;
}


export default class Pie2 extends Component {
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
        let total = 0

        for (const key of keys) {
            total += stats.categories[key].expense
        }
        for (const key of keys){
            categories.push({
                "key": key,
                "orderIds": stats.categories[key].orderIds,
                "orderNum": stats.categories[key].orderIds.length,
                "totalExpense": stats.categories[key].expense,
                "portion": stats.categories[key].expense/total * 100,
            })
        }
        categories.sort((a,b) =>  b.portion - a.portion )
        let sum = 0.0;
        for (const category of categories){
            data.push({
                "name": category.key,
                "value": category.portion
            })
            sum += category.portion
            if (sum >= 95) {
                data.push({
                    "name": "其他类别",
                    "value": 100 - sum
                })
                break
            }
        }
        console.log(data)
        // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.getInstanceByDom(
            document.getElementById("pie2-echarts")
        );
        if (myChart === undefined) {
            myChart = echarts.init(document.getElementById("pie2-echarts"));
        }
        // 绘制图表，option设置图表格式及源数据

        const option = {
            backgroundColor: '#fff',
            animation: true,
            series: [
                {
                    color:color,
                    name: '饼图圆点',
                    type: 'pie',
                    radius: ['35%', '50%'],
                    avoidLabelOverlap: false,
                    labelLine: {
                        normal: {
                            show: true,
                            length: 15, // 第一段线 长度
                            length2: 100, // 第二段线 长度
                            align: 'right'
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:setLabel(data)
                }
            ]
        }

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
                id="pie2-echarts"
                style={{ width: "100%", height: "100%", minHeight: 500 }}
            ></div>
        );
    }
}
