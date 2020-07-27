import React, { Component } from 'react'
import axios from 'axios';
import JingdongOrders from "../../jingdong/JingdongOrders";
import {Table,Badge, Menu, Dropdown} from 'antd';
import 'antd/dist/antd.css';


const expandedRowRender = (row) => {
  console.log("expanded row render",row)
  const columns = [
    {title:'商品名', dataIndex:'product',key:'product'},
    {title:'数量', dataIndex:'number',key:'number'},
    {title:'图片', dataIndex:'imgUrl',key:'imgUrl',
      render: (imgUrl) =>
        <div><img src={"http:" + imgUrl}></img></div>
      },
    {title:'单价', dataIndex:'price',key:'price',},
  ]
  return <Table columns={columns} dataSource={row.items}  pagination={false} />;
}
const columns = [
  {title:'订单号', dataIndex:'orderID',key:'orderID', align: 'left'},
  {title:'商店', dataIndex:'shop',key:'shop'},
  {title:'总价', dataIndex:'total',key:'total',render:(price)=>
      <div>¥ {price}</div>
    ,
    sorter: (a, b) => a.total > b.total,
    sortDirections: ['descend', 'ascend'],
  },
  {title:'日期', dataIndex:'date',key:'date', align: 'left',
    sorter: (a, b) => a.date > b.date,
    sortDirections: ['descend', 'ascend'],},
]
export default class JingdongBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      uid: "default",
      qrCodeBase64: "",
      qrCodeReady: false,
      loginSuccess: false,
      updating: false,
      fetching: false,
      orders: []
    }
  }
  componentDidMount() {
    const script = document.createElement("script");

    script.src = "dist/js/content.js";
    script.async = true;
    document.body.appendChild(script);
    this.getUid();
    setTimeout(() => {
      this.checkLoginRequest(this.state.uid)
      this.loginRequest(this.state.uid)
    },0)
  }

  server = "http://18.162.168.229"
  port = 8097
  getUid = () => {
    this.setState({
      uid: "zhaoxuyang13"
    })
  }
  loginRequest = (uid) => {
    const config = {
      method: 'post',
      url: this.server + ":" + this.port  + '/login/qrcode',
      headers: {
        'Content-Type': 'application/json'
      },
      params: {
        username: uid
      },
      withCredentials:true
    };
    console.log(config.url)
    axios(config)
        .then(response => {
          console.log("qrCorde", response.data)
          this.setState({
            qrCodeBase64:`data:image/png;base64,${response.data}`,
            qrCodeReady: true
          })
        })
  }
  checkLoginRequest = (uid) => {
    console.log("check login")
    const config = {
      method: 'get',
      url: this.server + ":" + this.port +  '/login/check',
      headers: {
        'Content-Type': 'application/json'
      },
      params: {
        username: uid
      },
      withCredentials:true
    };
    // console.log(config.url)
    axios(config)
        .then(response => {
          console.log(response.data)
          if (response.data){
            this.setState({loginSuccess:true})
            this.fetchAll(uid)
          }else {
            setTimeout(() => {
              this.checkLoginRequest(uid)
            }, 3000)
          }
        })
  }
  updateIncremental = (uid) => {
    const config = {
      method: 'post',
      url: this.server + ":" + this.port  + '/order/crawl/incremental',
      headers: {
        'Content-Type': 'application/json'
      },
      params: {
        username: uid
      },
      withCredentials:true
    }
    this.setState({
      updating: true
    })
    axios(config)
        .then(response => {
          this.setState({
            updating: false,
          })
          console.log(response)
          console.log("更新了" + response.data + "条购物信息")
          this.fetchAll(uid)
        })
  }
  fetchAll = (uid) => {
    const config = {
      method: 'get',
      url: this.server + ":" + this.port  + '/order/all',
      headers: {
        'Content-Type': 'application/json'
      },
      params: {
        username: uid,
      },
      withCredentials:true
    }
    this.setState({
      fetching: true
    })
    axios(config)
        .then(response => {
          console.log()
          const orders = response.data.map((order,index) => {
            order.key = index
            order.items = order.items.map((item,index) => {
              item.key = index
              return item
            })
            return order
          })
          this.setState({
            fetching: false,
            orders: orders
          })
        })
  }
  render() {
    const style = {
      flex:1
    }
    const { orders,loginSuccess,qrCodeBase64,qrCodeReady,uid} = this.state;
    console.log(this.state)
    return (
      <div className="content-wrapper">
        <section className="content">
          <div className="row">
            <div className="col-md-9">
              <div className="box box-primary">
                <div className="box-header with-border">
                  <h3 className="box-title">扫码登录</h3>
                </div>
                {/* form start */}
                <form role="form">
                  <div className="box-body">
                    <div className="form-group">
                      <div>
                      {
                        loginSuccess ?
                            <div> 登录成功！</div>
                            :
                            (qrCodeReady ? <img src={qrCodeBase64} className="img-square" alt="验证码"/>
                                : <div> 正在加载二维码... </div>)

                      }</div>
                    </div>
                  </div>
                </form></div></div>
          </div>
          <div className="row">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">京东购物记录</h3>
                  <button onClick={()=>this.updateIncremental(uid)}>
                    更新数据
                  </button>
                  {this.state.updating ?   <div>正在增量式爬取...</div> : null}
                  {this.state.fetching ?   <div>正在获取...</div> : null}
                </div>
                <div className="box-body" style={style}>
                  {/*<table id="example1" className="table table-bordered table-striped">*/}
                    <Table
                        columns={columns}
                        expandable={{ expandedRowRender }}
                        dataSource={orders}
                        bordered
                    >
                    </Table>
                    {/*<thead>*/}
                    {/*  <tr>*/}
                    {/*    <th>orderId</th>*/}
                    {/*    <th>question</th>*/}
                    {/*    <th>answer</th>*/}
                    {/*    <th>target_id</th>*/}
                    {/*    <th>created_time</th>*/}
                    {/*  </tr>*/}
                    {/*</thead>*/}
                    {/*<tbody>*/}
                    {/*  {orders.length > 0 ?  orders.map((order, index) => (*/}
                    {/*    <JingdongOrders*/}
                    {/*    ></JingdongOrders>*/}
                    {/*  )) : null }*/}

                    {/*</tbody>*/}
                    {/*<tfoot>*/}
                    {/*  <tr>*/}
                    {/*    <th>action_text</th>*/}
                    {/*    <th>question</th>*/}
                    {/*    <th>answer</th>*/}
                    {/*    <th>target_id</th>*/}
                    {/*    <th>created_time</th>*/}
                    {/*  </tr>*/}
                    {/*</tfoot>*/}
                  {/*</table>*/}
                </div>
              </div>
            </div>
          </div>
        </section>
      </div >
    )
  }
}
