import styled from '@emotion/styled';

export const RevieweeComment = styled.section`
  display: flex;
  align-items: center;
  justify-content: flex-start;

  height: 3.5rem;
  margin: 1rem 0;
  padding: 1rem;

  color: ${({ theme }) => theme.colors.black};

  background-color: ${({ theme }) => theme.colors.white};
  border-left: 0.4rem solid ${({ theme }) => theme.colors.black};
`;
