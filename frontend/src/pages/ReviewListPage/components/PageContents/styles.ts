import styled from '@emotion/styled';

import media from '@/utils/media';

export const Layout = styled.div`
  display: flex;
  flex-direction: column;

  ${media.medium} {
    height: 50rem;
  }

  width: 90%;
`;

export const ReviewSection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;
`;
