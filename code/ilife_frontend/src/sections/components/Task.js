import React, { Component } from 'react'

export default class Task extends Component {
  constructor(props) {
    super(props);
    this.state = {
      taskTotal: 9,
      tasks: [],
    }
  }
  render() {
    return (
      /* Tasks: style can be found in dropdown.less */
      <li className="dropdown tasks-menu">
        <a href="fake_url" className="dropdown-toggle" data-toggle="dropdown">
          <i className="fa fa-flag-o" />
          <span className="label label-danger">{this.state.taskTotal}</span>
        </a>
        <ul className="dropdown-menu">
          <li className="header">您有{this.state.taskTotal}个任务</li>
          <li>
            {/* inner menu: contains the actual data */}
            <ul className="menu">
              <li>{/* Task item */}
                <a href="fake_url">
                  <h3>
                    Design some buttons
            <small className="pull-right">20%</small>
                  </h3>
                  <div className="progress xs">
                    <div className="progress-bar progress-bar-aqua" style={{ width: '20%' }} role="progressbar" aria-valuenow={20} aria-valuemin={0} aria-valuemax={100}>
                      <span className="sr-only">20% Complete</span>
                    </div>
                  </div>
                </a>
              </li>
              {/* end task item */}
              <li>{/* Task item */}
                <a href="fake_url">
                  <h3>
                    Create a nice theme
            <small className="pull-right">40%</small>
                  </h3>
                  <div className="progress xs">
                    <div className="progress-bar progress-bar-green" style={{ width: '40%' }} role="progressbar" aria-valuenow={20} aria-valuemin={0} aria-valuemax={100}>
                      <span className="sr-only">40% Complete</span>
                    </div>
                  </div>
                </a>
              </li>
              {/* end task item */}
              <li>{/* Task item */}
                <a href="fake_url">
                  <h3>
                    Some task I need to do
            <small className="pull-right">60%</small>
                  </h3>
                  <div className="progress xs">
                    <div className="progress-bar progress-bar-red" style={{ width: '60%' }} role="progressbar" aria-valuenow={20} aria-valuemin={0} aria-valuemax={100}>
                      <span className="sr-only">60% Complete</span>
                    </div>
                  </div>
                </a>
              </li>
              {/* end task item */}
              <li>{/* Task item */}
                <a href="fake_url">
                  <h3>
                    Make beautiful transitions
            <small className="pull-right">80%</small>
                  </h3>
                  <div className="progress xs">
                    <div className="progress-bar progress-bar-yellow" style={{ width: '80%' }} role="progressbar" aria-valuenow={20} aria-valuemin={0} aria-valuemax={100}>
                      <span className="sr-only">80% Complete</span>
                    </div>
                  </div>
                </a>
              </li>
              {/* end task item */}
            </ul>
          </li>
          <li className="footer">
            <a href="fake_url">View all tasks</a>
          </li>
        </ul>
      </li>

    )
  }
}
