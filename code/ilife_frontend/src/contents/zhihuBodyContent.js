import React, { Component } from 'react'
import axios from 'axios';
export default class zhihuBodyContent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      code: "",
      picBase64: "",
    }
    this.login = this.login.bind(this);
    this.loginTwice = this.loginTwice.bind(this);
  }
  componentDidMount() {
    const script = document.createElement("script");

    script.src = "dist/js/content.js";
    script.async = true;
    document.body.appendChild(script);

  }

  nameOnChange(val) {
    this.setState({
      username: val.target.value,
    })
  }
  codeOnChange(val) {
    this.setState({
      code: val.target.value,
    })
  }
  psdOnChange(val) {
    this.setState({
      password: val.target.value,
    })
  }

  async login() {
    var pic;
    await axios.post("http://47.97.206.169:8090/login?username=" + this.state.username + "&password=" + this.state.password)
      .then(function (response) {
        console.log(response);
        pic = response.data;
      })

    this.setState({
      picBase64: `data:image/png;base64,${pic}`
    });

  }

  async loginTwice() {
    await axios.post("http://47.97.206.169:8090/login?username=" + this.state.username + "&password=" + this.state.password + "&captcha=" + this.state.code)
      .then(function (response) {
        console.log(response);
      })
    console.log("done");
    await axios.get("http://47.97.206.169:8090/activity/all?username=" + this.state.username)
      .then(function (response) {
        console.log(response);
      })
  }



  render() {

    return (
      <div className="content-wrapper">
        <section className="content">
          <div className="row">
            <div className="col-md-9">
              <div className="box box-primary">
                <div className="box-header with-border">
                  <h3 className="box-title">登录</h3>
                </div>
                {/* form start */}
                <form role="form">
                  <div className="box-body">
                    <div className="form-group">
                      <label htmlFor="exampleInputEmail1">Email地址</label>
                      <input type="email" className="form-control" id="exampleInputEmail1" placeholder="Enter email"
                        onChange={(val) => this.nameOnChange(val)} />
                    </div>
                    <div className="form-group">
                      <label htmlFor="exampleInputPassword1">密码</label>
                      <input type="password" className="form-control" id="exampleInputPassword1" placeholder="Password"
                        onChange={(val) => this.psdOnChange(val)} />
                    </div>
                  </div>
                </form>
                {/* /.box-body */}
                <div className="box-footer">
                  <button className="btn btn-primary" onClick={this.login}>Submit</button>
                </div>
              </div>
            </div>
            <div className="col-md-3">
              <div className="box box-primary">
                <div className="box-header with-border">
                  <h3 className="box-title">验证码</h3>
                </div>
                <img src={this.state.picBase64} className="img-square" alt="验证码" />
                <form role="form">
                  <div className="box-body">
                    <div className="form-group">
                      <label htmlFor="exampleInputCode1">验证码</label>
                      <input className="form-control" id="exampleInputCode1"
                        onChange={(val) => this.codeOnChange(val)} />
                    </div>
                  </div>
                </form>
                <div className="box-footer">
                  <button className="btn btn-primary" onClick={this.loginTwice}>Submit</button>
                </div>
              </div>
            </div>
          </div>
          <div className="row">
            <div className="col-xs-12">
              <div className="box">
                <div className="box-header">
                  <h3 className="box-title">Data Table With Full Features</h3>
                </div>
                <div className="box-body">
                  <table id="example1" className="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>Rendering engine</th>
                        <th>Browser</th>
                        <th>Platform(s)</th>
                        <th>Engine version</th>
                        <th>CSS grade</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>Trident</td>
                        <td>Internet
                        Explorer 4.0
                    </td>
                        <td>Win 95+</td>
                        <td> 4</td>
                        <td>X</td>
                      </tr>
                      <tr>
                        <td>Trident</td>
                        <td>Internet
                        Explorer 5.0
                    </td>
                        <td>Win 95+</td>
                        <td>5</td>
                        <td>C</td>
                      </tr>
                      <tr>
                        <td>Trident</td>
                        <td>Internet
                        Explorer 5.5
                    </td>
                        <td>Win 95+</td>
                        <td>5.5</td>
                        <td>A</td>
                      </tr><tr>
                        <td>Tasman</td>
                        <td>Internet Explorer 5.1</td>
                        <td>Mac OS 7.6-9</td>
                        <td>1</td>
                        <td>C</td>
                      </tr>
                      <tr>
                        <td>Tasman</td>
                        <td>Internet Explorer 5.2</td>
                        <td>Mac OS 8-X</td>
                        <td>1</td>
                        <td>C</td>
                      </tr>
                      <tr>
                        <td>Misc</td>
                        <td>NetFront 3.1</td>
                        <td>Embedded devices</td>
                        <td>-</td>
                        <td>C</td>
                      </tr>
                      <tr>
                        <td>Misc</td>
                        <td>NetFront 3.4</td>
                        <td>Embedded devices</td>
                        <td>-</td>
                        <td>A</td>
                      </tr>
                      <tr>
                        <td>Misc</td>
                        <td>Dillo 0.8</td>
                        <td>Embedded devices</td>
                        <td>-</td>
                        <td>X</td>
                      </tr>
                      <tr>
                        <td>Misc</td>
                        <td>Links</td>
                        <td>Text only</td>
                        <td>-</td>
                        <td>X</td>
                      </tr>
                      <tr>
                        <td>Misc</td>
                        <td>Lynx</td>
                        <td>Text only</td>
                        <td>-</td>
                        <td>X</td>
                      </tr>
                      <tr>
                        <td>Misc</td>
                        <td>IE Mobile</td>
                        <td>Windows Mobile 6</td>
                        <td>-</td>
                        <td>C</td>
                      </tr>
                      <tr>
                        <td>Misc</td>
                        <td>PSP browser</td>
                        <td>PSP</td>
                        <td>-</td>
                        <td>C</td>
                      </tr>
                      <tr>
                        <td>Other browsers</td>
                        <td>All others</td>
                        <td>-</td>
                        <td>-</td>
                        <td>U</td>
                      </tr>
                      <tr>
                        <td>Misc</td>
                        <td>PSP browser</td>
                        <td>PSP</td>
                        <td>-</td>
                        <td>C</td>
                      </tr>
                      <tr>
                        <td>Misc</td>
                        <td>PSP browser</td>
                        <td>PSP</td>
                        <td>-</td>
                        <td>C</td>
                      </tr>
                      <tr>
                        <td>Misc</td>
                        <td>PSP browser</td>
                        <td>PSP</td>
                        <td>-</td>
                        <td>C</td>
                      </tr>
                      <tr>
                        <td>Misc</td>
                        <td>PSP browser</td>
                        <td>PSP</td>
                        <td>-</td>
                        <td>C</td>
                      </tr>
                      <tr>
                        <td>Misc</td>
                        <td>PSP browser</td>
                        <td>PSP</td>
                        <td>-</td>
                        <td>C</td>
                      </tr>
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>Rendering engine</th>
                        <th>Browser</th>
                        <th>Platform(s)</th>
                        <th>Engine version</th>
                        <th>CSS grade</th>
                      </tr>
                    </tfoot>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    )
  }
}
