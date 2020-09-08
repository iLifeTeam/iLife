import React, { Component } from 'react'
import { configure, shallow, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });
import WyyHistory from '../wyy/WyyHistory'

var histories = [{
  hisid: 123,
  musics: {
    mname: "music_name",
    singers: [{ sname: "singer_name" }]
  }
}];

describe('WyyHistory test', () => {
  const $ = require('jquery')
  $.fn.DataTable = require('datatables.net')

  const wrapper = mount(<WyyHistory histories={histories} />);

  it('renders with row data', () => {


    expect(wrapper.props().histories).toEqual(histories);
    wrapper.setProps({ histories: histories });
    const spy = jest.spyOn(WyyHistory.prototype, "componentDidUpdate")

    histories.push({
      hisid: 456,
      musics: {
        mname: "music_name2",
        singers: [{ sname: "singer_name2" }]
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
    const wrapper2 = mount(<WyyHistory histories={null} />);
  })

});