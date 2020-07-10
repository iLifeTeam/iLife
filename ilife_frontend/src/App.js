import React, { Component } from 'react'
import Header from './components/Header'
import Footer from './components/Footer'
import Menu from './components/Menu'
import Content from './components/Content'
import ControlSidebar from './components/ControlSidebar'

export default class App extends Component {
  render() {
    return (
      <div>
        <Header />
        <Menu />
        <Content />
        <Footer />
        <ControlSidebar />
      </div>
    )
  }
}
