import styled from '@emotion/styled';

export const Question = styled.p`
  margin-bottom: 1rem;
  font-size: 1.6rem;
  font-weight: bold;
`;

export const ReviewSection = styled.section`
  width: 100%;
  margin-top: 3.2rem;
`;

export const Answer = styled.div`
  overflow-y: auto;

  box-sizing: border-box;
  width: 100%;
  height: 23rem;
  padding: 1rem 1.5rem;

  font-size: 1.6rem;

  background-color: ${({ theme }) => theme.colors.lightGray};
`;
