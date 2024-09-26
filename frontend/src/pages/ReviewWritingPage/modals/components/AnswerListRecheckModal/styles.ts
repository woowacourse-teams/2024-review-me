import styled from '@emotion/styled';

import media from '@/utils/media';

export const AnswerListContainer = styled.div`
  position: relative;

  display: flex;
  flex-direction: column;
  gap: 1.2rem;

  width: ${({ theme }) => theme.formWidth};

  ${media.medium} {
    width: ${({ theme }) => {
      const { maxWidth, padding } = theme.contentModalSize;
      const { basic } = theme.scrollbarWidth;

      return `calc(${maxWidth} - (${padding} * 2) - ${basic})`;
    }};
  }

  ${media.xSmall} {
    width: ${({ theme }) => {
      const { maxWidth, smallPadding } = theme.contentModalSize;
      const { small } = theme.scrollbarWidth;

      return `calc(${maxWidth} - (${smallPadding} * 2) - ${small})`;
    }};
  }

  @media screen and (max-width: 330px) {
    width: inherit;
  }
`;

export const EmptyTextAnswer = styled.p`
  width: 100%;
  color: ${({ theme }) => theme.colors.gray};
`;
