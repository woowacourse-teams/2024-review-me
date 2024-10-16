import styled from '@emotion/styled';

import media from '@/utils/media';

export const Message = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  align-items: center;

  min-width: 30rem;

  p {
    margin: 0;
    text-align: center;
  }

  ${media.xSmall} {
    min-width: 27rem;
  }

  @media screen and (max-width: 375px) {
    min-width: 60vw;
  }
`;
