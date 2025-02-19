import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewLinkDashboardContainer = styled.div`
  display: flex;
  justify-content: center;
  gap: 7rem;

  width: 100%;
  // 전체 영역에서 헤더(7rem), 푸터(6rem), 네비게이션 탭(4rem) 영역 제외
  min-height: calc(100vh - 17rem);

  ${media.medium} {
    gap: 4rem;
  }

  @media screen and (max-width: 900px) {
    gap: 2rem;
  }

  ${media.small} {
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;

    margin-top: 4rem;
  }

  ${media.xSmall} {
    h2 {
      font-size: 2rem;
    }
  }

  @media screen and (max-width: 370px) {
    h2 {
      font-size: 1.6rem;
    }
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
    width: 85%;
    padding: 0;
  }
`;

export const Separator = styled.div`
  width: 0.3rem;

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
