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

  height: 3rem;
  padding: 1rem;

  ${media.small} {
    display: ${({ $isDisplayedOnlyMobile }) => $isDisplayedOnlyMobile && 'flex'};
  }
`;
