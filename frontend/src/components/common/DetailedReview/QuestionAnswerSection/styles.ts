import styled from '@emotion/styled';

export const QuestionAnswerSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  width: 100%;
`;

export const TextAnswerWrapper = styled.div`
  overflow-y: auto;

  box-sizing: border-box;
  width: 100%;
  height: 20rem;
  padding: 1rem 1.5rem;

  font-size: 1.6rem;
  line-height: 2.4rem;

  background-color: ${({ theme }) => theme.colors.lightGray};
`;
