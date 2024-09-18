import styled from '@emotion/styled';

import media from '@/utils/media';

export const ConfirmModalMessage = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  align-items: start;

  word-break: keep-all;

  p {
    width: max-content;
    margin: 0;
  }

  ${media.xSmall} {
    width: 100%;
    min-width: 70vw;

    p {
      width: inherit;
    }
  }
`;
