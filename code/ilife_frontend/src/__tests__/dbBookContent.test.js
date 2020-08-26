import React from 'react';
import { configure, shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { mount } from 'enzyme';
import DbBookContent from '../sections/contents/DbBookContent'
import MockAdapter from "axios-mock-adapter";
import axios from 'axios'

describe('DbBookContent component test with Enzyme', () => {

  const data = {
    data: {
      oauthKey: "111",
      url: "111"
    }
  }

  const mock = new MockAdapter(axios);


  const body = shallow(<DbBookContent />);

  // 初始state确认
  it('test state change ', () => {
    body.setState({ username: "test" });
    expect(body.state().username).toEqual("test");
    expect(body.state().doubanId).toEqual(null);
  });


  it('test others', () => {
    body.instance().crawl();
  })
});

