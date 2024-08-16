import styled from '@emotion/styled';

export const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
`;

export const ErrorMessage = styled.p`
  height: 1.3rem;
  padding-left: 0.7rem;
  font-size: 1.3rem;
  color: ${({ theme }) => theme.colors.red};
`;
