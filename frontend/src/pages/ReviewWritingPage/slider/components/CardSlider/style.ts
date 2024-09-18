import styled from '@emotion/styled';

import media from '@/utils/media';

export const Slide = styled.div`
  overflow-x: hidden;
  flex: 0 0 100%;

  box-sizing: border-box;
  height: fit-content;

  border: 0.1rem solid ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  ${media.small} {
    border: none;
  }
`;

export const ButtonContainer = styled.div`
  display: flex;
  gap: 2rem;
  justify-content: flex-end;

  padding-right: 2.5rem;
  padding-bottom: 2rem;
  button {
    width: auto;
    min-width: 8rem;
    height: 3.5rem;
  }

  @media screen and (max-width: 375px) {
    gap: 1rem;

    button {
      font-size: ${({ theme }) => theme.fontSize.small};
    }
  }
`;
