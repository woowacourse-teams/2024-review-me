import styled from '@emotion/styled';

import media from '@/utils/media';

const OUTLINE_SIZE = 0.2;

export const Layout = styled.li`
  position: relative;

  display: flex;
  flex-direction: column;

  border-radius: 1rem;
  outline: ${OUTLINE_SIZE}rem solid ${({ theme }) => theme.colors.disabled};
  outline-offset: -${OUTLINE_SIZE}rem;

  &:hover {
    cursor: pointer;
    outline: ${OUTLINE_SIZE}rem solid ${({ theme }) => theme.colors.primaryHover};

    &::before {
      content: '';

      position: absolute;
      z-index: -1;
      inset: 0;

      background-color: ${({ theme }) => theme.colors.palePurple};
      border-radius: 1rem;
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

export const Title = styled.h3`
  font-weight: bold;
  color: ${({ theme }) => theme.colors.primary};
`;

export const Main = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

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
  flex-direction: column;
  gap: 1.3rem;

  width: 100%;
  padding: 0 2.5rem 2rem 2.5rem;

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

export const Divider = styled.hr`
  width: 100%;
  border-top: 0.1rem solid ${({ theme }) => theme.colors.placeholder};
`;
