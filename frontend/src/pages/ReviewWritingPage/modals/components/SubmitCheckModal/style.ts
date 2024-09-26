import styled from '@emotion/styled';

import media from '@/utils/media';

export const Message = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  align-items: start;

  min-width: 30rem;

  p {
    width: inherit;
    margin: 0;
  }

  ${media.xSmall} {
    min-width: 27rem;
  }
`;
