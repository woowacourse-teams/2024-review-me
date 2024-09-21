import styled from '@emotion/styled';

import media from '@/utils/media';

export const PasswordModal = styled.div`
  overflow: hidden;
  display: flex;
  flex-direction: column;
`;

export const InputContainer = styled.form`
  display: flex;
  gap: 1.5rem;
  justify-content: space-between;

  height: 4rem;
  margin-top: 3rem;

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

export const ErrorMessageWrapper = styled.div`
  height: 1.7rem;
`;

export const ErrorMessage = styled.p`
  margin-top: 0.3rem;
  margin-left: 0.4rem;
  font-size: 1.2rem;
  color: ${({ theme }) => theme.colors.red};

  ${media.xxSmall} {
    font-size: 1.1rem;
  }
`;
