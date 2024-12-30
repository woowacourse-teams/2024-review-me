import styled from '@emotion/styled';

import media from '@/utils/media';

interface DetailedWrittenReviewStyleProps {
  $isMobile: boolean;
}

export const DetailedWrittenReview = styled.div<DetailedWrittenReviewStyleProps>`
  width: 50%;

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
