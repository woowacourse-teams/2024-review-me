import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewDate = styled.div`
  display: flex;
  align-items: center;

  color: ${({ theme }) => theme.colors.gray};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.small};
  }
`;
