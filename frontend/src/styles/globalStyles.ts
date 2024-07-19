import { css } from '@emotion/react';

import reset from './reset';

const globalStyles = css`
  ${reset()}

  body {
    font-family: 'NanumGothic', 'Noto Sans', sans-serif;
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  html {
    font-size: 62.5%; // NOTE: 1rem = 10px을 위한 트릭
  }
`;

export default globalStyles;
