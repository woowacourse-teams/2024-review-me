import styled from '@emotion/styled';

export const FormLayout = styled.form`
  display: flex;
  flex-direction: column;
  width: 40rem;
`;

export const Title = styled.h2`
  margin-bottom: 2.2rem;
  font-size: ${({ theme }) => theme.fontSize.basic};
`;
