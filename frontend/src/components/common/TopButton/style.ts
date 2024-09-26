import styled from '@emotion/styled';

import media from '@/utils/media';

export const TopButton = styled.button`
  position: fixed;
  right: 5rem;
  bottom: 5rem;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 5rem;
  height: 5rem;

  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.primary};
  filter: drop-shadow(0 0 0.2rem ${({ theme }) => theme.colors.primary});
  border: 0.2rem solid ${({ theme }) => theme.colors.primary};
  border-radius: 100%;

  ${media.medium} {
    right: 7.5rem;
  }

  ${media.small} {
    right: 1.5rem;
    bottom: 5%;
    width: 4.5rem;
    height: 4.5rem;
  }

  ${media.xSmall} {
    right: 1rem;
    width: 3.8rem;
    height: 3.8rem;
  }
`;

export const ArrowImage = styled.img`
  width: 3rem;
  height: 3rem;

  ${media.small} {
    width: 2.7rem;
    height: 2.7rem;
  }

  ${media.xSmall} {
    width: 2.5rem;
    height: 2.5rem;
  }
`;
