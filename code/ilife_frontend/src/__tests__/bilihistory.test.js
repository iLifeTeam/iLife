import React from 'react';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
const $ = require('jquery');

import BilibiliHistorty from '../bilibili/BilibiliHistorty'

var histories = [{
  hisid: 1,
  video: { title: "testtitle", auther_name: "testname", tag_name: "testtag" }
}];

describe('BilibiliHistorty test with Enzyme', () => {
  $.DataTable = require('datatables.net')
  const wrapper = mount(<BilibiliHistorty histories={histories} />);
  it('renders without crashing', () => {
    expect(wrapper.props().histories).toEqual(histories);
    histories[0].hisid = 2;
    wrapper.setProps({ histories })
    expect(wrapper.props().histories[0].hisid).toEqual(2);
    histories = null;
    wrapper.setProps({ histories })
  })
});