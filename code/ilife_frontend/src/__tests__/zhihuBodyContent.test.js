import React from 'react';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { mount } from 'enzyme';
import ZhihuBodyContent from '../sections/contents/zhihuBodyContent'
import MockAdapter from "axios-mock-adapter";
import axios from 'axios'

describe('ZhihuBodyContent component test with Enzyme', () => {

  const body = mount(<ZhihuBodyContent />);

  // 初始state确认
  it('test state change ', () => {
    body.setState({ username: "test" });
    expect(body.state().username).toEqual("test");
    expect(body.state().needCaptcha).toEqual(false);
  });

  // input检查
  it('test input model', () => {
    expect(body.find("#exampleInputEmail1").exists()).toEqual(true);
    expect(body.find("#exampleInputPassword1").exists()).toEqual(true);

    const inputlabel1 = body.find("#exampleInputEmail1");
    inputlabel1.simulate('change', { target: { value: 'testinput1' } });

    const inputlabel2 = body.find("#exampleInputPassword1");
    inputlabel2.simulate('change', { target: { value: 'testinput2' } });

    expect(body.state().username).toEqual("testinput1");
    expect(body.state().password).toEqual("testinput2");
  })


});


describe('ZhihuBodyContent component test with Enzyme', () => {

  const mock = new MockAdapter(axios);

  const body = mount(<ZhihuBodyContent />);

  // submit1 mock
  it('submit1 mock 检查', async () => {
    const users = "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";
    const data = { users };
    mock.onPost("http://18.162.168.229:8090/login").reply(
      (config) => {
        return new Promise(function (resolve, reject) {
          resolve([200, data]);
        }).then(() => {
          const btn = body.find("#submit1");
          expect(btn.exists()).toEqual(true);
          btn.simulate("click");
        }).then(() => {
          expect(body.state().needCaptcha).toEqual(true);
        })
      });
  }
  );

  it('submit1 mock 检查', async () => {
    const users = "Login successfully!";
    const data = { users };
    mock.onPost("http://18.162.168.229:8090/login").reply(
      (config) => {
        return new Promise(function (resolve, reject) {
          resolve([200, data]);
        }).then(() => {
          const btn = body.find("#submit1");
          expect(btn.exists()).toEqual(true);
          btn.simulate("click");
          expect(body.state().needCaptcha).toEqual(true);
          console.log(body.state().needCaptcha)
        });
      }
    );
  })

  // submit2 mock
  it('submit2 mock: login with captcha', async () => {
    const activities = [{ type: "test" }];
    const data = { activities };
    mock.onPost("http://18.162.168.229:8090/login").reply(
      (config) => {
        return new Promise(function (resolve, reject) {
          resolve([200, data]);
        }).then(() => {
          body.setState({ needCaptcha: true });
          const btn = body.find("#submit2");
          expect(btn.exists()).toEqual(true);
          btn.simulate("click");
          expect(body.state().activities).toEqual(activities);
        });
      }
    );
  })

  // get activities mock
  it('mock: get activities', async () => {
    const activities = [{ type: "test" }];
    const data = { activities };
    mock.onPost("http://18.162.168.229:8090/login").reply(
      (config) => {
        return new Promise(function (resolve, reject) {
          resolve([200, { data: "success" }]);
        }).then(() => {
          body.setState({ username: "hyy", needCaptcha: true });

        });
      }
    );
    mock.onGet("http://18.162.168.229:8090/activity/all?username=hyy").reply(
      (config) => {
        return new Promise(function (resolve, reject) {
          resolve([200, data]);
        }).then(() => {
          const btn = body.find("#submit2");
          expect(btn.exists()).toEqual(true);
          btn.simulate("click");
          expect(body.state().activities).toEqual(activities);
        });
      }
    );
  })


})