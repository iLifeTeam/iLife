import React, { Component } from 'react'
import Header from '../sections/Header'
import Footer from '../sections/Footer'
import Menu from '../sections/Menu'
import Content from '../sections/Content'
import ControlSidebar from '../sections/ControlSidebar'

class HomePage extends Component {
  constructor(props) {
    super(props);
  }
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
export default HomePage