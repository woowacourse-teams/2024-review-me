import styled from '@emotion/styled';

import media from '@/utils/media';

export const MultipleChoiceAnswerList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;

  padding-left: 4rem;

  list-style-type: disc;

  ${media.xSmall} {
    padding-left: 2.5rem;
  }
`;
