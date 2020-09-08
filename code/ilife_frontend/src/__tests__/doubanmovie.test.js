import React from 'react'
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });
import DoubanMovies from '../douban/DoubanMovies'

var activities = [{
  name: "搜索",
  type: "剧情",
  language: "中文",
  ranking: 18,
  hot: 59283,
}];

describe('WyyHistory test', () => {
  const $ = require('jquery')
  $.fn.DataTable = require('datatables.net')

  const wrapper = mount(<DoubanMovies activities={activities} />);

  it('renders with row data', () => {


    expect(wrapper.props().activities).toEqual(activities);
    wrapper.setProps({ activities: activities });
    const spy = jest.spyOn(DoubanMovies.prototype, "componentDidUpdate")

    activities.push({
      name: "星际穿越",
      type: "科幻",
      language: "英文",
      ranking: 1,
      hot: 62390,
    })
    wrapper.setProps({ activities: activities });
    expect(spy).toHaveBeenCalledTimes(1);
    //console.log(wrapper.html());
    expect(wrapper.props().activities[1].ranking).toEqual(1);


    activities[0].ranking = 2;
    wrapper.setProps({ activities: activities });
    expect(wrapper.props().activities[0].ranking).toEqual(2);

  })

  it('renders with non data', () => {
    wrapper.setProps({ activities: null });
    expect(wrapper.props().activities).toEqual(null);
  })

  it("test unmount", () => {
    const wrapper2 = mount(<DoubanMovies activities={null} />);
  })

});