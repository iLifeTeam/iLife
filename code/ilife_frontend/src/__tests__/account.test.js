import React from 'react';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });
import axios from 'axios'

import { shallow } from 'enzyme';
import Account from '../sections/components/Account'

describe('Account test with Enzyme', () => {
  const wrapper = shallow(<Account />);
  it('renders without crashing', () => {

    jest.mock('axios');
    axios.post = jest.fn();
    const resp = "";
    (axios.post).mockResolvedValue(resp);
    wrapper.instance().setState({ username: "test" });
    expect(wrapper.state().username).toEqual("test");
    wrapper.instance().logoff();
    //console.log(wrapper.state().username);
  })
});