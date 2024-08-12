import styled from '@emotion/styled';

import { QuestionCardStyleType } from '@/types';

export const QuestionCard = styled.div<{ questionType: QuestionCardStyleType }>`
  margin-bottom: 2rem;
  font-size: ${({ questionType, theme }) => (questionType === 'guideline' ? theme.fontSize.basic : '1.8rem')};
  font-weight: ${({ questionType, theme }) =>
    questionType === 'guideline' ? theme.fontWeight.normal : theme.fontWeight.semibold};
  color: ${({ questionType, theme }) => (questionType === 'guideline' ? theme.colors.placeholder : theme.colors.black)};
`;
