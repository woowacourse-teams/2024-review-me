const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');
const Dotenv = require('dotenv-webpack');
const { sentryWebpackPlugin } = require('@sentry/webpack-plugin');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
const TerserPlugin = require('terser-webpack-plugin');

module.exports = (env, argv) => {
  const isProduction = argv.mode === 'production';

  return {
    mode: isProduction ? 'production' : 'development',
    entry: './src/index.tsx',
    output: {
      path: path.join(__dirname, 'dist'),
      filename: '[name].[contenthash].bundle.js',
      clean: true,
      publicPath: '/',
    },
    resolve: {
      extensions: ['.ts', '.tsx', '.js'],
      alias: {
        '@': path.resolve(__dirname, './src'),
      },
    },
    module: {
      rules: [
        {
          test: /\.(ts|tsx|js)$/,
          exclude: /(test.ts$|test.tsx$|node_modules)/,
          use: [
            {
              loader: 'babel-loader',
            },
          ],
        },
        {
          test: /\.(png|jpeg|jpg|svg)$/,
          type: 'asset/resource',
        },
      ],
    },
    plugins: [
      new HtmlWebpackPlugin({
        template: './public/index.html',
        minify: isProduction
          ? {
              collapseWhitespace: true,
              removeComments: true,
            }
          : false,
        hash: true,
        favicon: './src/favicons/favicon.ico',
      }),
      new CleanWebpackPlugin(),
      //new BundleAnalyzerPlugin(),
      new Dotenv({
        systemvars: true,
        path: './.env',
      }),
      sentryWebpackPlugin({
        authToken: process.env.SENTRY_AUTH_TOKEN,
        org: 'review-me',
        project: 'woowacourse-review-me',
        sourcemaps: {
          filesToDeleteAfterUpload: '**/*.js.map',
        },
      }),
    ],
    devtool: isProduction ? 'hidden-source-map' : 'eval',
    devServer: {
      historyApiFallback: true,
      port: 3000,
      hot: true,
    },
    optimization: {
      minimizer: [
        new TerserPlugin({
          terserOptions: {
            compress: {
              drop_console: true,
              drop_debugger: true,
              passes: 3,
            },
            format: {
              comments: false,
            },
            mangle: true,
          },
          extractComments: false,
        }),
      ],
      splitChunks: {
        chunks: 'all',
      },
    },
  };
};
