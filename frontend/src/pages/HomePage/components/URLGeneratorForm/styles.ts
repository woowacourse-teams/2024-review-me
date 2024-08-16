import styled from '@emotion/styled';

export const URLGeneratorForm = styled.section`
  display: flex;
  align-items: center;
  justify-content: center;

  padding: 0 9rem;
  width: 40%;
`;

export const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.2rem;

  position: relative;
`;

export const PasswordInputContainer = styled.div`
  display: flex;
  border: 0.1rem solid ${({ theme }) => theme.colors.placeholder};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  &:focus-within {
    border: 0.2rem solid ${({ theme }) => theme.colors.primary};
  }
`;

export const Label = styled.label`
  margin-bottom: 1.2rem;
`;

export const InputInfo = styled.p`
  font-size: 1.2rem;
  margin: 0.5rem 0.3rem 0;
`;

export const ErrorMessage = styled.p`
  font-size: 1.3rem;
  color: ${({ theme }) => theme.colors.red};
  height: 1.3rem;
  padding-left: 0.7rem;
`;
