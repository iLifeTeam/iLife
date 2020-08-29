import React, { Component } from 'react'
import { configure, shallow, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });
import BilibiliHistorty from '../bilibili/BilibiliHistorty'

var histories = [{
  hisid: 123,
  video: {
    title: "title",
    auther_name: "author",
    tag_name: "tag",
  }
}];

describe('WyyHistory test', () => {
  const $ = require('jquery')
  $.fn.DataTable = require('datatables.net')

  const wrapper = mount(<BilibiliHistorty histories={histories} />);

  it('renders with row data', () => {


    expect(wrapper.props().histories).toEqual(histories);
    wrapper.setProps({ histories: histories });
    const spy = jest.spyOn(BilibiliHistorty.prototype, "componentDidUpdate")

    histories.push({
      hisid: 456,
      video: {
        title: "title",
        auther_name: "author",
        tag_name: "tag",
      }
    })
    wrapper.setProps({ histories: histories });
    expect(spy).toHaveBeenCalledTimes(1);
    //console.log(wrapper.html());
    expect(wrapper.props().histories[1].hisid).toEqual(456);


    histories[0].hisid = 2;
    wrapper.setProps({ histories: histories });
    expect(wrapper.props().histories[0].hisid).toEqual(2);

  })

  it('renders with non data', () => {
    wrapper.setProps({ histories: null });
    expect(wrapper.props().histories).toEqual(null);
  })

  it("test unmount", () => {
    const wrapper2 = mount(<BilibiliHistorty histories={null} />);
  })

});