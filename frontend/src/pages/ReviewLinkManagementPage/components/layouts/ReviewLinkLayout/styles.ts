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
`;

export const SubTitle = styled.span`
  color: ${({ theme }) => theme.colors.gray};
`;

export const CardList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 4rem;

  height: calc(100vh - 34rem);

  overflow-y: auto;

  padding-right: 2rem;
`;
