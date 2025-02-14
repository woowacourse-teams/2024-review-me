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
  align-items: center;
  justify-content: center;

  gap: 3rem;

  width: 40%;
  padding: 0px 9rem;

  white-space: nowrap;

  ${media.small} {
    width: 100%;
    margin: 5rem 0 4rem 0;
  }
`;
