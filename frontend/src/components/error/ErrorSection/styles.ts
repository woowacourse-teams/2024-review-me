import styled from '@emotion/styled';

import media from '@/utils/media';

export const Layout = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  gap: 3rem;
  align-items: center;
  justify-content: center;

  text-align: center;

  ${media.xxSmall} {
    gap: 1.5rem;
  }
`;

export const ErrorLogoWrapper = styled.div`
  & > img {
    width: 15rem;
    height: 15rem;

    ${media.xSmall} {
      width: 11rem;
      height: 11rem;
    }

    ${media.xxSmall} {
      width: 8rem;
      height: 8rem;
    }
  }
`;

export const ErrorMessage = styled.span`
  width: max-content;
  font-size: 2rem;
  ${media.xSmall} {
    font-size: 1.6rem;
  }

  ${media.xxSmall} {
    font-size: 1.32rem;
  }
`;

export const Container = styled.div`
  display: flex;
  gap: 3.5rem;
  align-items: center;
  justify-content: center;

  ${media.xSmall} {
    flex-direction: column;
    gap: 1.5rem;
  }

  & > button {
    width: 17rem;
    height: 5rem;
    font-size: 1.4rem;

    ${media.xxSmall} {
      width: 15rem;
      height: 4rem;
      font-size: 1.1rem;
    }
  }
`;

export const ErrorSectionButtonContents = styled.div`
  display: flex;
  align-items: center;
  span {
    margin-left: 1rem;
  }
`;
