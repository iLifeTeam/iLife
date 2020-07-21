import React from 'react';
import Menu from '../sections/Menu';
import ZhihuBodyContent from '../sections/contents/zhihuBodyContent'
import renderer from 'react-test-renderer';

test('Menu changes the class when hovered', () => {
  const component = renderer.create(
    <ZhihuBodyContent />
  );
  let tree = component.toJSON();
  expect(tree).toMatchSnapshot();

});