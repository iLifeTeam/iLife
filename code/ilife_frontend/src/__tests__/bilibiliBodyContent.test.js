import React from 'react';
import { configure, shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { mount } from 'enzyme';
import BilibiliBodyContent from '../sections/contents/BilibiliBodyContent'
import MockAdapter from "axios-mock-adapter";
import axios from 'axios'

describe('ZhihuBodyContent component test with Enzyme', () => {

  const data = {
    data: {
      oauthKey: "111",
      url: "111"
    }
  }

  const mock = new MockAdapter(axios);


  const body = shallow(<BilibiliBodyContent />);

  // 初始state确认
  it('test state change ', () => {
    body.setState({ username: "test" });
    expect(body.state().username).toEqual("test");
    expect(body.state().needQRCode).toEqual(false);
  });

  // input检查
  it('test input model', () => {
    mock.onGet("http://18.162.168.229:8848/bili/getloginurl").reply(
      (config) => {
        return new Promise(function (resolve, reject) {
          resolve([200, data]);
        }).then(() => {
          body.find("#QRcodeButton").simulate("click");
          expect(body.state().needQRCode).toEqual(true);
        })
      })
  })

  it('test getHistories', () => {
    const histories = [1, 2, 3];
    mock.onPost().reply(
      (config) => {
        return new Promise(function (resolve, reject) {
          resolve([200, { content: histories }]);
        }).then(() => {
        })
      })
    body.instance().getHistories();
  })

  it('test Update', () => {
    mock.onPost().reply(
      (config) => {
        return new Promise(function (resolve, reject) {
          resolve([200, {}]);
        }).then(() => {
        })
      })
    body.setState({ SESSDATA: "test" })
    expect(body.find('#Update').exists()).toEqual(true);
    body.find('#Update').simulate("click");
  })

  it('test others', () => {
    body.instance().QRcodeLogin = jest.fn(() => {
      return body.instance().getResponse();
    })
    body.instance().QRcodeLogin();
    body.instance().getUserId();
    body.instance().getResponse();
    body.setState({ islogin: true });
    //expect(body.find("请打开bilibli手机端扫描二维码").exists()).toEqual(false);
  })
});

