import styled from '@emotion/styled';

export const Layout = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;
  width: ${({ theme }) => theme.formWidth};
`;

export const ReviewSection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 7rem;
`;
