import styled from '@emotion/styled';

import media from '@/utils/media';

export const HomePage = styled.div`
  display: flex;
  width: 100vw;
  min-height: inherit;

  ${media.small} {
    flex-direction: column;
  }
`;

export const FormSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 3rem;
  align-items: center;
  justify-content: center;

  width: 40%;
  padding: 0px 9rem;

  white-space: nowrap;

  ${media.small} {
    width: 100%;
    margin: 5rem 0 4rem 0;
  }

  ${media.xSmall} {
    h2 {
      font-size: 2rem;
    }

    p {
      font-size: 1.4rem;
    }
  }

  @media screen and (max-width: 380px) {
    h2 {
      font-size: 1.8rem;
    }
  }
`;
