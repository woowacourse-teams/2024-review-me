import styled from '@emotion/styled';

import media from '@/utils/media';

export const URLGeneratorForm = styled.section`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 40%;
  padding: 0 9rem;

  ${media.medium} {
    width: 45%;

    h2 {
      font-size: 2rem;
    }
  }

  ${media.small} {
    width: 100%;
    margin: 5rem 0 4rem 0;
  }

  ${media.xSmall} {
    h2 {
      margin-bottom: 4rem;
      font-size: 1.8rem;
    }

    label {
      font-size: 1.5rem;
    }

    p {
      font-size: 1.3rem;
    }

    button {
      font-size: 1.5rem;
    }
  }

  ${media.xxSmall} {
    h2 {
      font-size: 1.6rem;
    }

    label {
      font-size: 1.3rem;
    }

    p {
      font-size: 1.1rem;
    }

    button {
      font-size: 1.3rem;
    }
  }
`;

export const InputContainer = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
`;

export const PasswordInputContainer = styled.div`
  position: relative;
  display: flex;
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
