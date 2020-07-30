import React, { Component } from 'react';
import {
  Route,
} from "react-router-dom";
import zhihuBodyContent from './contents/zhihuBodyContent'
import DataTable from './components/DataTable'
import WeiboBodyContent from './contents/WeiboBodyContent';
import BilibiliBodyContent from './contents/BilibiliBodyContent';
import WyyBodyContent from './contents/WyyBodyContent';
import DbMovieContent from './contents/DbMovieContent';
import DbBookContent from './contents/DbBookContent';
import JingdongBodyContent from "./contents/JingdongBodyContent";
import TaobaoBodyContent from "./contents/TaobaoBodyContent";

export default class Content extends Component {
  render() {
    return (
      <div>
        <Route path="/home" exact component={DataTable} />
        <Route path="/home/zhihu" component={zhihuBodyContent} />
        <Route path="/home/weibo" component={WeiboBodyContent} />
        <Route path="/home/bilibili" component={BilibiliBodyContent} />
        <Route path="/home/wyy" component={WyyBodyContent} />
        <Route path="/home/movie" component={DbMovieContent} />
        <Route path="/home/book" component={DbBookContent} />
        <Route path="/home/jingdong" component={JingdongBodyContent} />
        <Route path="/home/taobao" component={TaobaoBodyContent} />
      </div>
    )
  }
}
