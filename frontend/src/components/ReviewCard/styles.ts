import styled from '@emotion/styled';

import media from '@/utils/media';

export const Layout = styled.div`
  display: flex;
  border: 0.1rem solid ${({ theme }) => theme.colors.lightGray};
  border-radius: 1rem;

  &:hover {
    cursor: pointer;
    border: 0.15rem solid ${({ theme }) => theme.colors.primaryHover};

    & > div:first-of-type {
      background-color: ${({ theme }) => theme.colors.lightPurple};
    }
  }
`;

export const LeftLineBorder = styled.div`
  width: 2.5rem;
  background-color: ${({ theme }) => theme.colors.lightGray};
  border-radius: 1rem 0 0 1rem;
`;

export const Title = styled.div`
  font-size: 1.6rem;
  font-weight: 700;
`;

export const Date = styled.p`
  height: fit-content;
  padding: 0 1rem;
  font-size: 1.3rem;
`;

export const Visibility = styled.div`
  display: flex;
  gap: 0.6rem;
  align-items: center;

  font-size: 1.6rem;
  font-weight: 700;

  img {
    width: 2rem;
  }
`;

export const Main = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: 100%;
  padding: 2rem 3rem;

  font-size: 1.6rem;
`;

export const ContentPreview = styled.p`
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;

  height: 6rem;
  padding-right: 2rem;

  line-height: 2rem;
  text-overflow: ellipsis;
  overflow-wrap: break-word;
`;

export const Footer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;

  ${media.small} {
    flex-direction: column;
    gap: 1.2rem;
    align-items: flex-start;
  }
`;

export const Keyword = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 2.5rem;
  align-items: center;

  font-size: 1.4rem;

  ${media.small} {
    gap: 1.2rem;
  }

  div {
    padding: 0.5rem 3rem;
    background-color: ${({ theme }) => theme.colors.lightPurple};
    border-radius: 0.8rem;
  }
`;
