import styled from '@emotion/styled';

import media from '@/utils/media';

export const Layout = styled.div`
  display: flex;
  flex-direction: column;
  border: 0.2rem solid ${({ theme }) => theme.colors.disabled};
  border-radius: 1rem;

  &:hover {
    cursor: pointer;
    border: 0.2rem solid ${({ theme }) => theme.colors.primaryHover};

    & > div {
      background-color: ${({ theme }) => theme.colors.palePurple};
    }
  }
`;

export const Header = styled.div`
  display: flex;
  align-items: center;

  width: 100%;
  padding: 2rem 0 0 2.5rem;

  border-radius: 1rem 1rem 0 0;
`;

export const Main = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: 100%;
  padding: 2rem 2.5rem;

  font-size: 1.6rem;

  border-radius: 0 0 1rem 1rem;
`;

export const ContentPreview = styled.p`
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;

  height: 7.5rem;
  padding-right: 2rem;

  line-height: 2.5rem;
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

export const ReviewKeywordList = styled.ul`
  display: flex;
  flex-wrap: wrap;
  gap: 2.5rem;
  align-items: center;

  font-size: 1.2rem;
  list-style-type: none;

  ${media.small} {
    gap: 1.2rem;
  }
`;
