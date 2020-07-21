import React from 'react';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
import Menu from '../sections/Menu';

describe('Menu component test with Enzyme', () => {
  it('renders without crashing', () => {
    shallow(<Menu />);
  });
});