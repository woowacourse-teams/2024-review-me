import styled from '@emotion/styled';

import { breakpoints } from '@/utils/media';

export const Contents = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: fit-content;

  white-space: nowrap;

  ${breakpoints.xSmall} {
    min-width: ${({ theme }) => {
      const { maxWidth, padding } = theme.confirmModalSize;
      return `calc(${maxWidth} - (${padding} * 2))`;
    }};
    white-space: break-spaces;
  }
`;

export const ConfirmModalTitle = styled.p`
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;
