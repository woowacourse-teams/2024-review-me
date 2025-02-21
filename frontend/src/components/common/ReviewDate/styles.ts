import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewDate = styled.div`
  display: flex;
  align-items: center;
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
  color: ${({ theme }) => theme.colors.gray};

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.small};
  }
`;
