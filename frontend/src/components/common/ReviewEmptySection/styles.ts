import styled from '@emotion/styled';

import media from '@/utils/media';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const NullText = styled.span`
  font-size: 23rem;
  font-weight: ${({ theme }) => theme.fontWeight.bolder};
  color: #e0e1e3;

  ${media.small} {
    font-size: 16rem;
  }

  ${media.xSmall} {
    font-size: 15rem;
  }

  ${media.xxSmall} {
    font-size: 14rem;
  }
`;

export const EmptyReviewsText = styled.span`
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.bold};

  ${media.small} {
    font-size: 2.2rem;
  }

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.mediumSmall};
  }

  ${media.xxSmall} {
    font-size: 1.8rem;
  }
`;
