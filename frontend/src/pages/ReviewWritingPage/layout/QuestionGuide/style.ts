import styled from '@emotion/styled';

export const QuestionRequiredMark = styled.sup`
  font-size: ${({ theme }) => theme.colors.small};
  color: ${({ theme }) => theme.colors.red};
`;

export const MultipleGuideline = styled.span`
  margin-left: 0.5rem;
`;

export const NotRequiredQuestionText = styled.span`
  margin-left: 0.5rem;
`;
