import React, { Component } from 'react'
import { configure, shallow, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });
import WeiboInfo from '../weibo/WeiboInfo'

var activities = [{
  id: "id",
  publish_time: "2020-08-01T20:00:00.000+00:00",
  content: "content",
  retweet_num: "retweet_num",
  comment_count: "comment_count",
  up_num: "up_num"
}];

describe('DoubanBooks test', () => {
  const $ = require('jquery')
  $.fn.DataTable = require('datatables.net')

  const wrapper = mount(<WeiboInfo activities={activities} />);

  it('renders with row data', () => {
    //wrapper.setProps({ activities })
    expect(wrapper.props().activities).toEqual(activities);
    wrapper.setProps({ activities: activities });
    const spy = jest.spyOn(WeiboInfo.prototype, "componentDidUpdate")
    //expect(wrapper.find("#weiboTable").children[0]).toEqual("2020-08-01 20:00");
    activities.push({
      id: "id",
      content: "content",
      retweet_num: "retweet_num",
      comment_count: "comment_count",
      up_num: "up_num"
    })
    wrapper.setProps({ activities: activities });
    expect(spy).toHaveBeenCalledTimes(1);

    //console.log(wrapper.html());
    expect(wrapper.props().activities[1].id).toEqual("id");
    activities[0].id = 2;
    wrapper.setProps({ activities: activities });
    expect(wrapper.props().activities[0].id).toEqual(2);

  })

  it('renders with non data', () => {
    wrapper.setProps({ activities: null });
    expect(wrapper.props().activities).toEqual(null);
  })

  it("test parsedata", () => {
    wrapper.unmount();
    wrapper.mount(<WeiboInfo activities={activities} />)
    var testData = [];
    expect(wrapper.instance().parseData(testData)).toEqual(testData);
    expect(wrapper.instance().parseData(activities)[0].publish_time).toEqual("2020-08-01 20:00");
  })

});