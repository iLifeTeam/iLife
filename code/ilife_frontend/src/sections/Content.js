import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import zhihuBodyContent from './contents/zhihuBodyContent'
import Oauth from '../weibo/Oauth'
import DataTable from './components/DataTable'
import WeiboBodyContent from './contents/WeiboBodyContent';

export default class Content extends Component {
  render() {
    return (
      <div>
        <Route path="/home" exact component={DataTable} />
        <Route path="/home/zhihu" component={zhihuBodyContent} />
        <Route path="/home/weibo" component={WeiboBodyContent} />
      </div>
    )
  }
}
