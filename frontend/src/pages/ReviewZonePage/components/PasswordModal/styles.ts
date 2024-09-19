import styled from '@emotion/styled';

import media from '@/utils/media';

export const PasswordModal = styled.div`
  display: flex;
  flex-direction: column;
`;

export const InputContainer = styled.form`
  display: flex;
  justify-content: space-between;
  gap: 1.5rem;

  height: 4rem;

  ${media.xSmall} {
    height: 3.8rem;
  }

  button {
    font-size: 1.4rem;
    white-space: nowrap;

    ${media.small} {
      font-size: 1.3rem;
    }
  }
`;

export const PasswordInputContainer = styled.div`
  position: relative;
  display: flex;

  width: 80%;

  ${media.medium} {
    width: 70%;
  }
`;

export const ErrorMessage = styled.p`
  margin-top: 0.2rem;
  margin-left: 0.4rem;
  font-size: 1.2rem;
  color: ${({ theme }) => theme.colors.red};
`;
