import styled from '@emotion/styled';

import media from '@/utils/media';

export const HomePage = styled.div`
  display: flex;
  width: 100vw;
  min-height: inherit;

  ${media.xxSmall`
    flex-direction: column;
  `}
`;
