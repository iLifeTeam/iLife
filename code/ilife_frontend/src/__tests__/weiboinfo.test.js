import React from 'react';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
const $ = require('jquery');

import WeiboInfo from '../weibo/WeiboInfo'

var activities = [{
  id: "id",
  publish_time: "publish_time",
  content: "content",
  retweet_num: "retweet_num",
  comment_count: "comment_count",
  up_num: "up_num"
}];

describe('DoubanBooks test with Enzyme', () => {
  $.DataTable = require('datatables.net')
  const wrapper = mount(<WeiboInfo activities={activities} />);
  it('renders without crashing', () => {
    expect(wrapper.props().activities).toEqual(activities);
    activities[0].id = "2";
    wrapper.setProps({ activities })
    expect(wrapper.props().activities[0].id).toEqual("2");
    activities = null;
    wrapper.setProps({ activities })
  })
});