import styled from '@emotion/styled';

import media from '@/utils/media';

export const URLGeneratorForm = styled.section`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40%;

  ${media.medium} {
    width: 45%;
    padding: 0 9rem;

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
