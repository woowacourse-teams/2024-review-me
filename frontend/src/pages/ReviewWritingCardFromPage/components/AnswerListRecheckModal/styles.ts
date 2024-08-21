import styled from '@emotion/styled';

import { QuestionCardContainerStyleProps } from '.';

export const AnswerListContainer = styled.div``;

export const CardLayout = styled.div`
  position: relative;

  overflow: hidden;
  display: flex;
  flex-direction: column;
  gap: 1.2rem;

  width: ${({ theme }) => theme.formWidth};
`;

export const QuestionCardContainer = styled.div<QuestionCardContainerStyleProps>`
  margin-top: ${({ $index }) => ($index === 1 ? '3rem' : '0')};
`;

export const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

export const ReviewCardWrapper = styled.div`
  overflow: hidden;
  border: 0.2rem solid ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const EmptyTextAnswer = styled.p`
  width: 100%;
  color: ${({ theme }) => theme.colors.gray};
`;
