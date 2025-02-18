import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewLinkLayout = styled.div`
  display: flex;
  flex-direction: column;

  gap: 4rem;
`;

export const TitleWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

export const Title = styled.h2`
  ${media.medium} {
    font-size: 2rem;
  }

  ${media.xSmall} {
    font-size: 1.8rem;
  }

  ${media.xxSmall} {
    font-size: 1.6rem;
  }
`;

export const SubTitle = styled.span`
  color: ${({ theme }) => theme.colors.gray};

  ${media.xSmall} {
    font-size: 1.5rem;
  }

  ${media.xxSmall} {
    font-size: 1.3rem;
  }
`;

export const CardList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 4rem;

  max-height: calc(100vh - 34rem);
  overflow-y: auto;

  ${media.small} {
    max-height: none;
    padding-right: 0;
  }
`;
