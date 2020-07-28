import React from 'react';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow, mount } from 'enzyme';
import LoginPage from '../pages/LoginPage';

describe('LoginPage test with Enzyme', () => {
  it('renders without crashing (logoff state)', () => {
    shallow(<LoginPage />);
  });

  it('renders without crashing (login state)', () => {
    const body = shallow(<LoginPage />);
    body.setState({ username: "test" })
    expect(body.state().username).toEqual("test");
  })

});

describe('login test', () => {

  const body = shallow(<LoginPage />);
  it('login success', async () => {

    const axiosMock = jest.fn(() => "success");
    body.instance().axiosfunc = axiosMock;

    const btn = body.find("#login");
    expect(btn.exists()).toEqual(true);
    btn.simulate("click");
    expect().toEqual("http://localhosr:3000/home")
  })
});