import styled from '@emotion/styled';

export const PasswordModal = styled.div`
  display: flex;
  flex-direction: column;

  height: 14rem;
`;

export const ModalTitle = styled.h3`
  font-weight: ${({ theme }) => theme.fontWeight.bold};

  margin-bottom: 2rem;
`;

export const InputContainer = styled.div`
  display: flex;
  gap: 1rem;

  justify-content: space-between;
  width: 100%;
`;

export const Label = styled.label`
  font-size: 1.4rem;
  margin-left: 0.2rem;
  margin-bottom: 0.5rem;
`;

export const PasswordInputContainer = styled.div`
  display: flex;
  position: relative;
`;

export const ErrorMessage = styled.p`
  font-size: 1.2rem;
  color: ${({ theme }) => theme.colors.red};

  margin-top: 0.2rem;
  margin-left: 0.4rem;
`;
