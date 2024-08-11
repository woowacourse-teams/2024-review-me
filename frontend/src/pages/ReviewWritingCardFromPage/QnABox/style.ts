import styled from '@emotion/styled';

export const QnASection = styled.section`
  margin-bottom: 2rem;
`;
export const LimitGuideMessage = styled.div`
  position: absolute;
  width: fit-content;
  padding: 1rem 2rem;
  background-color: ${({ theme }) => theme.colors.lightPurple};
`;

export const QuestionGuideline = styled.p`
  margin-bottom: 2rem;
  font-size: ${({ theme }) => theme.fontSize.small};
  color: ${({ theme }) => theme.colors.gray};
`;

export const QuestionTitle = styled.p`
  margin-bottom: 2rem;
  font-size: ${({ theme }) => theme.fontSize.basic};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
  color: ${({ theme }) => theme.colors.black};
`;
