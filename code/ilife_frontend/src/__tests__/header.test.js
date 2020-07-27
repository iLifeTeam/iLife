import React from 'react';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
import Header from '../sections/Header';

describe('Header test with Enzyme', () => {
  it('renders without crashing (logoff state)', () => {
    shallow(<Header />);
  });

  it('renders without crashing (login state)', () => {
    const body = shallow(<Header />);
    body.setState({ username: "test" })
    expect(body.state().username).toEqual("test");
  })

});