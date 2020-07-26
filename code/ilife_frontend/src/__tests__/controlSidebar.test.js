import React from 'react';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
import ControlSidebar from '../sections/ControlSidebar';

describe('ControlSidebar test with Enzyme', () => {
  it('renders without crashing (logoff state)', () => {
    shallow(<ControlSidebar />);
  });

});