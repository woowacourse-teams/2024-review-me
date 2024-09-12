import { forwardRef, useLayoutEffect } from 'react';

import { EssentialPropsWithChildren } from '@/types';

import * as S from './style';

interface CarouselProps {
  height?: string;
  cardIndex: number;
}
const Carousel = forwardRef<HTMLDivElement, EssentialPropsWithChildren<CarouselProps>>(
  ({ height, children, cardIndex, ...rest }, ref) => {
    const handleSlideAnimation = () => {
      if (!ref) return;
      if (!(ref as React.RefObject<HTMLDivElement>).current) return;

      const slide = (ref as React.RefObject<HTMLDivElement>).current as HTMLDivElement;

      const slideWidth = slide.clientWidth;
      slide.style.transform = `translate3d(-${slideWidth * cardIndex * 0.1}rem, 0, 0)`;
    };

    useLayoutEffect(() => {
      window.requestAnimationFrame(handleSlideAnimation);
    }, [cardIndex, ref]);

    return (
      <S.SliderContainer $height={height} ref={ref} {...rest}>
        {children}
      </S.SliderContainer>
    );
  },
);

Carousel.displayName = 'Carousel';

export default Carousel;
