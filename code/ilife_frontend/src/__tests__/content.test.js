import React from 'react';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
import Content from '../sections/Content'

describe('Content test with Enzyme', () => {
  it('', () => {
    shallow(<Content />);
  });

});