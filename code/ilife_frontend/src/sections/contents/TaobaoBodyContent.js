import React, { Component } from "react";

import axios from "axios";
import {
  Table,
  Badge,
  Menu,
  Dropdown,
  Button,
  Divider,
  Row,
  Col,
  Typography,
  Space,
  DatePicker,
  Statistic,
  List,
  Spin,
} from "antd";

import "antd/dist/antd.css";
import { createBrowserHistory } from "history";
import Pie from "../../taobao/Pie";
import Pie2 from "../../taobao/Pie2";
import moment from "moment";
import { AccountBookOutlined, BarChartOutlined } from "@ant-design/icons";

const { RangePicker } = DatePicker;
const { Text, Paragraph } = Typography;

const expandedRowRender = (row) => {
  console.log("expanded row render", row);
  const columns = [
    { title: "商品名", dataIndex: "product", key: "product" },
    { title: "数量", dataIndex: "number", key: "number" },
    {
      title: "图片",
      dataIndex: "imgUrl",
      key: "imgUrl",
      render: (imgUrl) => (
        <div>
          <img src={imgUrl}></img>
        </div>
      ),
    },
    { title: "单价", dataIndex: "price", key: "price" },
    { title: "一级类目", dataIndex: "firstCategory", key: "firstCategory" },
    { title: "二级类目", dataIndex: "secondCategory", key: "secondCategory" },
    { title: "三级泪目", dataIndex: "thirdCategory", key: "thirdCategory" },
  ];
  return <Table columns={columns} dataSource={row.items} pagination={false} />;
};
export default class TaobaoBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      uid: "default",
      phone: "",
      smsCode: "",
      loginSuccess: false,
      crawlerEnabled: false,
      loading: false,
      updating: false,
      fetching: false,
      btnDisabled: false,
      orders: [],
      account: "",
      stats: null,
      statsLoading: false,
      statsReady: false,
      updatingStats: false,
      updatingComplete: false,
      columns : [
        { title: "订单号", dataIndex: "orderID", key: "orderID", align: "left" },
        { title: "商店", dataIndex: "shop", key: "shop",
          filters: [],
          filterMultiple: true,
          onFilter: (value, record) => record.items.some(item => item.firstCategory.indexOf(value) === 0),
        },
        {
          title: "总价",
          dataIndex: "total",
          key: "total",
          render: (price) => <div>¥ {price}</div>,
          sorter: (a, b) => a.total > b.total,
          sortDirections: ["descend", "ascend"],
          defaultSortOrder: ["descend"],
        },
        {
          title: "日期",
          dataIndex: "date",
          key: "date",
          align: "left",
          sorter: (a, b) => a.date > b.date,
          sortDirections: ["descend", "ascend"],
          defaultSortOrder: ["descend"],
        },
      ]
    };
  }

  componentDidMount() {
    let arr,
      reg = new RegExp("(^| )" + "username" + "=([^;]*)(;|$)");
    let username = "";
    if ((arr = document.cookie.match(reg))) {
      username = unescape(arr[2]);
    } else {
      username = null;
    }
    this.setState({
      username: username,
    });

    if (!username) {
      const history = createBrowserHistory();
      history.push("/login");
      window.location.reload();
    }
    this.setState({
      account: username,
    });
    const script = document.createElement("script");
    script.src = "dist/js/content.js";
    script.async = true;
    document.body.appendChild(script);
    this.fetchUserinfo(username);
  }

  server = "http://18.166.111.161";
  port = 8000;
  authPort = 8686;
  fetchUserinfo = (account) => {
    const config = {
      method: "get",
      url:
        this.server +
        ":" +
        this.authPort +
        "/auth/getByAccount?account=" +
        account,
      headers: {
        withCredentials: true,
      },
    };
    axios(config).then((response) => {
      console.log(response.data);
      const phone = response.data.tbid == "0" ? "" : response.data.tbid;
      this.setState({
        phone: phone,
        uid: response.data.id,
      });
      if (phone != null) {
        // this.fetchSmsRequest(phone)
      }
    });
  };
  setTbId = (phone) => {
    const config = {
      method: "post",
      url: this.server + ":" + this.authPort + "/auth/updateTbId",
      headers: {
        withCredentials: true,
      },
      data: {
        userId: this.state.uid,
        TbId: phone,
      },
    };
    axios(config).then((response) => {
      console.log("update tbid", response);
    });
  };
  phoneOnChange = (phone) => {
    this.setState({
      phone: phone.target.value,
    });
  };
  codeOnChange = (smsCode) => {
    this.setState({
      smsCode: smsCode.target.value,
    });
  };
  fakeLogin = (phone) => {
    this.setState({
      loginSuccess: true,
    });
    // this.fetchAfter(phone, "2020-01-01") // todo: change this
    this.updateStats(phone);
    this.fetchAll(phone);
  };
  loginRequest = (phone, smsCode) => {
    const config = {
      method: "post",
      url: this.server + ":" + this.port + "/taobao/login/sms",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        phone: phone,
        smsCode: smsCode,
      },
      withCredentials: true,
    };
    console.log(config.url);
    axios(config).then((response) => {
      if (response.data == "success")
        this.setState({
          loginSuccess: true,
          crawlerEnabled: true,
        });
      this.setTbId(phone);
      this.fetchAfter(phone, "2020-01-01"); // todo: change this
    });
  };
  fetchSmsRequest = (phone) => {
    console.log("fetch sms");
    const config = {
      method: "post",
      url: this.server + ":" + this.port + "/taobao/login/sms/fetch",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        phone: phone,
      },
      withCredentials: true,
    };

    this.setState({
      loading: true,
    });
    // console.log(config.url)
    axios(config).then((response) => {
      if (response.data == "success") {
        this.setState({
          btnDisabled: true,
          loading: false,
          crawlerEnabled: true,
        });
      } else if (response.data == "already login") {
        this.setState({
          btnDisabled: true,
          loading: false,
          loginSuccess: true,
          crawlerEnabled: true,
        });
        this.setTbId(phone);
        this.fetchAfter(phone, "2020-01-01"); // todo: change this
      } else {
        console.log("failed", response.data);
        this.setState({
          loading: false,
        });
      }
    });
  };
  updateIncremental = (phone) => {
    const config = {
      method: "post",
      url: this.server + ":" + this.port + "/taobao/order/crawl/incremental",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        username: phone,
      },
      withCredentials: true,
      timeout: 60 * 1000 * 10,
    };
    this.setState({
      updating: true,
    });
    axios(config).then((response) => {
      this.setState({
        updating: false,
      });
      console.log(response);
      console.log("更新了" + response.data + "条购物信息");
      this.updateStats(phone);
      // this.fetchAfter(phone, "2020-03-01") // todo: alter this
      this.fetchAll(phone);
    });
  };
  updateAll = (phone) => {
    const config = {
      method: "post",
      url: this.server + ":" + this.port + "/taobao/order/crawl/all",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        username: phone,
      },
      withCredentials: true,
    };
    this.setState({
      updating: true,
    });
    axios(config).then((response) => {
      this.setState({
        updating: false,
      });
      console.log(response);
      console.log("更新了" + response.data + "条购物信息");
      // this.fetchAfter(phone, "2020-01-01") // todo: alter this
      this.fetchAll(phone);
    });
  };
  fetchAfter = (phone, date) => {
    const config = {
      method: "get",
      url: this.server + ":" + this.port + "/taobao/order/between",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        username: phone,
        low: date,
        high: "2030-01-01",
      },
      withCredentials: true,
    };
    this.setState({
      fetching: true,
    });
    axios(config)
      .then((response) => {
        console.log();
        const orders = response.data.map((order, index) => {
          order.key = index;
          order.items = order.items.map((item, index) => {
            item.key = index;
            return item;
          });
          return order;
        });
        this.setState({
          fetching: false,
          orders: orders,
        });
        this.updateColumns(orders)
      })
      .catch((err) => {
        console.log(err);
      });
  };
  updateStats = (phone) => {
    const config = {
      method: "post",
      url: this.server + ":" + this.port + "/taobao/stats/category/update",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        username: phone,
      },
      withCredentials: true,
      timeout: 20 * 60 * 1000,
    };
    this.setState({ updatingStats: true });
    axios(config).then((response) => {
      console.log("update complete");
      this.setState({
        updatingStats: false,
        updateComplete: true,
      });
    });
  };
  fetchAll = (phone) => {
    const config = {
      method: "get",
      url: this.server + ":" + this.port + "/taobao/order/all",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        username: phone,
      },
      withCredentials: true,
    };
    this.setState({
      fetching: true,
    });
    axios(config).then((response) => {
      console.log();
      const orders = response.data.map((order, index) => {
        order.key = index;
        order.items = order.items.map((item, index) => {
          item.key = index;
          return item;
        });
        return order;
      });
      this.setState({
        fetching: false,
        orders: orders,
      });
      this.updateColumns(orders)
    });
  };
  updateColumns = (orders) =>{
    let {columns} = this.state
    let filters = [], set = []
    for(const order of orders){
      for (const item of order.items){
        const cate = item.firstCategory
        if (!set.includes(cate)){
          set.push(cate)
          filters.push({
            text: cate,
            value: cate
          })
        }
      }
    }
    columns[1].filters = filters
    console.log(columns)
    this.setState({
      columns:columns
    })
  }
  fetchStats = (phone) => {
    const config = {
      method: "get",
      url: this.server + ":" + this.port + "/taobao/stats",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        username: phone,
      },
      withCredentials: true,
    };
    this.setState({
      statsLoading: true,
    });
    axios(config).then((response) => {
      console.log(response.data);
      this.setState({
        statsLoading: false,
        statsReady: true,
        stats: response.data,
      });
    });
  };
  computeSum = (categories) => {
    console.log(categories);
    let sum = 0;
    for (const key of Object.keys(categories)) {
      sum += categories[key].orderIds.length;
    }
    return sum;
  };
  render() {
    const style = {
      flex: 1,
    };
    const {
      orders,
      loginSuccess,
      uid,
      phone,
      btnDisabled,
      loading,
      updating,
      loginLoading,
      smsCode,
      crawlerEnabled,
      statsLoading,
      stats,
      statsReady,
      updatingStats,
      updateComplete,
      columns,
    } = this.state;
    console.log(this.state);
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
                {loginSuccess ? (
                  <div>登陆成功</div>
                ) : (
                  <form role="form">
                    <div className="box-body">
                      <div className="form-group">
                        <label htmlFor="exampleInputEmail1">手机号码</label>
                        <input
                          type="phone"
                          className="form-control"
                          id="exampleInputEmail1"
                          placeholder="Enter phone"
                          onChange={this.phoneOnChange}
                          value={phone}
                        />
                      </div>
                      <div className="form-group">
                        <label htmlFor="exampleInputPassword1">验证码</label>
                        <Button
                          type="text"
                          disabled={btnDisabled}
                          loading={loading}
                          onClick={() => {
                            this.fetchSmsRequest(phone);
                          }}
                        >
                          {" "}
                          获取验证码{" "}
                        </Button>
                        <input
                          type="password"
                          className="form-control"
                          id="exampleInputPassword1"
                          placeholder="Password"
                          onChange={this.codeOnChange}
                        />
                        <div className="form-group">
                          <Button
                            type="primary"
                            loading={loginLoading}
                            disabled={smsCode == ""}
                            onClick={() => this.loginRequest(phone, smsCode)}
                          >
                            {" "}
                            登录{" "}
                          </Button>
                          <Button
                            type="primary"
                            loading={loginLoading}
                            onClick={() => this.fakeLogin(phone)}
                          >
                            {" "}
                            不登录，直接获取当前帐户数据{" "}
                          </Button>
                        </div>
                      </div>
                    </div>
                  </form>
                )}
              </div>
            </div>
          </div>
          <div className="row">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">淘宝购物记录</h3>
                  <Button
                    onClick={() => this.updateIncremental(phone)}
                    disabled={!crawlerEnabled}
                    loading={updating}
                  >
                    更新数据
                  </Button>{" "}
                  <Button
                    onClick={() => this.updateAll(phone)}
                    loading={updating}
                    disabled={!crawlerEnabled}
                  >
                    全部重新爬取
                  </Button>
                  {this.state.updating ? <div>正在增量式爬取...</div> : null}
                  {this.state.fetching ? <div>正在获取...</div> : null}
                  {this.state.updatingStats ? (
                    <div>
                      正在对数据进一步处理... <Spin />
                    </div>
                  ) : null}
                </div>
                <div className="box-body" style={style}>
                  {/*<table id="example1" className="table table-bordered table-striped">*/}
                  <Table
                    columns={columns}
                    expandable={{ expandedRowRender }}
                    dataSource={orders}
                    bordered
                  ></Table>
                </div>
              </div>
            </div>
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <Divider
                    orientation="left"
                    style={{ color: "#333", fontWeight: "normal" }}
                  >
                    <h3 className="box-title">我的淘宝购物统计</h3>
                  </Divider>

                  <Button
                    loading={statsLoading}
                    onClick={() => {
                      this.fetchStats(phone);
                    }}
                  >
                    生成报表
                  </Button>

                  <Space direction="vertical" size={12}>
                    <RangePicker
                      ranges={{
                        Today: [moment(), moment()],
                        "This Month": [
                          moment().startOf("month"),
                          moment().endOf("month"),
                        ],
                      }}
                      showTime
                      format="YYYY/MM/DD HH:mm:ss"
                      onChange={(dates) => {
                        var GMT = new Date(dates[0]._d);
                        var GMT1 = new Date(dates[1]._d);
                        this.setState({
                          startTime: GMT.toUTCString(),
                          endTime: GMT1.toUTCString(),
                        });
                      }}
                      style={{ marginLeft: "20px" }}
                    />
                  </Space>
                </div>
                {statsReady && updateComplete ? (
                  <div style={{ marginLeft: "100px" }}>
                    <Divider orientation="left">总览</Divider>
                    <Row justify="left">
                      <Col span={16}>
                        <Row gutter={16}>
                          <Col span={12}>
                            <Statistic
                              title="本月消费"
                              value={stats.countMonth}
                              prefix={<AccountBookOutlined />}
                            />
                          </Col>
                          <Col span={12}>
                            <Statistic
                              title="今年购入"
                              value={this.computeSum(stats.categories)}
                              prefix={<BarChartOutlined />}
                              suffix="件"
                            />
                          </Col>
                          <Col span={12}>
                            <Statistic
                              title="今年消费"
                              value={stats.expenseYear}
                              prefix={<AccountBookOutlined />}
                            />
                          </Col>
                        </Row>
                        <List
                          header={
                            <div>
                              我买过最贵的一单花了
                              {stats.mostExpensive.total.toFixed(2)}元, 买了
                              {stats.mostExpensive.items.length}件宝贝
                            </div>
                          }
                          bordered
                          dataSource={stats.mostExpensive.items}
                          renderItem={(item) => (
                            <List.Item>
                              <Typography.Text mark>
                                <img src={item.imgUrl} />
                              </Typography.Text>{" "}
                              {item.product}
                            </List.Item>
                          )}
                        />
                        <Text className="box-body"></Text>
                      </Col>
                    </Row>
                    <Divider orientation="left">购物数量分布</Divider>
                    <Row justify="center">
                      <Pie stats={stats} />
                    </Row>
                    <Divider orientation="left">购物花销分布</Divider>
                    <Row justify="center">
                      <Pie2 stats={stats} />
                    </Row>
                  </div>
                ) : statsLoading ? (
                  <div> "加载中..." </div>
                ) : null}
              </div>
            </div>
          </div>
        </section>
      </div>
    );
  }
}
