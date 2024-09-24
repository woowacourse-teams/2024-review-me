import styled from '@emotion/styled';

import media from '@/utils/media';

export const Layout = styled.section`
  height: 70vh;
`;

export const Container = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  gap: 5rem;
  align-items: center;
  justify-content: center;
`;

export const ReviewComplete = styled.div`
  display: flex;
  gap: 1rem;
  align-items: center;

  img {
    ${media.xSmall} {
      width: 2.8rem;
      height: 2.8rem;
    }
  }
`;

export const Title = styled.p`
  font-size: 2.8rem;
  font-weight: bold;

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.mediumSmall};
  }
`;

export const HomeIcon = styled.img`
  width: 2rem;
  height: 2rem;

  ${media.xSmall} {
    width: 1rem;
    height: 1rem;
  }
`;

export const HomeText = styled.p`
  margin-left: 0.5rem;

  ${media.xSmall} {
    font-size: 1rem;
  }
`;
