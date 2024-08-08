import styled from '@emotion/styled';

import { QuestionCardStyleType } from '@/types';

export const QuestionCard = styled.span<{ questionType: QuestionCardStyleType }>`
  font-size: ${({ theme }) => theme.fontSize.basic};
  color: ${({ questionType, theme }) => (questionType === 'guideline' ? theme.colors.placeholder : theme.colors.black)};
`;
