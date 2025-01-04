import styled from '@emotion/styled';

import media from '@/utils/media';

export const PageContentLayout = styled.article`
  display: flex;
  flex-direction: column;
  height: 100%;

  ${media.xSmall} {
    margin: 0 auto;
  }
`;

export const Title = styled.h2`
  margin-top: 4.7rem;
  margin-bottom: 2.4rem;
  font-size: 1.8rem;
  font-weight: bold;
`;

export const Content = styled.section`

`;
