import styled from '@emotion/styled';

import media from '@/utils/media';

export const Contents = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: max-content;

  word-break: keep-all;

  ${media.xSmall} {
    min-width: ${({ theme }) => {
      const { maxWidth, padding } = theme.confirmModalSize;
      return `calc(${maxWidth} - (${padding} * 2))`;
    }};
  }
`;

export const ConfirmModalTitle = styled.p`
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;
