import styled from '@emotion/styled';

import media from '@/utils/media';

export const KeywordList = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 2.5rem;
  align-items: center;

  font-size: 1.2rem;

  ${media.small} {
    gap: 1.2rem;
  }
`;

export const KeywordItem = styled.span`
  padding: 0.5rem 2rem;
  background-color: ${({ theme }) => theme.colors.lightPurple};
  border-radius: 1.4rem;

  color: ${({ theme }) => theme.colors.primary};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
`;
