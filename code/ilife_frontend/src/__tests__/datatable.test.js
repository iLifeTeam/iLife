import React from 'react';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });

import { shallow } from 'enzyme';
import DataTable from '../sections/components/DataTable'

describe('DataTable test with Enzyme', () => {
  it('', () => {
    shallow(<DataTable />);
  });

});