
import React from 'react';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });


import { shallow } from 'enzyme';
import HomePage from '../pages/HomePage';

describe('First React component test with Enzyme', () => {
  it('renders without crashing', () => {
    shallow(<HomePage />);
  });
});