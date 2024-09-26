const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');
const Dotenv = require('dotenv-webpack');
const { sentryWebpackPlugin } = require('@sentry/webpack-plugin');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
const TerserPlugin = require('terser-webpack-plugin');
const CompressionPlugin = require('compression-webpack-plugin');
const zlib = require('zlib');

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
      new Dotenv({
        systemvars: true,
        path: './.env',
      }),
      //new BundleAnalyzerPlugin(),
      ...(isProduction
        ? [
            new CompressionPlugin({
              filename: '[path][base].br',
              algorithm: 'brotliCompress', // Brotli 압축 사용
              test: /\.(js|jsx|ts|tsx|css|html|svg|ico)$/, // 압축할 파일 유형
              threshold: 8192, // 8KB 이상의 파일만 압축
              minRatio: 0.8, // 압축 후 80% 이하로 줄어든 파일만 압축
              compressionOptions: {
                level: 11, // 압축 수준 (0~11, 기본값: 11)
              },
              deleteOriginalAssets: false, // 원본 파일을 삭제하지 않음
            }),
            sentryWebpackPlugin({
              authToken: process.env.SENTRY_AUTH_TOKEN,
              org: 'review-me',
              project: 'woowacourse-review-me',
              sourcemaps: {
                filesToDeleteAfterUpload: '**/*.js.map',
              },
            }),
          ]
        : []),
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
