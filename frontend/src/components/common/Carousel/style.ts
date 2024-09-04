import styled from '@emotion/styled';

interface SlideContainerProps {
  $translateX: number;
  $height?: string;
}

export const SliderContainer = styled.div<SlideContainerProps>`
  transform: ${({ $translateX }) => `translateX(-${$translateX}px)`};

  display: flex;

  width: 100%;
  height: ${({ $height }) => $height || 'auto'};
  margin-bottom: 2rem;

  transition: transform 0.5s ease-in-out;
`;
