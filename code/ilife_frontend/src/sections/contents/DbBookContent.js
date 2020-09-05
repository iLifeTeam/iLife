import React, { Component } from "react";
import axios from "../../axios";
import { createBrowserHistory } from "history";
import DoubanBooks from "../../douban/DoubanBooks";
import { Button, Divider, message, Popconfirm, Typography, Input } from "antd";
import "antd/dist/antd.css";
const { Text, Paragraph } = Typography;
const text = (
  <div>
    <Paragraph>所有数据和图片均来自网上公开资料，请放心食用。</Paragraph>
    <Paragraph>数据更新将会花费一段时间，请耐心等待~</Paragraph>
    希望获取几页数据? <input id="bookInput" />
  </div>
);
export default class DbBookContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      show: false,
      doubanId: null,
      username: "",
      password: "",
      activities: null,
      statsLoading: false,
      statsReady: false,
      stats: null,
    };
    //this.login = this.login.bind(this);
    this.getMovies = this.getMovies.bind(this);
    this.crawl = this.crawl.bind(this);
  }

  componentDidMount() {
    const username = window.sessionStorage.getItem("username");

    if (username === null || username === undefined) {
      const history = createBrowserHistory();
      history.push("/login");
      window.location.reload();
    }

    const script = document.createElement("script");

    script.src = "../../dist/js/content.js";
    script.async = true;
    document.body.appendChild(script);
    this.getMovies(username);
  }

  async getMovies(username) {
    var config = {
      method: "get",
      url: "http://18.166.111.161:8686/auth/getByAccount?account=" + username,
      headers: {
        withCredentials: true,
      },
    };

    const doubanId = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        return response.data.doubanid;
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });

    this.setState({ doubanId });
    console.log(doubanId);
    var config = {
      method: "get",
      url: "http://121.36.196.234:8383/douban/getBooks?userId=" + doubanId,
      headers: {
        withCredentials: true,
      },
    };

    const activities = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
    this.setState({ activities: activities });
  }

  nameOnChange(val) {
    this.setState({
      username: val.target.value,
    });
  }

  psdOnChange(val) {
    this.setState({
      password: val.target.value,
    });
  }

  crawl = () => {
    const that = this;
    let bookInput = document.getElementById("bookInput");
    console.log(bookInput.value);
    if (
      bookInput.value === null ||
      bookInput.value === "" ||
      bookInput.value === 0
    ) {
      bookInput.value = 2;
    }
    message.loading({
      content: "正在更新图书数据，请稍作等待！",
      style: { marginTop: "40px" },
      duration: 0,
    });
    var config = {
      method: "get",
      url:
        "http://121.36.196.234:8484/douban/crawlMovie?userId=" +
        this.state.doubanId +
        "&limit=" +
        bookInput.value +
        "&type=book",
      headers: {
        withCredentials: true,
      },
    };

    axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        that.componentDidMount();
        message.destroy();
        message.success({
          content: "图书数据更新成功！请重新进入页面查看",
          style: { marginTop: "40px" },
        });
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  /* start 文案 here <- bad comment style*/
  doubanServer = "http://121.36.196.234:8383";
  doubanCrawlServer = "http://18.166.24.220:8484";
  fetchStats = (userId) => {
    const config = {
      method: "get",
      url: this.doubanServer + "/douban/getBookStats?userId=" + userId,
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
    };
    this.setState({
      statsLoading: true,
    });
    axios(config).then((response) => {
      console.log(response.data);
      this.setState({
        stats: response.data,
        statsLoading: false,
        statsReady: true,
      });
    });
  };

  /* end 文案 here*/

  changeId = async (e) => {
    let userId = window.sessionStorage.getItem("iLifeId");
    let dbId = document.getElementById("changeId").value;

    let data1 = {
      userId: userId,
      dbId: dbId,
    };
    var config = {
      method: "post",
      data: data1,
      url: "http://18.166.111.161:8686/auth/updateDbId",
      headers: {
        withCredentials: true,
      },
    };

    const doubanId = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    if (doubanId) this.setState({ doubanId: dbId });
    this.componentDidMount();
  };

  render() {
    const { activities, stats, statsReady, statsLoading, userId } = this.state;
    return (
      <div className="content-wrapper">
        <section className="content" id="records">
          <div className="row">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <Divider
                    orientation="left"
                    style={{ color: "#333", fontWeight: "normal" }}
                  >
                    {" "}
                    <h3 className="box-title">
                      用户{this.state.doubanId}的豆瓣图书数据
                    </h3>
                  </Divider>
                  <Popconfirm
                    placement="bottomLeft"
                    title={text}
                    onConfirm={this.crawl}
                    okText="更新数据"
                    cancelText="再想想"
                  >
                    <p className="btn btn-danger">更新数据</p>
                  </Popconfirm>
                  <p
                    className="btn btn-primary"
                    onClick={() => {
                      this.setState({ show: !this.state.show });
                    }}
                  >
                    绑定账户
                  </p>
                  {this.state.show ? (
                    <Input
                      id="changeId"
                      style={{ marginTop: 10 }}
                      placeholder={"输入豆瓣用户主页中浏览器地址栏处的用户ID"}
                      suffix={<Button onClick={this.changeId}>确认</Button>}
                    />
                  ) : null}
                </div>
                <div className="box-body">
                  {<DoubanBooks activities={activities} />}
                </div>
              </div>
            </div>
          </div>
        </section>
        <section>
          <div className="row" id="analyse">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">
                    用户{this.state.weiboId}的豆瓣读书报表
                  </h3>
                  {statsReady?null:
                    <Button
                        size={"large"}
                        loading={statsLoading}
                        onClick={() => {
                          this.fetchStats(this.state.doubanId);
                        }}
                    >
                      生成报表
                    </Button>
                  }
                </div>
                {statsReady ? (
                  <div className="box-body" id="analyse" style={{ fontSize: "18px" }}>
                    <Paragraph>
                      孙中山有言：“我一生的嗜好，除了革命之外，就是读书。我一天不读书，就不能够生活。”
                    </Paragraph>
                    <Paragraph>
                      你是一个爱看书的人，从你踏入豆瓣的小世界以来，你已经阅读了
                      {stats.allBook}本书，书海无涯，知识作舟，要继续保持哦。
                    </Paragraph>
                    <Paragraph>在你阅读的书籍中：</Paragraph>
                    <Paragraph>
                      平均评分是
                      <Text mark strong>
                        {stats.avgRanking}
                      </Text>
                      ，评分最高的是
                      <Text mark strong>
                        {stats.maxRankingBook.name}
                      </Text>
                      ，达到了
                      <Text mark strong>
                        {stats.maxRanking}
                      </Text>
                      分，这是一本由
                      <Text mark strong>
                        {stats.maxRankingBook.author}
                      </Text>
                      所著的书，不知道你是否欣喜与它相遇呢？
                    </Paragraph>
                    <Paragraph>
                      平均价格是
                      <Text mark strong>
                        {stats.avgPrice}
                      </Text>
                      ，价格最高的是
                      <Text mark strong>
                        {stats.maxPriceBook.name}
                      </Text>
                      ，需要
                      <Text mark strong>
                        {stats.maxPrice}
                      </Text>
                      大洋，这是一本由
                      <Text mark strong>
                        {stats.maxPriceBook.author}
                      </Text>
                      所著的书，想必它一定有你所中意之处吧？
                    </Paragraph>
                    <Paragraph>
                      平均热度是
                      <Text mark strong>
                        {stats.avgHot}
                      </Text>
                      ，热度最高的是
                      <Text mark strong>
                        {stats.maxHotBook.name}
                      </Text>
                      ，共有
                      <Text mark strong>
                        {stats.maxHot}
                      </Text>
                      看过，这是一本由
                      <Text mark strong>
                        {stats.maxHotBook.author}
                      </Text>
                      所著的书，这样的爆款书目，读起来一定畅快淋漓吧？
                    </Paragraph>
                    <Paragraph>
                      热度最低的是
                      <Text mark strong>
                        {stats.minHotBook.name}
                      </Text>
                      ，共有
                      <Text mark strong>
                        {stats.minHot}
                      </Text>
                      人看过，这是一本由
                      <Text mark strong>
                        {stats.minHotBook.author}
                      </Text>
                      所著的书，愿意读小众书籍的人，运气都不会太差！
                    </Paragraph>
                    <Paragraph>
                      你的热度喜好是{stats.preferHot + 1}颗星
                    </Paragraph>
                    <Paragraph>
                      你最喜欢的作者是
                      <Text mark strong>
                        {stats.preAuthor}
                      </Text>
                      ，读一个人的著作，也是和人心灵沟通的一种方式。
                    </Paragraph>
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
