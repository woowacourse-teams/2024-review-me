import { forwardRef } from 'react';

import { EssentialPropsWithChildren } from '@/types';

import * as S from './style';

interface CarouselProps {
  translateX: number;
  height?: string;
}
const Carousel = forwardRef<HTMLDivElement, EssentialPropsWithChildren<CarouselProps>>(
  ({ translateX, height, children, ...rest }, ref) => {
    return (
      <S.SliderContainer $translateX={translateX} $height={height} ref={ref} {...rest}>
        {children}
      </S.SliderContainer>
    );
  },
);

Carousel.displayName = 'Carousel';

export default Carousel;
