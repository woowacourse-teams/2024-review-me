import styled from '@emotion/styled';

import media from '@/utils/media';

export const Layout = styled.div`
  display: flex;
  flex-direction: column;
  border: 0.2rem solid ${({ theme }) => theme.colors.disabled};
  border-radius: 1rem;

  padding: 2rem 2.5rem;

  gap: 1.8rem;

  &:hover {
    cursor: pointer;
    border: 0.2rem solid ${({ theme }) => theme.colors.primaryHover};

    background-color: ${({ theme }) => theme.colors.palePurple};
  }
`;

export const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;

  width: 100%;
`;

export const ProjectDetails = styled.div`
  display: flex;
  align-items: center;

  gap: 1rem;
`;

export const ProjectName = styled.h3`
  font-size: 1.8rem;

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.basic};
  }
`;

export const ReviewCount = styled.div`
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.lightPurple};

  font-size: ${({ theme }) => theme.fontSize.small};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
  color: ${({ theme }) => theme.colors.primary};

  padding: 0.1rem 0.8rem;

  ${media.xSmall} {
    font-size: 1.2rem;
  }
`;

export const ReviewLink = styled.p`
  padding-right: 2rem;

  color: ${({ theme }) => theme.colors.gray};

  line-height: 2.5rem;
  overflow-wrap: break-word;
`;

export const Divider = styled.div`
  background-color: ${({ theme }) => theme.colors.disabled};
  height: 0.2rem;
`;
