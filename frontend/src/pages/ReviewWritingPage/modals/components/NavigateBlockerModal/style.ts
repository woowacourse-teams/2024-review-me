import styled from '@emotion/styled';

import media from '@/utils/media';

export const ConfirmModalMessage = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  align-items: center;

  p {
    margin: 0;
    text-align: center;
  }

  ${media.xSmall} {
    width: 100%;
    min-width: 70vw;
  }
`;
