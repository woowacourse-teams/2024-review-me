import styled from '@emotion/styled';

import media from '@/utils/media';

export const RevieweeInfo = styled.article`
  display: flex;
  gap: 0.3rem;
  align-items: center;

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.small};
  }
`;

export const Guide = styled.span`
  display: flex;
  gap: 0.2rem;

  img {
    width: 2rem;
    height: 2rem;
  }
`;

export const RevieweeName = styled.span``;
