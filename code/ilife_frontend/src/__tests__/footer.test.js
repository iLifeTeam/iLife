import React from 'react';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
import Footer from '../sections/Footer';

describe('Footer test with Enzyme', () => {
  it('renders without crashing (logoff state)', () => {
    shallow(<Footer />);
  });

});