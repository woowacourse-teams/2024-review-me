import styled from '@emotion/styled';

import media from '@/utils/media';

export const Layout = styled.section`
  width: 100%;

  display: flex;

  ${media.small} {
    flex-direction: column;

    padding: 0 3rem;
  }
`;

export const LeftContainer = styled.div`
  width: 30%;

  padding: 10rem 0;

  ${media.small} {
    width: 100%;
  }
`;

export const Separator = styled.div`
  width: 0.1rem;
  min-height: calc(100vh - 13rem); // 전체 영역에서 헤더와 푸터 영역 제외

  background-color: ${({ theme }) => theme.colors.lightGray};

  margin: 0 10rem;

  ${media.small} {
    display: none;
  }
`;

export const RightContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3rem;

  width: 70%;

  padding: 10rem 0;

  ${media.small} {
    width: 100%;
  }
`;

export const TitleWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

export const SubTitle = styled.span`
  color: ${({ theme }) => theme.colors.gray};
  /* font-size: ${({ theme }) => theme.fontSize.small}; */
  /* font-weight: ${({ theme }) => theme.fontWeight.semibold}; */
`;

export const ReviewLinkList = styled.div``;
