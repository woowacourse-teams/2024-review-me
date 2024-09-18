import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewMeOverview = styled.section`
  position: relative;
  overflow: hidden;
  width: 65%;
  background-color: ${({ theme }) => theme.colors.lightPurple};

  ${media.xxSmall`
    background-color: #E6E3F6; // light-purple
    flex-direction: column;
    width: 100%;
    height: 38vh;
  `}
`;

export const ColumnSectionContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  height: 100%;

  ${media.xxSmall`
    gap: 1rem;
  `}
`;

export const OverviewTitleContainer = styled.div`
  display: flex;
  gap: 1.3rem;
  align-items: center;
  justify-content: center;

  img {
    width: 7rem;
    height: 7rem;
  }

  ${media.xxSmall`
    img {
      width: 4rem;
      height: 4rem;
    }

  `}
`;

export const OverviewTitle = styled.p`
  font-size: ${({ theme }) => theme.fontSize.large};
  font-weight: ${({ theme }) => theme.fontWeight.bold};

  ${media.xxSmall`
    font-size: 1.8rem;
  `}
`;
