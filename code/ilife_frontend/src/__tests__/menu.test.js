import React from 'react';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
import Menu from '../sections/Menu';

describe('Menu component test with Enzyme', () => {
  it('renders without crashing (logoff state)', () => {
    shallow(<Menu />);
  });

  it('renders without crashing (login state)', () => {
    const menu = shallow(<Menu />);
    menu.setState({ username: "test" })
    expect(menu.state().username).toEqual("test");

  })

});