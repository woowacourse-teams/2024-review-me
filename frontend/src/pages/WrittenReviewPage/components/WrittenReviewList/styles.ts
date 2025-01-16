import styled from '@emotion/styled';

import media from '@/utils/media';

export const WrittenReviewList = styled.ul`
  overflow-x: hidden;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 1.7rem;

  width: 100%;
  min-width: ${({ theme }) => theme.writtenReviewLayoutSize.largeMinWidth};
  max-width: ${({ theme }) => theme.writtenReviewLayoutSize.largeMaxWidth};
  height: 100%;
  min-height: ${({ theme }) => theme.writtenReviewLayoutSize.largeMaxHeight};
  max-height: ${({ theme }) => theme.writtenReviewLayoutSize.largeMaxHeight};

  ${media.medium} {
    min-width: 65vw;
    max-width: 65vw;
  }

  ${media.small} {
    min-width: 75vw;
    max-width: 75vw;
  }

  & > li {
    margin-right: 0.5rem;
  }
`;
