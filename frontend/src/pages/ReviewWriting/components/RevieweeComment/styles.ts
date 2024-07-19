import styled from '@emotion/styled';

export const RevieweeComment = styled.section`
  display: flex;
  justify-content: flex-start;
  align-items: center;

  height: 3.5rem;

  padding: 1rem;
  margin: 1rem 0;
  border-left: 4px solid ${({ theme }) => theme.colors.black};

  background-color: ${({ theme }) => theme.colors.white};
  color: ${({ theme }) => theme.colors.black};
`;
