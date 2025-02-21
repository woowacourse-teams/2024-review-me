import styled from '@emotion/styled';

export const InputContainer = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
`;

export const Label = styled.label`
  margin-bottom: 1.2rem;
`;

export const InputInfo = styled.p`
  margin: 0.5rem 0.3rem 0.4rem;
  font-size: 1.2rem;
`;

export const ErrorMessage = styled.p`
  height: 1.3rem;
  padding-left: 0.7rem;
  font-size: 1.3rem;
  color: ${({ theme }) => theme.colors.red};
`;
