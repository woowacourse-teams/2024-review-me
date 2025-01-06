import styled from '@emotion/styled';

import media from '@/utils/media';

interface ReadonlyItemStyleProps {
  $isDisplayedOnlyMobile: boolean;
}

export const ReadonlyItemContainer = styled.div<ReadonlyItemStyleProps>`
  cursor: default;

  display: ${({ $isDisplayedOnlyMobile }) => ($isDisplayedOnlyMobile ? 'none' : 'flex')};
  gap: 1rem;
  align-items: center;

  width: 100%;
  height: 3rem;
  padding: 1rem;

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
