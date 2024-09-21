import { useRef, useState, useEffect } from 'react';

import nextArrowIcon from '@/assets/nextArrow.svg';
import prevArrowIcon from '@/assets/prevArrow.svg';
import { breakpoints } from '@/styles/theme';

import * as S from './styles';

export interface Slide {
  imageSrc: string;
  alt: string;
}

interface InfinityCarouselProps {
  slideList: Slide[];
}

interface HandleSlideAnimationParams {
  slide: HTMLDivElement;
  withTransition: boolean;
  index: number;
  deltaX?: number;
}

const REAL_START_INDEX = 1;
const TRANSITION_DURATION = 500; // 트랜지션 시간
const AUTO_SLIDE_INTERVAL = 6000; // 자동 슬라이드 시간
const DRAG_THRESHOLD = 0.3; // 슬라이드 넘기기 위한 최소 드래그 거리 비율 (슬라이드 너비의 30%)

const InfinityCarousel = ({ slideList }: InfinityCarouselProps) => {
  const [currentSlideIndex, setCurrentSlideIndex] = useState(REAL_START_INDEX);
  const [isTransitioning, setIsTransitioning] = useState(false);
  const [clicked, setClicked] = useState(false);
  const [isDragging, setIsDragging] = useState(false);
  const [startX, setStartX] = useState(0); // 드래그를 시작한 시점의 X 좌표
  const [deltaX, setDeltaX] = useState(0); // 현재 드래그 중인 위치와 시작 위치 사이의 차이

  const slideRef = useRef<HTMLDivElement>(null);

  const slideLength = slideList.length;
  const clonedSlideList = [slideList[slideLength - 1], ...slideList, slideList[0]];

  const scrollToSlide = (index: number, withTransition = true) => {
    if (slideRef.current) {
      setIsTransitioning(true);
      handleSlideAnimation({ slide: slideRef.current, withTransition, index });
    }
    setCurrentSlideIndex(index);
  };

  const handleSlideAnimation = ({ slide, withTransition, index, deltaX = 0 }: HandleSlideAnimationParams) => {
    const slideWidth = slide.clientWidth;
    slide.style.transition = withTransition ? `transform ${TRANSITION_DURATION}ms ease-in-out` : 'none';
    slide.style.transform = `translate3d(${-slideWidth * index + deltaX}px, 0, 0)`;
  };

  const nextSlide = () => {
    if (clicked) return;
    setClicked(true);
    scrollToSlide(currentSlideIndex + 1);
    setTimeout(() => setClicked(false), TRANSITION_DURATION + 100);
  };

  const prevSlide = () => {
    if (clicked) return;
    setClicked(true);
    scrollToSlide(currentSlideIndex - 1);
    setTimeout(() => setClicked(false), TRANSITION_DURATION + 100);
  };

  // 초기 슬라이드 위치 설정
  useEffect(() => {
    scrollToSlide(REAL_START_INDEX, false);
  }, []);

  // 슬라이드 경계 처리
  useEffect(() => {
    if (isTransitioning) {
      if (currentSlideIndex === slideLength + 1) {
        setTimeout(() => {
          setIsTransitioning(false);
          scrollToSlide(REAL_START_INDEX, false);
        }, TRANSITION_DURATION);
      } else if (currentSlideIndex === 0) {
        setTimeout(() => {
          setIsTransitioning(false);
          scrollToSlide(slideLength, false);
        }, TRANSITION_DURATION);
      }
    }
  }, [currentSlideIndex, slideLength, isTransitioning]);

  // 자동 슬라이드 이동
  useEffect(() => {
    const timeout = setTimeout(() => {
      nextSlide();
    }, AUTO_SLIDE_INTERVAL);

    return () => clearTimeout(timeout);
  }, [currentSlideIndex, clicked]);

  const handleTouchStart = (e: React.MouseEvent | React.TouchEvent) => {
    if (window.innerWidth <= breakpoints.xSmall) {
      setIsDragging(true);
      setStartX(getClientX(e));
    }
  };

  const handleTouchMove = (e: React.MouseEvent | React.TouchEvent) => {
    if (!isDragging || !slideRef.current) return;
    const currentX = getClientX(e);
    const dragDistance = currentX - startX;

    setDeltaX(dragDistance);
    handleSlideAnimation({
      slide: slideRef.current,
      withTransition: false,
      index: currentSlideIndex,
      deltaX: dragDistance,
    });
  };

  const handleTouchUp = () => {
    setIsDragging(false);

    if (!slideRef.current) return;
    const slideWidth = slideRef.current.clientWidth;

    // 드래그된 거리가 슬라이드 너비의 30%를 넘으면 슬라이드 넘김
    if (Math.abs(deltaX) > slideWidth * DRAG_THRESHOLD) {
      deltaX > 0 ? prevSlide() : nextSlide();
    } else {
      scrollToSlide(currentSlideIndex);
    }

    setDeltaX(0);
  };

  const getClientX = (e: React.MouseEvent | React.TouchEvent) => ('touches' in e ? e.touches[0].clientX : e.clientX);

  return (
    <S.InfinityCarouselContainer>
      <S.SlideList
        ref={slideRef}
        onTouchStart={handleTouchStart}
        onTouchMove={handleTouchMove}
        onTouchEnd={handleTouchUp}
      >
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
      <S.PrevButton onClick={prevSlide}>
        <img src={prevArrowIcon} alt="이전 화살표" />
      </S.PrevButton>
      <S.NextButton onClick={nextSlide}>
        <img src={nextArrowIcon} alt="다음 화살표" />
      </S.NextButton>
    </S.InfinityCarouselContainer>
  );
};

export default InfinityCarousel;
