import React, { useRef, useState, useEffect } from 'react';

import * as S from './styles';

export interface Slide {
  imageSrc: string;
  alt: string;
}

interface CarouselProps {
  slides: Slide[];
}

const Carousel = ({ slides }: CarouselProps) => {
  const [currentSlide, setCurrentSlide] = useState(0);
  const slideRef = useRef<HTMLDivElement>(null);

  const scrollToSlide = (index: number) => {
    if (slideRef.current) {
      const slideWidth = slideRef.current.clientWidth;
      slideRef.current.style.transform = `translateX(-${slideWidth * index}px)`;
    }
    setCurrentSlide(index);
  };

  const nextSlide = () => {
    const nextIndex = (currentSlide + 1) % slides.length;
    scrollToSlide(nextIndex);
  };

  const prevSlide = () => {
    const prevIndex = (currentSlide - 1 + slides.length) % slides.length;
    scrollToSlide(prevIndex);
  };

  useEffect(() => {
    const timeout = setTimeout(() => {
      nextSlide();
    }, 10000); // 자동 슬라이드 이동 간격 (10초)

    return () => clearTimeout(timeout);
  }, [currentSlide]);

  return (
    <S.CarouselContainer>
      <S.SlideList ref={slideRef}>
        {slides.map((slide, index) => (
          <S.SlideItem key={index}>
            <S.SlideContent>
              <img src={slide.imageSrc} alt={slide.alt} />
            </S.SlideContent>
          </S.SlideItem>
        ))}
      </S.SlideList>
      <S.PrevButton onClick={prevSlide}>{'<'}</S.PrevButton>
      <S.NextButton onClick={nextSlide}>{'>'}</S.NextButton>
    </S.CarouselContainer>
  );
};

export default Carousel;
