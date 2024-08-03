const path = require('path');

module.exports = {
  root: true,
  env: { browser: true, es2020: true },
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:react-hooks/recommended',
    'plugin:import/recommended',
    'plugin:react/recommended',
    'prettier',
  ],
  ignorePatterns: ['dist', '.eslintrc.cjs', 'webpack.config.js', 'jest.config.js', 'tsconfig.json'],
  parser: '@typescript-eslint/parser',
  plugins: ['react-refresh'],
  rules: {
    'react/react-in-jsx-scope': 'off',
    'react/no-unknown-property': ['error', { ignore: ['css'] }],
    'import/order': [
      'error',
      {
        'newlines-between': 'always',
        groups: [['builtin', 'external'], 'internal', 'parent', 'sibling', 'index'],
        pathGroups: [
          {
            pattern: 'next',
            group: 'builtin',
          },
          {
            pattern: 'react',
            group: 'builtin',
          },
          {
            pattern: '@MyDesignSystem/**',
            group: 'internal',
          },
          {
            pattern: 'src/**',
            group: 'internal',
          },
        ],
        pathGroupsExcludedImportTypes: ['src/**', '@MyDesignSystem/**'],
        alphabetize: {
          order: 'asc',
          caseInsensitive: true,
        },
      },
    ],
  },
  settings: {
    'import/resolver': {
      alias: {
        map: [['@', path.resolve(__dirname, 'src')]],
        extensions: ['.js', '.jsx', '.ts', '.tsx'],
      },
    },
  },
};
