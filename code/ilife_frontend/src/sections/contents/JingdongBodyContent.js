import React, { Component } from "react";
import axios from "../../axios";
import {
  Table,
  Badge,
  Menu,
  Dropdown,
  Divider,
  Button,
  Space,
  Row,
  Col,
  DatePicker,
  Typography,
  List,
  Statistic,
  Spin,
} from "antd";
import "antd/dist/antd.css";
import { createBrowserHistory } from "history";
import moment from "moment";
import Pie2 from "../../taobao/Pie2";
import Pie from "../../taobao/Pie";
import { AccountBookOutlined, BarChartOutlined } from "@ant-design/icons";

const { RangePicker } = DatePicker;
const { Text, Paragraph } = Typography;
const expandedRowRender = (row) => {
  console.log("expanded row render", row);
  const columns = [
    { title: "商品名", dataIndex: "product", key: "product" },
    {
      title: "图片",
      dataIndex: "imgUrl",
      key: "imgUrl",
      render: (imgUrl) => (
        <div>
          <img src={"http:" + imgUrl}></img>
        </div>
      ),
    },
    { title: "一级类目", dataIndex: "firstCategory", key: "firstCategory" },
    { title: "二级类目", dataIndex: "secondCategory", key: "secondCategory" },
    { title: "三级类目", dataIndex: "thirdCategory", key: "thirdCategory" },
  ];
  return <Table columns={columns} dataSource={row.items} pagination={false} />;
};
const columns = [
  { title: "订单号", dataIndex: "orderID", key: "orderID", align: "left" },
  { title: "商店", dataIndex: "shop", key: "shop" },
  {
    title: "总价",
    dataIndex: "total",
    key: "total",
    render: (price) => <div>¥ {price}</div>,
    sorter: (a, b) => a.total > b.total,
    sortDirections: ["descend", "ascend"],
  },
  {
    title: "日期",
    dataIndex: "date",
    key: "date",
    align: "left",
    sorter: (a, b) => a.date > b.date,
    sortDirections: ["descend", "ascend"],
  },
];
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
      orders: [],
      stats: null,
      statsLoading: false,
      statsReady: false,
      updatingStats: false,
      updateComplete: false,
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
    const script = document.createElement("script");
    script.src = "dist/js/content.js";
    script.async = true;
    document.body.appendChild(script);

    this.getUid(username);
    setTimeout(() => {
      this.checkLoginRequest(this.state.uid);
    }, 0);
  }

  server = "http://18.166.111.161";
  port = "8000/jingdong";
  getUid = (username) => {
    this.setState({
      uid: username,
    });
  };
  loginRequest = (uid) => {
    const config = {
      method: "post",
      url: this.server + ":" + this.port + "/login/qrcode",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        username: uid,
      },
      withCredentials: true,
    };
    console.log(config.url);
    axios(config).then((response) => {
      console.log("qrCorde", response.data);
      this.setState({
        qrCodeBase64: `data:image/png;base64,${response.data}`,
        qrCodeReady: true,
      });
    });
  };
  checkLoginRequest = (uid) => {
    console.log("check login");
    const config = {
      method: "get",
      url: this.server + ":" + this.port + "/login/check",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        username: uid,
      },
      withCredentials: true,
    };
    const { qrCodeReady } = this.state;
    // console.log(config.url)
    axios(config).then((response) => {
      console.log(response.data);
      if (response.data) {
        this.setState({ loginSuccess: true });
        this.fetchAll(uid);
        this.updateStats(uid);
      } else {
        if (!qrCodeReady) {
          this.loginRequest(this.state.uid);
        }
        setTimeout(() => {
          this.checkLoginRequest(uid);
        }, 5000);
      }
    });
  };
  updateStats = (uid) => {
    const config = {
      method: "post",
      url: this.server + ":" + this.port + "/stats/category/update",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        username: uid,
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
      this.fetchAll(uid);
    });
  };

  updateIncremental = (uid) => {
    const config = {
      method: "post",
      url: this.server + ":" + this.port + "/order/crawl/incremental",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        username: uid,
      },
      withCredentials: true,
      timeout: 60 * 1000 * 2,
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
      this.fetchAll(uid);
    });
  };
  fetchAll = (uid) => {
    const config = {
      method: "get",
      url: this.server + ":" + this.port + "/order/all",
      headers: {
        "Content-Type": "application/json",
      },
      params: {
        username: uid,
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
    });
  };
  fetchStats = () => {
    this.setState({
      statsLoading: true,
    });
    const { orders } = this.state;
    let mostExpensive = null,
      maxPrice = 0,
      expenseYear = 0,
      expenseMonth = 0,
      countYear = 0,
      countMonth = 0,
      categories = {};
    const today = new Date();
    for (const order of orders) {
      if (order.total > maxPrice) {
        maxPrice = order.total;
        mostExpensive = order;
      }
      const orderDate = new Date(order.date);
      // console.log(order,orderDate)
      if (orderDate.getFullYear() == today.getFullYear()) {
        expenseYear += order.total;
        countYear += order.items.length;
        if (orderDate.getMonth() == today.getMonth()) {
          expenseMonth += order.total;
          countMonth += order.items.length;
        }
        let category = null,
          count = 0;
        for (const item of order.items) {
          if (count == 0) {
            category = item.firstCategory;
            count = 1;
            continue;
          }
          if (item.firstCategory == category) count++;
          else count--;
        }
        if (categories[category] != null) {
          categories[category].expense += order.total;
          categories[category].orderIds.push(order.orderID);
        } else {
          categories[category] = {
            expense: order.total,
            orderIds: [order.orderID],
          };
        }
      }
    }
    console.log(categories);

    this.setState({
      statsReady: true,
      statsLoading: false,
      stats: {
        mostExpensive: mostExpensive,
        expenseMonth: expenseMonth,
        expenseYear: expenseYear,
        countMonth: countMonth,
        countYear: countYear,
        categories: categories,
      },
    });
  };
  render() {
    const style = {
      flex: 1,
    };
    const {
      orders,
      loginSuccess,
      qrCodeBase64,
      qrCodeReady,
      uid,
      statsLoading,
      statsReady,
      stats,
    } = this.state;
    console.log(this.state);
    return (
      <div className="content-wrapper">
        <section className="content" id="bills">
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
                        {loginSuccess ? (
                          <div> 登录成功！</div>
                        ) : qrCodeReady ? (
                          <img
                            src={qrCodeBase64}
                            className="img-square"
                            alt="验证码"
                          />
                        ) : (
                          <div> 正在加载二维码... </div>
                        )}
                      </div>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
          <div className="row">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">京东购物记录</h3>
                  <button onClick={() => this.updateIncremental(uid)}>
                    更新数据
                  </button>
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
                    <h3 className="box-title">我的京东购物统计</h3>
                  </Divider>

                  <Button
                    loading={statsLoading}
                    onClick={() => {
                      this.fetchStats();
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
                {statsReady ? (
                  <div style={{ marginLeft: "100px" }}>
                    <Divider orientation="left">总览</Divider>
                    <Row justify="left">
                      <Col span={24}>
                        <Row gutter={24}>
                          <Col span={12}>
                            <Statistic
                              title="本月购入"
                              value={stats.countMonth}
                              prefix={<BarChartOutlined />}
                              suffix="件"
                            />
                          </Col>
                          <Col span={12}>
                            <Statistic
                              title="本月消费"
                              value={stats.countMonth}
                              prefix={<AccountBookOutlined />}
                            />
                          </Col>
                        </Row>
                        <Row gutter={24}>
                          <Col span={12}>
                            <Statistic
                              title="今年购入"
                              value={stats.countYear}
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
                          style={{ width: "100%" }}
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
                    <Divider orientation="left">购物统计</Divider>
                    <Row justify="center">
                      <Col style={{ width: "70%" }}>
                        <Pie stats={stats} />
                      </Col>
                      <Col style={{ width: "30%" }}>
                        <Divider orientation="left">购物花销分布</Divider>
                        <Pie2 stats={stats} />
                      </Col>
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
