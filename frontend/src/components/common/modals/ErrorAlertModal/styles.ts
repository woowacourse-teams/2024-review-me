import styled from '@emotion/styled';

import media from '@/utils/media';

export const Contents = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
  align-items: center;

  width: inherit;
`;

export const AlertTriangle = styled.img`
  width: 6rem;
  height: 6rem;

  ${media.xSmall} {
    width: 4rem;
    height: 4rem;
  }
`;
