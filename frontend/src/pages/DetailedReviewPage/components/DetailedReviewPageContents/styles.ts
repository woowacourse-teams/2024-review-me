import styled from '@emotion/styled';

import media from '@/utils/media';

export const DetailedReviewPageContents = styled.div`
  border: 0.1rem solid ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  width: 70%;
  margin-top: 2rem;

  ${media.medium} {
    width: 80%;
  }

  ${media.small} {
    width: 92%;
  }
`;

export const ReviewContentContainer = styled.div`
  margin-bottom: 7rem;
  padding: 0 4rem;

  ${media.xSmall} {
    padding: 0 2rem;
  }
`;
