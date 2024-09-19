import styled from '@emotion/styled';

import media from '@/utils/media';

export const Layout = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;

  ${media.medium} {
    gap: 2rem;
  }

  width: 90%;
`;

export const ReviewSection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;
`;
