import styled from '@emotion/styled';

export const QnASection = styled.section`
  margin-bottom: 2rem;
`;

export const QuestionTitle = styled.div`
  margin-bottom: 2rem;
  font-size: ${({ theme }) => theme.fontSize.basic};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
  color: ${({ theme }) => theme.colors.black};
`;
