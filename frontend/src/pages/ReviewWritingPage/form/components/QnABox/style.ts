import styled from '@emotion/styled';

export const QnASection = styled.section`
  margin-bottom: 2rem;
`;

export const QuestionGuideline = styled.p`
  margin-bottom: 2rem;
  font-size: ${({ theme }) => theme.fontSize.small};
  color: ${({ theme }) => theme.colors.gray};
`;

export const QuestionTitle = styled.div`
  margin-bottom: 2rem;
  font-size: ${({ theme }) => theme.fontSize.basic};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
  color: ${({ theme }) => theme.colors.black};
`;

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
