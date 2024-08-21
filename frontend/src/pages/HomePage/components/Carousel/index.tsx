import { useRef, useState, useEffect } from 'react';

import * as S from './styles';

export interface Slide {
  imageSrc: string;
  alt: string;
}

interface CarouselProps {
  slideList: Slide[];
}

const REAL_START_INDEX = 1;

const TRANSITION_DURATION = 500; // NOTE: 트랜지션(애니메이션) 시간
const AUTO_SLIDE_INTERVAL = 6000; // NOTE: 자동 슬라이드 시간

const Carousel = ({ slideList }: CarouselProps) => {
  // NOTE: 마지막 슬라이드를 복제해서 slideList의 맨 앞에 추가하므로 처음에 보여져야 하는 슬라이드는 1번 인덱스
  const [currentSlideIndex, setCurrentSlideIndex] = useState(REAL_START_INDEX);
  const [isTransitioning, setIsTransitioning] = useState(false);
  const slideRef = useRef<HTMLDivElement>(null);

  const slideLength = slideList.length;
  // NOTE: 첫 슬라이드와 마지막 슬라이드의 복제본을 각각 맨 뒤, 맨 처음에 추가
  const clonedSlideList = [slideList[slideLength - 1], ...slideList, slideList[0]];

  const scrollToSlide = (index: number) => {
    if (slideRef.current) {
      setIsTransitioning(true);

      const slideWidth = slideRef.current.clientWidth;
      slideRef.current.style.transition = 'transform 0.5s ease-in-out';
      slideRef.current.style.transform = `translateX(-${slideWidth * index * 0.1}rem)`;
    }
    setCurrentSlideIndex(index);
  };

  const nextSlide = () => {
    scrollToSlide(currentSlideIndex + 1);
  };

  const prevSlide = () => {
    scrollToSlide(currentSlideIndex - 1);
  };

  // NOTE: 맨 처음/맨 끝 슬라이드 전환용 useEffect
  useEffect(() => {
    if (!isTransitioning) return;

    if (currentSlideIndex === slideLength + 1) {
      // 마지막 슬라이드 처리
      setTimeout(() => {
        setIsTransitioning(false);
        slideRef.current!.style.transition = 'none';
        slideRef.current!.style.transform = `translateX(-${slideRef.current!.clientWidth * 0.1}rem)`;
        setCurrentSlideIndex(REAL_START_INDEX);
      }, TRANSITION_DURATION); // NOTE: 애니메이션 트랜지션 시간과 동일하게 설정 (0.5초)
    }

    if (currentSlideIndex === 0) {
      // 첫 번째 슬라이드 처리
      setTimeout(() => {
        setIsTransitioning(false);
        slideRef.current!.style.transition = 'none';
        slideRef.current!.style.transform = `translateX(-${slideRef.current!.clientWidth * slideLength * 0.1}rem)`;
        setCurrentSlideIndex(slideLength);
      }, TRANSITION_DURATION);
    }
  }, [currentSlideIndex, slideLength]);

  // NOTE: 슬라이드 자동 이동용
  useEffect(() => {
    const timeout = setTimeout(() => {
      nextSlide();
    }, AUTO_SLIDE_INTERVAL);

    return () => clearTimeout(timeout);
  }, [currentSlideIndex]);

  return (
    <S.CarouselContainer>
      <S.SlideList ref={slideRef}>
        {clonedSlideList.map((slide, index) => (
          <S.SlideItem key={index}>
            <S.SlideContent>
              <img src={slide.imageSrc} alt={slide.alt} />
            </S.SlideContent>
          </S.SlideItem>
        ))}
      </S.SlideList>
      <S.IndicatorWrapper>
        {slideList.map((_, index) => (
          <S.Indicator
            key={`indicator_${index}`}
            focused={index === (currentSlideIndex - 1 + slideLength) % slideLength}
            onClick={() => scrollToSlide(index + 1)}
          />
        ))}
      </S.IndicatorWrapper>
      <S.PrevButton onClick={prevSlide}>{'<'}</S.PrevButton>
      <S.NextButton onClick={nextSlide}>{'>'}</S.NextButton>
    </S.CarouselContainer>
  );
};

export default Carousel;
