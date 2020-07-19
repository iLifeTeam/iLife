import React, { Component } from 'react'
import Header from '../sections/Header'
import Footer from '../sections/Footer'
import Menu from '../sections/Menu'
import Content from '../sections/Content'
import ControlSidebar from '../sections/ControlSidebar'
import DataTable from '../sections/components/DataTable'

export default class HomePage extends Component {
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
