import styled from '@emotion/styled';

export const PasswordModal = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 2rem;
`;

export const InputContainer = styled.form`
  display: flex;
  gap: 1rem;
  justify-content: space-between;
  width: 100%;
`;

export const Label = styled.label`
  margin-bottom: 0.5rem;
  margin-left: 0.2rem;
  font-size: 1.4rem;
`;

export const PasswordInputContainer = styled.div`
  position: relative;
  display: flex;
  gap: 30rem;
  width: 70%;
`;

export const ErrorMessage = styled.p`
  margin-top: 0.2rem;
  margin-left: 0.4rem;
  font-size: 1.2rem;
  color: ${({ theme }) => theme.colors.red};
`;
