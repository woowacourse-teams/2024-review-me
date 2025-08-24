const { merge } = require('webpack-merge');
const { sentryWebpackPlugin } = require('@sentry/webpack-plugin');
const CompressionPlugin = require('compression-webpack-plugin');
const TerserPlugin = require('terser-webpack-plugin');

const common = require('./webpack.common.js');

module.exports = merge(common, {
  mode: 'production',
  devtool: 'hidden-source-map',
  plugins: [
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
  ],
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
});
