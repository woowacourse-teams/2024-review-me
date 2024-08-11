import styled from '@emotion/styled';

export const CardLayout = styled.div`
  position: relative;

  overflow: hidden;

  width: ${({ theme }) => theme.formWidth};

  border: 0.1rem solid ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

interface SlideContainerProps {
  $translateX: number;
  $height: string;
}
export const SliderContainer = styled.div<SlideContainerProps>`
  transform: ${({ $translateX }) => `translateX(-${$translateX}px)`};

  display: flex;

  width: 100%;
  height: ${({ $height }) => $height};

  transition: transform 0.5s ease-in-out;
`;

export const Slide = styled.div`
  flex: 0 0 100%;
  box-sizing: border-box;
  height: fit-content;
`;
