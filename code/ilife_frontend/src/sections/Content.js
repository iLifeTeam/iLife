import React, { Component } from 'react';
import {
  Switch,
  Route,
} from "react-router-dom";
import zhihuBodyContent from '../contents/zhihuBodyContent'
import Oauth from '../components/weibo/Oauth'
import DataTable from '../components/DataTable'

export default class Content extends Component {
  render() {
    return (
      <Switch>
        <Route path="/" component={zhihuBodyContent} />
        <Route path="/zhihu" component={zhihuBodyContent} />
        <Route path="/weibo" component={Oauth} />
      </Switch>
    )
  }
}
