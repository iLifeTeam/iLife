import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import zhihuBodyContent from './contents/zhihuBodyContent'
import Oauth from '../weibo/Oauth'
import DataTable from './components/DataTable'

export default class Content extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path="/home" component={DataTable} />
          <Route path="/zhihu" component={zhihuBodyContent} />
          <Route path="/weibo" component={Oauth} />

        </Switch>
      </Router>
    )
  }
}
