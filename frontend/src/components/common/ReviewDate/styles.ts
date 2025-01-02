import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewDate = styled.div`
  display: flex;
  align-items: center;

  span {
    color: ${({ theme }) => theme.colors.gray};
  }

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.small};
  }
`;
