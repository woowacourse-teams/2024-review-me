import styled from '@emotion/styled';

import media from '@/utils/media';

export const ConfirmModalMessage = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  align-items: start;

  p {
    width: max-content;
    margin: 0;
  }

  ${media.xSmall} {
    width: 100%;
    min-width: 70vw;

    p {
      width: inherit;
      word-break: keep-all;
      word-wrap: break-word; /* 오래된 속성, 호환성 용 */
      overflow-wrap: break-word; /* 최신 속성, 단어 단위로 줄바꿈 */
    }
  }
`;
