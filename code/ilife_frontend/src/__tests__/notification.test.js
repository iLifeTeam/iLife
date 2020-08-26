import React from 'react';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
import Notification from '../sections/components/Notification'

describe('Notification test with Enzyme', () => {
  it('', () => {
    shallow(<Notification />);
  });

});