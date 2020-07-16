import React, { Component } from 'react'
import Header from './sections/Header'
import Footer from './sections/Footer'
import Menu from './sections/Menu'
import Content from './sections/Content'
import ControlSidebar from './sections/ControlSidebar'

import {
  BrowserRouter as Router
} from "react-router-dom";


export default class App extends Component {
  render() {
    return (
      <div>
        <Router>
          <Header />
          <Menu />
          <Content />
          <Footer />
          <ControlSidebar />
        </Router>
      </div>
    )
  }
}
