import { css } from '@emotion/react';

import reset from './reset';

const globalStyles = css`
  ${reset()}

  html {
    font-size: 62.5%; // NOTE: 1rem = 10px을 위한 트릭
  }

  body {
    font-family: 'Pretendard-Regular', 'NanumGothic', 'Noto Sans', sans-serif;
    margin: 0;
    padding: 0;
    box-sizing: border-box;

    font-size: 1.6rem;
  }
`;

export default globalStyles;
