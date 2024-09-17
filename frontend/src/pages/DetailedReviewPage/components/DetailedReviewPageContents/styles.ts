import styled from '@emotion/styled';

import media from '@/utils/media';

export const DetailedReviewPageContents = styled.div`
  border: 0.1rem solid ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  margin-top: 2rem;

  @media (max-width: 767px) {
    width: 92%;
  }

  @media (min-width: 768px) and (max-width: 1023px) {
    width: 80%;
  }

  @media (min-width: 1024px) {
    width: 70%;
  }
`;

export const ReviewContentContainer = styled.div`
  margin-bottom: 7rem;
  padding: 0 4rem;

  ${media.xSmall`
    padding: 0 2rem;
  `};
`;
