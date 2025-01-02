import styled from '@emotion/styled';

import media from '@/utils/media';


export const WrittenReviewList = styled.ul`
  overflow-x: hidden;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 1.7rem;

  max-height: 68vh;

  ${media.xSmall} {
    width: 100%;
  }

  & > li {
    margin-right: 0.5rem;
  }
`;
