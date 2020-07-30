import React from 'react';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });


import { shallow } from 'enzyme';
import App from '../App';
import register from '../serviceWorker'

describe('First React component test with Enzyme', () => {
  it('renders without crashing', () => {
    shallow(<App />);
  });
});
