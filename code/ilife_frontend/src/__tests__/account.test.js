import React from 'react';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });
import axios from 'axios'
jest.mock('axios');

import { shallow } from 'enzyme';
import Account from '../sections/components/Account'


describe('Account test with Enzyme', () => {
  const wrapper = shallow(<Account />);
  it('renders without crashing', () => {

    //axios.post = jest.fn();
    const resp = { data: [{ name: 'Bob' }] };
    axios.mockImplementation(() => Promise.resolve(resp));
    wrapper.instance().setState({ username: "test" });
    expect(wrapper.state().username).toEqual("test");
    wrapper.instance().logoff();
  })
});