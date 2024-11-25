import styled from '@emotion/styled';

export const ReviewCollectionContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5rem;

  width: 100%;
  padding: 1rem;

  border: 0.1rem solid ${({ theme }) => theme.colors.lightGray};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const ReviewSectionDropdown = styled.div`
  display: flex;
  justify-content: flex-end;
`;

export const ReviewCollection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const ReviewAnswerContainer = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  list-style-position: inside;
`;

export const ReviewAnswer = styled.li`
  margin-left: 2.2rem;
  text-indent: -2.2rem;
`;
