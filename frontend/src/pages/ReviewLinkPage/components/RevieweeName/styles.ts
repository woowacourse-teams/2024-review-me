import styled from '@emotion/styled';

import media from '@/utils/media';

export const RevieweeNameWrapper = styled.span`
  font-weight: ${({ theme }) => theme.fontWeight.semibold};

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.small};
  }
`;
