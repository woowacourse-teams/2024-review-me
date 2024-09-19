import styled from '@emotion/styled';

import media from '@/utils/media';

const EYE_BUTTON_SIZE = '1.6rem';

export const EyeButton = styled.button`
  cursor: pointer;

  position: absolute;
  top: calc((100% - ${EYE_BUTTON_SIZE}) / 2);
  right: 1rem;

  display: inline-block;

  width: ${EYE_BUTTON_SIZE};
  height: ${EYE_BUTTON_SIZE};

  ${media.xSmall} {
    width: 1.5rem;
    height: 1.5rem;
  }

  ${media.xxSmall} {
    width: 1.3rem;
    height: 1.3rem;
  }
`;
