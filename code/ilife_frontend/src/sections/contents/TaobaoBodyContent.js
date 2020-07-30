import React, { Component } from 'react'
import axios from 'axios';
import {Table,Badge, Menu, Dropdown,Button} from 'antd';
import 'antd/dist/antd.css';


const expandedRowRender = (row) => {
  console.log("expanded row render",row)
  const columns = [
    {title:'商品名', dataIndex:'product',key:'product'},
    {title:'数量', dataIndex:'number',key:'number'},
    {title:'图片', dataIndex:'imgUrl',key:'imgUrl',
      render: (imgUrl) =>
        <div><img src={imgUrl}></img></div>
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
    defaultSortOrder:['descend']
  },
  {title:'日期', dataIndex:'date',key:'date', align: 'left',
    sorter: (a, b) => a.date > b.date,
    sortDirections: ['descend', 'ascend'],
    defaultSortOrder:['descend']},
]
export default class TaobaoBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      uid: "default",
      phone:"",
      smsCode:"",
      loginSuccess: false,
      loading: false,
      updating: false,
      fetching: false,
      btnDisabled: false,
      orders: []
    }
  }
  componentDidMount() {
    const script = document.createElement("script");

    script.src = "dist/js/content.js";
    script.async = true;
    document.body.appendChild(script);
    this.getUid();
  }

  server = "http://18.162.168.229"
  port = 8095
  getUid = () => {
    this.setState({
      uid: "zhaoxuyang13"
    })
  }
  phoneOnChange = (phone) =>{
    this.setState({
      phone:phone.target.value
    })
  }
  codeOnChange = (smsCode) =>{
    this.setState({
      smsCode: smsCode.target.value
    })
  }
  loginRequest = (phone,smsCode) => {
    const config = {
      method: 'post',
      url: this.server + ":" + this.port  + '/login/sms',
      headers: {
        'Content-Type': 'application/json'
      },
      params: {
        phone: phone,
        smsCode:smsCode
      },
      withCredentials:true
    };
    console.log(config.url)
    axios(config)
        .then(response => {
          if (response.data == "success")
          this.setState({
            loginSuccess:true
          })
        })
  }
  fetchSmsRequest = (phone) => {
    console.log("fetch sms")
    const config = {
      method: 'post',
      url: this.server + ":" + this.port +  '/login/sms/fetch',
      headers: {
        'Content-Type': 'application/json'
      },
      params: {
        phone: phone
      },
      withCredentials:true
    };

    this.setState({
      loading: true
    })
    // console.log(config.url)
    axios(config)
        .then(response => {
          if (response.data == "success") {
            this.setState({
              btnDisabled: true,
              loading: false,
            })
          } else if (response.data == "already login") {
            this.setState({
              btnDisabled: true,
              loading: false,
              loginSuccess: true,
            })
            this.fetchAfter(phone, "2020-05-01")
          } else {
            console.log("failed", response.data)
            this.setState({
              loading: false
            })
          }
        })
  }
  updateIncremental = (phone) => {
    const config = {
      method: 'post',
      url: this.server + ":" + this.port  + '/order/crawl/incremental',
      headers: {
        'Content-Type': 'application/json'
      },
      params: {
        username: phone,
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
          this.fetchAfter(phone, "2020-05-01")
        })
  }
  fetchAfter = (phone,date) =>{
    const config = {
      method: 'get',
      url: this.server + ":" + this.port  + '/order/between',
      headers: {
        'Content-Type': 'application/json'
      },
      params: {
        username: phone,
        low: date,
        high: "2030-01-01"
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
  fetchAll = (phone) => {
    const config = {
      method: 'get',
      url: this.server + ":" + this.port  + '/order/all',
      headers: {
        'Content-Type': 'application/json'
      },
      params: {
        username: phone,
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
    const { orders,loginSuccess,uid,phone,btnDisabled,loading,loginLoading,smsCode} = this.state;
    console.log(this.state)
    return (
      <div className="content-wrapper">
        <section className="content">
          <div className="row">
            <div className="col-md-9">
              <div className="box box-primary">
                <div className="box-header with-border">
                  <h3 className="box-title">短信验证码登录</h3>
                </div>
                {/* form start */}
                { loginSuccess ? <div>登陆成功</div>
                    :
                <form role="form">
                  <div className="box-body">
                    <div className="form-group">
                      <label htmlFor="exampleInputEmail1">手机号码</label>
                      <input type="phone" className="form-control" id="exampleInputEmail1" placeholder="Enter email"
                             onChange={this.phoneOnChange} />
                    </div>
                    <div className="form-group">
                      <label htmlFor="exampleInputPassword1">验证码</label>
                        <Button
                          type="text"
                          disabled={btnDisabled}
                          loading={loading}
                          onClick={()=>{this.fetchSmsRequest(phone)}}
                        > 获取验证码 </Button>
                      <input type="password" className="form-control" id="exampleInputPassword1" placeholder="Password"
                             onChange={this.codeOnChange} />
                      <div className="form-group">
                        <Button
                            type="primary"
                            loading={loginLoading}
                            disabled={smsCode==""}
                            onClick={() => this.loginRequest(phone,smsCode)}
                        > 登录 </Button>
                      </div>
                    </div>
                  </div>
                </form>
                    }
              </div>
            </div>
          </div>
          <div className="row">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">淘宝购物记录</h3>
                  <Button
                      onClick={()=>this.updateIncremental(phone)}
                      disabled={!loginSuccess}
                  >
                    更新数据
                  </Button>
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
