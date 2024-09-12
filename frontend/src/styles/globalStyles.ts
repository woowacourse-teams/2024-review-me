import { css } from '@emotion/react';

import reset from './reset';

const globalStyles = css`
  ${reset()}

  html {
    font-size: 62.5%; // NOTE: 1rem = 10px을 위한 트릭
  }

  body {
    font-family: 'Pretendard Variable', 'Noto Sans', sans-serif;
    margin: 0;
    padding: 0;
    box-sizing: border-box;

    font-size: 1.6rem;

    padding-right: 1.2rem;
  }

  /* 스크롤바 설정 */

  ::-webkit-scrollbar {
    width: 1.2rem; /* 스크롤바 너비 */
  }

  ::-webkit-scrollbar-thumb {
    background: #c1c1c1; /* 스크롤바 색상 */
    border-radius: 0.8rem;
  }

  ::-webkit-scrollbar-track {
    background: #f1f2f4; /*스크롤바 배경 색상 (lightGray)*/
    border-radius: 0.8rem;
  }
`;

export default globalStyles;
