import React from 'react';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
import Task from '../sections/components/Task'

describe('Task test with Enzyme', () => {
  it('', () => {
    shallow(<Task />);
  });

});