import styled from '@emotion/styled';

import media from '@/utils/media';

export const Layout = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;
  width: 90%;

  ${media.medium} {
    gap: 2rem;
  }
`;

export const ReviewSection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;
`;
