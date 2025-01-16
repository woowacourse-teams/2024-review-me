import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewListItem = styled.li`
  display: flex;
  flex-direction: column;

  min-width: ${({ theme }) => theme.writtenReviewLayoutSize.largeMinWidth};
  max-width: ${({ theme }) => theme.writtenReviewLayoutSize.largeMaxWidth};
  min-height: 20rem;
  max-height: 24rem;

  border: 0.2rem solid ${({ theme }) => theme.colors.placeholder};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  ${media.medium} {
    min-width: 62vw;
    min-height: 18vh;
  }

  ${media.small} {
    min-width: 65vw;
    min-height: 14vh;
  }

  ${media.xSmall} {
    min-width: 70vw;
    min-height: 14vh;
  }
`;
