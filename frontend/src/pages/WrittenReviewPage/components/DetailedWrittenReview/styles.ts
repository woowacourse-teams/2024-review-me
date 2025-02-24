import styled from '@emotion/styled';

import media from '@/utils/media';

import { DetailedWrittenReviewProps } from '.';

export interface StyleProps extends Pick<DetailedWrittenReviewProps, '$isDisplayable'> {}

export const DetailedWrittenReview = styled.div<StyleProps>`
  display: flex;
  flex-direction: column;
  justify-content: center;

  min-width: 55rem;
  max-width: ${({ theme }) => theme.writtenReviewLayoutSize.largeMaxWidth};

  ${media.medium} {
    ${({ $isDisplayable }) =>
      $isDisplayable
        ? `
        display: flex;
      `
        : `
        display: none;
      `}
  }

  ${media.small} {
    max-width: 90vw;
    min-width: 0;
    margin: 0 1.5rem;
  }
`;

export const Outline = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  min-width: ${({ theme }) => theme.writtenReviewLayoutSize.largeMinWidth};
  height: 100%;
  min-height: ${({ theme }) => theme.writtenReviewLayoutSize.largeMaxHeight};
  margin-bottom: 2rem;

  border: 0.2rem solid ${({ theme }) => theme.colors.lightGray};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  ${media.small} {
    min-width: 0;
    max-width: 90vw;
  }
`;
