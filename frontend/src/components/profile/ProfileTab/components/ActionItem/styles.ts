import styled from '@emotion/styled';

import media from '@/utils/media';

interface ActionItemStyleProps {
  $isDisplayedOnlyMobile: boolean;
}

export const ActionItemContainer = styled.div<ActionItemStyleProps>`
  cursor: pointer;

  display: ${({ $isDisplayedOnlyMobile }) => ($isDisplayedOnlyMobile ? 'none' : 'flex')};
  gap: 1rem;
  align-items: center;

  width: 100%;
  height: 3rem;
  padding: 1rem;

  border-radius: 0.8rem;

  :hover {
    background-color: ${({ theme }) => theme.colors.lightGray};
  }

  ${media.small} {
    display: ${({ $isDisplayedOnlyMobile }) => $isDisplayedOnlyMobile && 'flex'};
  }
`;

export const ItemText = styled.p`
  overflow: hidden;
  display: block;

  width: 100%;

  text-overflow: ellipsis;
  white-space: nowrap;
`;
