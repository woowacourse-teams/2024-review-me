import styled from '@emotion/styled';

const EYE_BUTTON_SIZE = '1.6rem';

export const EyeButton = styled.button`
  display: inline-block;

  position: absolute;
  top: calc((100% - ${EYE_BUTTON_SIZE}) / 2);
  right: 1rem;

  width: ${EYE_BUTTON_SIZE};
  height: ${EYE_BUTTON_SIZE};

  cursor: pointer;
`;
