import React, { Component } from "react";
import axios from "../../axios";
import { createBrowserHistory } from "history";
import {
  Button,
  Divider,
  Typography,
  Popconfirm,
  message,
  Row,
  Col,
  Card,
} from "antd";
import "antd/dist/antd.css";

const { Meta } = Card;
const { Text, Paragraph } = Typography;
const text = (
  <div>
    <Paragraph>
      '进行推荐前，请确保你已经绑定了豆瓣账户且进行了书籍和电影数据的获取，否则推荐结果将不太准确。
    </Paragraph>
    <Paragraph>所有数据和图片均来自网上公开资料，请放心食用。</Paragraph>
    <Paragraph>推荐将会花费一段时间，请耐心等待~</Paragraph>
  </div>
);
export default class EntertainContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      finish: false,
      pop: false,
      show: false,
      doubanId: null,
      username: "",
      password: "",
      movies: null,
      books: null,
      statsLoading: false,
      statsReady: false,
      stats: null,
      rcmd: null,
    };
  }

  componentWillMount = () => {
    //<meta name="referrer" content="no-referrer" />
    this.appendMeta();
  };
  appendMeta = () => {
    let oMeta = document.createElement("meta");
    oMeta.referrer = "no-referrer";
    document.getElementsByTagName("head")[0].appendChild(oMeta);
  };

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

    script.src = "../../dist/js/content.js";
    script.async = true;
    document.body.appendChild(script);
    this.getInfo(username);
  }

  async getInfo(username) {
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
    this.getStoredRcmd(doubanId);
    console.log(doubanId);
  }

  async saveRcmd(data) {
    data.id = this.state.doubanId;
    console.log("To send", { rcmd: data });
    var config = {
      method: "post",
      data: { rcmd: data },
      url: "http://121.36.196.234:8383/douban/saveRcmd",
      headers: {
        withCredentials: true,
      },
    };
    await axios(config)
      .then(function (response) {
        console.log(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  async getStoredRcmd(doubanId) {
    const that = this;
    var config = {
      method: "get",
      url: "http://121.36.196.234:8383/douban/getStoredRcmd?userId=" + doubanId,
      headers: {
        withCredentials: true,
      },
    };
    const storedRcmd = await axios(config)
      .then(function (response) {
        console.log(response.data);
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
    if (storedRcmd !== "") {
      console.log("stored recommendation retrieved!");
      that.setState({ finish: true, rcmd: storedRcmd });
    } else {
      console.log("can not find stored recommendation");
    }
  }

  /* end 文案 here*/
  async getMovies(doubanId) {
    var config = {
      method: "get",
      url: "http://121.36.196.234:8383/douban/getBooks?userId=" + doubanId,
      headers: {
        withCredentials: true,
      },
    };
    const movies = await axios(config)
      .then(function (response) {
        console.log(response.data);
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
    this.setState({ books: movies });
    this.getBooks(doubanId);
  }

  getBooks = async (doubanId) => {
    var config = {
      method: "get",
      url: "http://121.36.196.234:8383/douban/getMovies?userId=" + doubanId,
      headers: {
        withCredentials: true,
      },
    };

    const books = await axios(config)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
    this.setState({ movies: books });
    this.getAttitude(doubanId);
  };
  getAttitude = async (doubanId) => {
    let data = [];
    for (let movie of this.state.movies) {
      data.push(movie.name);
    }
    for (let book of this.state.books) {
      data.push(book.name);
    }
    console.log(data);
    //通过百度AI获取书籍和电影的情感倾向
    var config = {
      method: "post",
      data: data,
      url: "http://18.166.111.161:8188/baiduapi/analysis",
      headers: {
        withCredentials: true,
      },
    };
    const result = await axios(config)
      .then(function (response) {
        console.log(response.data);
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
    let attitude;
    if (result === null || result === undefined) attitude = 0;
    else if (result.negative === 0) attitude = 1;
    else if (result.positive === 0 || result.positive / result.negative <= 5)
      attitude = 0;
    else attitude = 1;
    var configRcmd = {
      method: "get",
      url: "http://121.36.196.234:8383/douban/getRcmd?userId=" + doubanId,
      headers: {
        withCredentials: true,
      },
    };
    const resultRcmd = await axios(configRcmd)
      .then(function (response) {
        console.log(response.data);
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
    let hashTag =
      resultRcmd.preAuthor.length *
        resultRcmd.preAuthor.length *
        this.state.movies.length +
      this.state.books.length;
    if (hashTag % 7 === 0) hashTag += 1;
    let data2 = {
      bookTagList: resultRcmd.bookTagList,
      preAuthor: resultRcmd.preAuthor,
      movieTagList: resultRcmd.movieTagList,
      musicTag: resultRcmd.musicTag,
      gameTag: resultRcmd.gameTag,
      attitude: attitude,
      hashTag: hashTag,
    };
    console.log(data2);
    const that = this;
    var config2 = {
      method: "post",
      data: data2,
      url: "http://121.36.196.234:8484/douban/crawlRcmd",
      headers: {
        withCredentials: true,
      },
      timeout:"10000ms",
    };
    const finalResult = await axios(config2)
      .then(function (response) {
        message.destroy();
        message.success({
          content: "娱乐推荐完毕！请重新进入页面查看",
          style: { marginTop: "40px" },
        });
        that.setState({ finish: true, rcmd: response.data });
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
    console.log(finalResult);
    this.saveRcmd(finalResult);

    Object.defineProperty(Image.prototype, "authsrc", {
      writable: true,
      enumerable: true,
      configurable: true,
    });
    let img = document.getElementById("img");
    let url = finalResult.picture_movie;
    var config3 = {
      method: "get",
      url: url,
      headers: {
        Referer: "https://movie.douban.com/photos/photo/2548750147/",
      },
    };
    // const moviePic = await axios(config3)
    //     .then(function (response) {
    //         console.log(response.data);
    //         return response.data;
    //     })
    //     .catch(function (error) {
    //         console.log(error);
    //     });
    // img.src = URL.createObjectURL(moviePic);
    // img.onload = () => {
    //     URL.revokeObjectURL(img.src);
    // };
  };
  confirm = () => {
    message.loading({
      content: "正在进行娱乐推荐，请稍作等待！",
      style: { marginTop: "40px" },
      duration: 0,
    });
    this.getMovies(this.state.doubanId);
    return null;
  };
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
  };
  changeSrcMovie = () => {
    let movie = document.getElementById("img");
    let ram = 3;
    if (this.state.movies === null) ram = 7;
    else ram = this.state.movies.length % 4;
    if (ram <= 1)
      movie.setAttribute(
        "src",
        "https://img1.doubanio.com/view/photo/l_ratio_poster/public/p2074715729.webp"
      );
    if (ram <= 2)
      movie.setAttribute(
        "src",
        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1062375456,1059818807&fm=26&gp=0.jpg"
      );
    else {
      movie.setAttribute(
        "src",
        "http://image11.m1905.cn/uploadfile/2015/0827/20150827093855444797.jpg"
      );
    }
    if (
      this.state.rcmd.picture_url ===
      "https://img3.doubanio.com/view/photo/l_ratio_poster/public/p2577799411.jpg"
    ) {
      movie.setAttribute("src", require("../../pic/backMovie1.jpg"));
    }
    if (
      this.state.rcmd.picture_url ===
      "https://img3.doubanio.com/view/photo/l_ratio_poster/public/p2565063791.jpg "
    ) {
      movie.setAttribute("src", require("../../pic/backMovie2.jpg"));
    }
  };
  changeSrcBook = () => {
    let book = document.getElementById("img2");
    book.setAttribute("src", require("../../pic/backBook1.jpg"));
    if (
      this.state.rcmd.picture_book ===
      "https://img1.doubanio.com/view/subject/l/public/s33462079.jpg"
    ) {
      book.setAttribute("src", require("../../pic/backBook2.jpg"));
    }
    if (
      this.state.rcmd.picture_book ===
      "https://img3.doubanio.com/view/subject/l/public/s29866441.jpg"
    ) {
      book.setAttribute("src", require("../../pic/backBook3.jpg"));
    }
  };

  render() {
    const { activities, stats, statsReady, statsLoading, userId } = this.state;
    return (
      <div className="content-wrapper">
        <section className="content">
          <div className="row">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <Divider
                    orientation="left"
                    style={{ color: "#333", fontWeight: "normal" }}
                  >
                    <h3 className="box-title">
                      {this.state.doubanId === null
                        ? "请先点击“绑定账户”绑定豆瓣用户！"
                        : "用户" + this.state.doubanId + "的娱乐推荐"}
                    </h3>
                  </Divider>
                  <Popconfirm
                    placement="bottomLeft"
                    title={text}
                    onConfirm={this.confirm}
                    okText="开始推荐"
                    cancelText="再想想"
                  >
                    <p className="btn btn-danger">开始推荐</p>
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
                    <p>
                      <input id="changeId" />
                      <button onClick={this.changeId}>确认</button>
                    </p>
                  ) : null}
                </div>
                {this.state.finish ? (
                  <div className="box-body">
                    <div className="row">
                      <div className="col-xs-6">
                        <img
                            referrerpolicy="no-referrer"
                          id="img"
                          src={this.state.rcmd.picture_movie.trim()}
                          alt={
                            "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2074715729.webp"
                          }
                          style={{ width: "100%" }}
                          onError={this.changeSrcMovie}
                        />
                        <Card style={{ width: "95%" }}>
                          <p style={{ fontSize: "25px", color: "red" }}>
                            推荐书籍：
                          </p>
                          <h1
                            style={{
                              marginTop: "40px",
                              fontWeight: "bold",
                              color: "blue",
                            }}
                          >
                            {this.state.rcmd.title_book}
                          </h1>
                          <p style={{ marginTop: "60px", fontSize: "20px" }}>
                            <span style={{ fontWeight: "bold" }}>
                              书籍评分：
                            </span>
                            {this.state.rcmd.rate_book}
                          </p>
                          <p style={{ marginTop: "60px", fontSize: "20px" }}>
                            <span style={{ fontWeight: "bold" }}>
                              书籍热度：
                            </span>
                            {this.state.rcmd.hot_book}
                          </p>
                          <p style={{ marginTop: "60px", fontSize: "20px" }}>
                            <span style={{ fontWeight: "bold" }}>
                              书籍价格：
                            </span>
                            {this.state.rcmd.price_book}
                          </p>
                          <p style={{ marginTop: "60px", fontSize: "20px" }}>
                            <span style={{ fontWeight: "bold" }}>
                              书籍简介：
                            </span>
                            {this.state.rcmd.introduction_book}
                          </p>
                        </Card>
                      </div>
                      <div className="col-xs-6">
                        <Card style={{ width: "95%" }}>
                          <p style={{ fontSize: "25px", color: "red" }}>
                            推荐电影：
                          </p>
                          <h1
                            style={{
                              marginTop: "40px",
                              fontWeight: "bold",
                              color: "blue",
                            }}
                          >
                            {this.state.rcmd.title_movie}
                          </h1>
                          <p style={{ marginTop: "60px", fontSize: "20px" }}>
                            <span style={{ fontWeight: "bold" }}>
                              电影类型：
                            </span>
                            {this.state.rcmd.type_movie}
                          </p>
                          <p style={{ marginTop: "60px", fontSize: "20px" }}>
                            <span style={{ fontWeight: "bold" }}>
                              豆瓣评分：
                            </span>
                            {this.state.rcmd.rate_movie}
                          </p>
                          <p style={{ marginTop: "60px", fontSize: "20px" }}>
                            <span style={{ fontWeight: "bold" }}>
                              演员列表：
                            </span>
                            {this.state.rcmd.actors_list_movie}
                          </p>
                          <p style={{ marginTop: "60px", fontSize: "20px" }}>
                            <span style={{ fontWeight: "bold" }}>
                              电影简介：
                            </span>
                            {this.state.rcmd.introduction_movie}
                          </p>
                        </Card>
                        <img
                          id="img2"
                          src={this.state.rcmd.picture_book.trim()}
                          alt={"正在加载中..."}
                          style={{ width: "100%", marginTop: "40px" }}
                          onError={this.changeSrcBook}
                        />
                      </div>
                    </div>
                  </div>
                ) : null}
              </div>
            </div>
          </div>
        </section>
      </div>
    );
  }
}
