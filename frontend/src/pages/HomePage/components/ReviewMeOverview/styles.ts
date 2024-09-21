import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewMeOverview = styled.section`
  position: relative;
  overflow: hidden;
  width: 65%;
  background-color: ${({ theme }) => theme.colors.lightPurple};

  ${media.small} {
    flex-direction: column;
    width: 100%;
    padding: 2rem 0;
  }
`;

export const ColumnSectionContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  height: 100%;

  gap: 1.5rem;
`;

export const OverviewTitleContainer = styled.div`
  display: flex;
  gap: 1.3rem;
  align-items: center;
  justify-content: center;

  img {
    width: 7rem;
    height: 7rem;

    ${media.medium} {
      width: 6rem;
      height: 6rem;
    }

    ${media.small} {
      width: 5.5rem;
      height: 5.5rem;
    }

    ${media.xSmall} {
      width: 4rem;
      height: 4rem;
    }

    ${media.xxSmall} {
      width: 3.5rem;
      height: 3.5rem;
    }
  }

  ${media.xxSmall} {
    gap: 0.6rem;
  }
`;

export const OverviewTitle = styled.p`
  font-size: ${({ theme }) => theme.fontSize.large};
  font-weight: ${({ theme }) => theme.fontWeight.bold};

  ${media.medium} {
    font-size: ${({ theme }) => theme.fontSize.medium};
  }

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.mediumSmall};
  }

  ${media.xxSmall} {
    font-size: 1.6rem;
  }
`;
