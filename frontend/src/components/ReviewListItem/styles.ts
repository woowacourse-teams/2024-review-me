import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewListItem = styled.li`
  display: flex;
  flex-direction: column;

  min-width: ${({ theme }) => theme.writtenReviewLayoutSize.width};
  min-height: 20rem;

  border: 0.2rem solid ${({ theme }) => theme.colors.placeholder};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  ${media.small} {
    min-width: 30rem;
    min-height: 18rem;
  }
`;
