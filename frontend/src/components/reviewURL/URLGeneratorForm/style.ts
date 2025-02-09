import styled from '@emotion/styled';

import media from '@/utils/media';

export const Title = styled.h2`
  margin-bottom: 6.5rem;
  font-size: 2.5rem;
  white-space: nowrap;

  ${media.medium} {
    font-size: 2rem;
  }

  ${media.xSmall} {
    margin-bottom: 4rem;
    font-size: 1.8rem;
  }

  ${media.xxSmall} {
    font-size: 1.6rem;
  }
`;

export const Fieldset = styled.fieldset`
  display: flex;
  flex-direction: column;
  gap: 1.8rem;

  width: 100%;
  margin: 0;
  padding: 0;

  border: none;
`;

export const URLGeneratorForm = styled.form`
  & > button {
    width: 100%;
    margin-top: 1.8rem;
  }

  ${media.xSmall} {
    label {
      font-size: 1.5rem;
    }

    p {
      font-size: 1.3rem;
    }

    & > button {
      font-size: 1.5rem;
    }
  }

  ${media.xxSmall} {
    label {
      font-size: 1.3rem;
    }

    p {
      font-size: 1.1rem;
    }

    & > button {
      font-size: 1.3rem;
    }
  }
`;

export const FormContainer = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 40%;

  ${media.medium} {
    width: 45%;
    padding: 0 9rem;
  }

  ${media.small} {
    width: 100%;
    margin: 5rem 0 4rem 0;
  }
`;
