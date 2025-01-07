import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewLinkDashboardContainer = styled.div`
  display: flex;
  justify-content: center;
  gap: 7rem;

  width: 100%;

  ${media.medium} {
    gap: 4rem;
  }

  @media screen and (max-width: 900px) {
    gap: 2rem;
  }

  ${media.small} {
    flex-direction: column;
    align-items: center;
  }
`;

export const FormSection = styled.section`
  section {
    width: auto;
  }

  padding: 5rem 0;

  ${media.medium} {
    section {
      padding: 0;
    }
  }

  ${media.small} {
    width: 100%;
    padding: 0;
  }
`;

export const Separator = styled.div`
  width: 0.1rem;
  // 전체 영역에서 헤더(7rem)와 푸터(6rem) 영역 제외하고, 추후 네비게이션 탭이 추가되면 해당 영역도 제외
  min-height: calc(100vh - 13rem);

  background-color: ${({ theme }) => theme.colors.lightGray};

  ${media.small} {
    display: none;
  }
`;

export const LinkSection = styled.section`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 3rem;

  width: 100%;

  padding: 5rem 0;

  ${media.medium} {
    width: 50%;
  }

  ${media.small} {
    width: 85%;
  }

  ${media.xSmall} {
    width: 90%;
  }
`;
