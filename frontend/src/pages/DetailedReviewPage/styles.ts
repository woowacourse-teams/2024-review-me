import styled from '@emotion/styled';

import media from '@/utils/media';

export const PageWithBackButton = styled.div`
  width: 70%;

  margin: 2rem;

  ${media.small} {
    width: 80%;
  }

  ${media.xSmall} {
    width: 90%;
  }
`;
