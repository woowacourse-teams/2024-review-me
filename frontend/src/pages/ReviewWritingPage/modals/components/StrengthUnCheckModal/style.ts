import styled from '@emotion/styled';

import media from '@/utils/media';

export const Contents = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: max-content;

  ${media.xSmall} {
    min-width: ${({ theme }) => {
      const { maxWidth, padding } = theme.confirmModalSize;
      return `calc(${maxWidth} - (${padding} * 2))`;
    }};
    word-wrap: break-word; /* 오래된 속성, 호환성 용 */
    overflow-wrap: break-word; /* 최신 속성, 단어 단위로 줄바꿈 */
  }
`;

export const ConfirmModalTitle = styled.p`
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;
