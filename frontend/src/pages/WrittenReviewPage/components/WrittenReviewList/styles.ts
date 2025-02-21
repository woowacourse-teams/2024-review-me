import styled from '@emotion/styled';

import media from '@/utils/media';

export const WrittenReviewList = styled.ul`
  scrollbar-gutter: stable;

  overflow-x: auto;
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
    min-width: 70vw;
    max-width: 70vw;
  }

  ${media.small} {
    min-width: 85vw;
    max-width: 85vw;
  }

  & > li {
    margin-right: 0.9rem;
  }
`;
