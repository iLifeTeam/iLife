import React from 'react'
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });
import DoubanBooks from '../douban/DoubanBooks'

var activities = [{
  name: "name",
  author: "author",
  price: 59,
  ranking: 1,
  hot: 62390,
}];

describe('WyyHistory test', () => {
  const $ = require('jquery')
  $.fn.DataTable = require('datatables.net')

  const wrapper = mount(<DoubanBooks activities={activities} />);

  it('renders with row data', () => {


    expect(wrapper.props().activities).toEqual(activities);
    wrapper.setProps({ activities: activities });
    const spy = jest.spyOn(DoubanBooks.prototype, "componentDidUpdate")

    activities.push({
      name: "name",
      author: "author",
      price: 60,
      ranking: 1,
      hot: 62390,
    })
    wrapper.setProps({ activities: activities });
    expect(spy).toHaveBeenCalledTimes(1);
    //console.log(wrapper.html());
    expect(wrapper.props().activities[1].price).toEqual(60);


    activities[0].price = 2;
    wrapper.setProps({ activities: activities });
    expect(wrapper.props().activities[0].price).toEqual(2);

  })

  it('renders with non data', () => {
    wrapper.setProps({ activities: null });
    expect(wrapper.props().activities).toEqual(null);
  })

  it("test unmount", () => {
    const wrapper2 = mount(<DoubanBooks activities={null} />);
  })

});