import React from 'react';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
const $ = require('jquery');

import DoubanMovies from '../douban/DoubanMovies'

var activities = [{
  name: "testname",
  type: "testtype",
  language: "testlanguage",
  ranking: "testranking",
  hot: "testhot"
}];

describe('DoubanMovies test with Enzyme', () => {
  $.DataTable = require('datatables.net')
  const wrapper = mount(<DoubanMovies activities={activities} />);
  it('renders without crashing', () => {
    expect(wrapper.props().activities).toEqual(activities);
    activities[0].name = "2";
    wrapper.setProps({ activities })
    expect(wrapper.props().activities[0].name).toEqual("2");
    activities = null;
    wrapper.setProps({ activities })
  })
});