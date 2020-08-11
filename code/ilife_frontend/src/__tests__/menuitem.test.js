import React from 'react';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
import MenuItem from '../sections/components/MenuItem'

var itemURL = "/home/weibo";
var itemName = "微博";
var childItems = [{
  name: "浏览信息",
  url: "/info"
},
{
  name: "趋势分析",
  url: "/analyse"
}
]

describe('DataTable test with Enzyme', () => {
  it('Branch test', () => {
    const wrapper = shallow(<MenuItem itemURL={itemURL} itemName={itemName} childItems={childItems} />);
    childItems = undefined;
    wrapper.setState({ childItems: childItems })

  });

});