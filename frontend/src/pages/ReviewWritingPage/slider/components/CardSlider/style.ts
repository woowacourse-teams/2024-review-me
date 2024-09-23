import styled from '@emotion/styled';

import media from '@/utils/media';

export const Slide = styled.div`
  flex: 0 0 100%;
  box-sizing: border-box;
  width: 100%;
  height: fit-content;
`;

export const ButtonContainer = styled.div`
  display: flex;
  gap: 2rem;
  justify-content: flex-end;
  padding: 2rem 0 0 2rem;
  button {
    width: auto;
    min-width: 8rem;
    height: 3.5rem;
  }

  ${media.xSmall} {
    gap: 1.5rem;
    button {
      max-width: calc(100% - (0.8rem * 2) / 3);
      padding-right: 0.8rem;
      padding-left: 0.8rem;
      font-size: ${({ theme }) => theme.fontSize.small};
    }
  }

  ${media.xxSmall} {
    gap: 0.8rem;

    button {
      padding-right: 0.4rem;
      padding-left: 0.4rem;
      font-size: 1.2rem;
      text-align: center;
    }
  }
`;
