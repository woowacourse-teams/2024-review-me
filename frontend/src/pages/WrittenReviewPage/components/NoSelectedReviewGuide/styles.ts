import styled from '@emotion/styled';

import media from '@/utils/media';

export const NoSelectedReview = styled.section`
  display: flex;
  gap: 2rem;
  align-items: center;
  justify-content: center;

  margin: 0 auto;

  img {
    height: 3rem;

    ${media.medium} {
      height: 2.8rem;
      margin-left: 2.5rem;
    }
  }

  p {
    font-size: ${({ theme }) => theme.fontSize.mediumSmall};
    font-weight: bold;
    color: ${({ theme }) => theme.colors.disabled};

    ${media.medium} {
      font-size: ${({ theme }) => theme.fontSize.basic};
    }
  }
`;
