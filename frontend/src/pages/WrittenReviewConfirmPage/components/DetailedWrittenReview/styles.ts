import styled from '@emotion/styled';

import media from '@/utils/media';

interface DetailedWrittenReviewStyleProps {
  $isMobile: boolean;
}

export const DetailedWrittenReview = styled.div<DetailedWrittenReviewStyleProps>`
  ${media.xSmall} {
    ${({ $isMobile }) =>
      $isMobile
        ? `
          display: block;
          width: 100%;
        `
        : `
          display: none;
        `}
  }
`;

export const Outline = styled.div`
  min-width: ${({ theme }) => theme.writtenReviewLayoutSize.width};
  min-height: ${({ theme }) => theme.writtenReviewLayoutSize.height};
  border: 0.2rem solid ${({ theme }) => theme.colors.lightGray};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const NoSelectedReview = styled.p`
  img {
  }
`;
