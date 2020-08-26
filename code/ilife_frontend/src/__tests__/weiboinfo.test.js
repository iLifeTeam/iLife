import React from 'react';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import WeiboInfo from '../weibo/WeiboInfo'

var activities = [{
  id: "id",
  publish_time: "\"2020-08-01T20:00\"",
  content: "content",
  retweet_num: "retweet_num",
  comment_count: "comment_count",
  up_num: "up_num"
}];

describe('DoubanBooks test', () => {


  const wrapper = mount(<WeiboInfo activities={activities} />);
  it('renders with row data', () => {
    expect(wrapper.props().activities).toEqual(activities);
    //expect(wrapper.find("#weiboTable").DataTable().rows[0].publish_time).toEqual("2020-08-01 20:00");
    activities.id = 2;
    wrapper.setProps({ activities })
    expect(wrapper.props().activities[0].id).toEqual("2");
  })
});