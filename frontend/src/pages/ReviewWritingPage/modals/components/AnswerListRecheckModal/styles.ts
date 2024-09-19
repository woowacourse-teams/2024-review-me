import styled from '@emotion/styled';

import media from '@/utils/media';

export const AnswerListContainer = styled.div`
  position: relative;

  display: flex;
  flex-direction: column;
  gap: 1.2rem;

  width: ${({ theme }) => theme.formWidth};

  word-break: keep-all;

  ${media.medium} {
    width: ${({ theme }) => {
      const { maxWidth, padding } = theme.contentModalSize;
      const { scrollbarWidth } = theme;

      return `calc(${maxWidth} - (${padding} * 2) - ${scrollbarWidth})`;
    }};
  }

  ${media.xSmall} {
    width: 100%;
  }
`;

export const EmptyTextAnswer = styled.p`
  width: 100%;
  color: ${({ theme }) => theme.colors.gray};
`;
