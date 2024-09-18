import styled from '@emotion/styled';

import media from '@/utils/media';

export const AnswerListContainer = styled.div`
  position: relative;

  overflow: hidden;
  display: flex;
  flex-direction: column;
  gap: 1.2rem;

  width: ${({ theme }) => theme.formWidth};

  ${media.medium} {
    width: ${({ theme }) => {
      const { maxWidth, padding } = theme.contentModalSize;
      const { scrollbarWidth } = theme;

      return `calc(${maxWidth} - (${padding} * 2) - ${scrollbarWidth})`;
    }};
  }

  ${media.xSmall} {
    width: ${({ theme }) => {
      const { maxWidth, smallPadding } = theme.contentModalSize;
      const { scrollbarWidth } = theme;

      return `calc(${maxWidth} - (${smallPadding} * 2) - ${scrollbarWidth})`;
    }};
  }
`;

export const EmptyTextAnswer = styled.p`
  width: 100%;
  color: ${({ theme }) => theme.colors.gray};
`;
