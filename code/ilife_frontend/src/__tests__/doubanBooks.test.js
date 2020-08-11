import React from 'react';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
const $ = require('jquery');

import DoubanBooks from '../douban/DoubanBooks'

var activities = [{
  name: "testname",
  author: "testauthor",
  price: "testprice",
  ranking: "testranking",
  hot: "testhot"
}];

describe('DoubanBooks test with Enzyme', () => {
  $.DataTable = require('datatables.net')
  const wrapper = mount(<DoubanBooks activities={activities} />);
  it('renders without crashing', () => {
    expect(wrapper.props().activities).toEqual(activities);
    activities[0].name = "2";
    wrapper.setProps({ activities })
    expect(wrapper.props().activities[0].name).toEqual("2");
    activities = null;
    wrapper.setProps({ activities })
  })
});