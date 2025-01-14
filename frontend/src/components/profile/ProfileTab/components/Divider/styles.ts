import styled from '@emotion/styled';

import media from '@/utils/media';

interface DividerStyleProps {
  $isDisplayedOnlyMobile: boolean;
}

export const Divider = styled.hr<DividerStyleProps>`
  display: ${({ $isDisplayedOnlyMobile }) => ($isDisplayedOnlyMobile ? 'none' : 'block')};

  width: 100%;
  height: 0;
  margin: 0.5rem 0;
  padding: 0;

  border: 0.1rem solid ${({ theme }) => theme.colors.placeholder};

  ${media.small} {
    display: ${({ $isDisplayedOnlyMobile }) => $isDisplayedOnlyMobile && 'block'};
  }
`;
