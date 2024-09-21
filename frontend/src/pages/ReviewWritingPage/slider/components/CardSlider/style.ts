import styled from '@emotion/styled';

import media from '@/utils/media';

export const Slide = styled.div`
  flex: 0 0 100%;
  box-sizing: border-box;
  height: fit-content;
`;

export const ButtonContainer = styled.div`
  display: flex;
  gap: 2rem;
  justify-content: flex-end;

  padding: 0 2rem;
  padding-bottom: 2rem;
  button {
    width: auto;
    min-width: 8rem;
    height: 3.5rem;
  }

  ${media.xSmall} {
    gap: 1.5rem;
    button {
      font-size: ${({ theme }) => theme.fontSize.small};
    }
  }

  ${media.xxSmall} {
    gap: 0.8rem;

    button {
      max-width: calc(100% - (0.8rem * 2) / 3);
      padding-right: 0.4rem;
      padding-left: 0.4rem;
      text-align: center;
    }
  }
`;
