import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewDateText = styled.div`
  display: inline-flex;
  align-items: center;
  justify-content: center;

  ${media.small} {
    display: none;
  }
`;

export const ClockImg = styled.img`
  width: auto;
  height: 1.6rem;
  margin-right: 0.8rem;
`;

export const ReviewDate = styled.div`
  display: flex;
  align-items: center;

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.small};
  }
`;

export const Colon = styled.span`
  margin: 0 1rem;
`;
