import styled from '@emotion/styled';

import media from '@/utils/media';

export const PageContainer = styled.div`
  display: flex;
  gap: 6rem;
  justify-content: center;

  ${media.medium} {
    gap: 4rem;
  }

  ${media.small} {
    margin: 0 2rem;
  }
`;
