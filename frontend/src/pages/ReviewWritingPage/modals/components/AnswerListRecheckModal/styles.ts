import styled from '@emotion/styled';

import media from '@/utils/media';

export const AnswerListModalContent = styled.div`
  width: 100%;
  overflow-x: hidden;
`;

export const AnswerListContainer = styled.div`
  position: relative;

  display: flex;
  flex-direction: column;
  gap: 1.2rem;

  width: calc(${({ theme }) => theme.formWidth}- 0.2rem);
  margin-right: 0.2rem;

  ${media.medium} {
    width: ${({ theme }) => {
      const { maxWidth, padding } = theme.contentModalSize;
      const { basic } = theme.scrollbarWidth;

      return `calc(${maxWidth} - (${padding} * 2) - ${basic} - 0.2rem)`;
    }};
  }

  ${media.xSmall} {
    width: ${({ theme }) => {
      const { maxWidth, smallPadding } = theme.contentModalSize;
      const { small } = theme.scrollbarWidth;

      return `calc(${maxWidth} - (${smallPadding} * 2) - ${small} - 0.2rem)`;
    }};
  }

  @media screen and (max-width: 330px) {
    width: calc(100% - 0.2rem);
  }
`;

export const EmptyTextAnswer = styled.p`
  width: 100%;
  color: ${({ theme }) => theme.colors.gray};
`;
