const { pathsToModuleNameMapper } = require('ts-jest');

const { compilerOptions } = require('./tsconfig');

module.exports = {
  moduleNameMapper: pathsToModuleNameMapper(compilerOptions.paths, { prefix: '<rootDir>/' }),
  setupFiles: ['./jest.polyfills.js'],
  testEnvironment: 'jsdom',
  setupFilesAfterEnv: ['./jest.setup.js'],
  testEnvironmentOptions: {
    customExportConditions: [''],
  },
  preset: 'ts-jest',
  transform: {
    '^.+\\.(ts|tsx)$': 'ts-jest',
    '^.+\\.js$': 'babel-jest',
    '^.+\\.svg$': '<rootDir>/svgTransform.js',
  },
  transformIgnorePatterns: ['<rootDir>/node_modules/'],
};
