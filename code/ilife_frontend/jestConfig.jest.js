module.exports = {
  roots: ['<rootDir>/__tests__/'], // 测试的目录
  modulePaths: ['<rootDir>'],
  coveragePathIgnorePatterns: ['/node_modules/'], // 忽略统计覆盖率的文件
  // transform: {
  //   '^.+\\.js$': 'babel-jest',
  //   '^.+\\.(ts|tsx)$': 'ts-jest',
  // },
  transformIgnorePatterns: [
    '<rootDir>/node_modules/',
  ], //
  testMatch: [ // 匹配的测试文件
    '<rootDir>/__tests__/*.test.{js,jsx,mjs}',
    '<rootDir>/src/**/__tests__/**/*.{js,jsx,mjs}',
  ],

  setupFiles: [
    "<rootDir>/__tests__/setup.js"
  ],
  moduleFileExtensions: ['js', 'jsx', 'json'], //支持文件名
};