import styled from '@emotion/styled';

const EYE_BUTTON_SIZE = '1.6rem';

export const EyeButton = styled.button`
  cursor: pointer;

  position: absolute;
  top: calc((100% - ${EYE_BUTTON_SIZE}) / 2);
  right: 1rem;

  display: inline-block;

  width: ${EYE_BUTTON_SIZE};
  height: ${EYE_BUTTON_SIZE};
`;
