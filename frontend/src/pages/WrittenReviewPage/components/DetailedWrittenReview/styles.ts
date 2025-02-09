import styled from '@emotion/styled';

import media from '@/utils/media';

import { DetailedWrittenReviewProps } from '.';

export interface StyleProps extends Pick<DetailedWrittenReviewProps, '$isDisplayable'> {}

export const DetailedWrittenReview = styled.div<StyleProps>`
  display: block;
  max-width: ${({ theme }) => theme.writtenReviewLayoutSize.largeMaxWidth};
  min-width: 55rem;

  ${media.medium} {
    ${({ $isDisplayable }) =>
      $isDisplayable
        ? `
        display: block;
      `
        : `
        display: none;
      `}
  }
`;

export const Outline = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  min-width: ${({ theme }) => theme.writtenReviewLayoutSize.largeMinWidth};
  max-width: ${({ theme }) => theme.writtenReviewLayoutSize.largeMaxWidth};
  height: 100%;
  min-height: ${({ theme }) => theme.writtenReviewLayoutSize.largeMaxHeight};
  margin-bottom: 2rem;

  border: 0.2rem solid ${({ theme }) => theme.colors.lightGray};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  ${media.medium} {
    min-width: 65vw;
    max-width: 65vw;
  }

  ${media.small} {
    min-width: 75vw;
    max-width: 75vw;
  }
`;
