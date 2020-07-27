import React from 'react';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow, mount } from 'enzyme';
import RegisterPage from '../pages/RegisterPage';
import MockAdapter from "axios-mock-adapter";
import axios from 'axios'
const mock = new MockAdapter(axios);
mock.onPost("http://18.162.168.229:8686/login").reply((config) => {
  return new Promise(function (resolve, reject) {
    resolve([200, { data: "iLife login success" }]);
  }).then(() => {

  })
})
describe('RegisterPage test with Enzyme', () => {
  it('renders without crashing (logoff state)', () => {
    shallow(<RegisterPage />);
  });

  it('renders without crashing (login state)', () => {
    const body = shallow(<RegisterPage />);
    body.setState({ username: "test" })
    expect(body.state().username).toEqual("test");
  })

  it('test input model', () => {
    const body = shallow(<RegisterPage />);
    expect(body.find("#nameinput").exists()).toEqual(true);
    expect(body.find("#psdinput").exists()).toEqual(true);
    expect(body.find("#psd2input").exists()).toEqual(true);
    expect(body.find("#emailinput").exists()).toEqual(true);
    expect(body.find("#accountinput").exists()).toEqual(true);

    const inputlabel1 = body.find("#nameinput");
    inputlabel1.simulate('change', { target: { value: 'testinput1' } });

    const inputlabel2 = body.find("#psdinput");
    inputlabel2.simulate('change', { target: { value: 'testinput2' } });

    const inputlabel3 = body.find("#emailinput");
    inputlabel3.simulate('change', { target: { value: 'testinput3' } });

    const inputlabel4 = body.find("#accountinput");
    inputlabel4.simulate('change', { target: { value: 'testinput4' } });


    expect(body.state().nickname).toEqual("testinput1");
    expect(body.state().password).toEqual("testinput2");
    expect(body.state().email).toEqual("testinput3");
    expect(body.state().account).toEqual("testinput4");
  })
});

describe('login test', () => {


  it('login success', async () => {


    const body = shallow(<RegisterPage />);
    body.setState({ username: "test" })
    const btn = body.find("#register");
    expect(btn.exists()).toEqual(true);
    btn.simulate("click");
  });

});