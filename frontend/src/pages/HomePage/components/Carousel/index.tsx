import { useRef, useState } from 'react';

import * as S from './styles';

interface Slide {
  imageSrc: string;
  title: string;
  description: string[];
}

interface CarouselProps {
  slides: Slide[];
  pageWidth: number;
  pageHeight: number;
  gap: number;
  offset: number;
}

const Carousel = ({ slides, pageWidth, pageHeight, gap, offset }: CarouselProps) => {
  const [page, setPage] = useState(0);
  const scrollRef = useRef<HTMLDivElement>(null);

  const handleScroll = () => {
    if (scrollRef.current) {
      const scrollLeft = scrollRef.current.scrollLeft;
      const newPage = Math.round(scrollLeft / ((pageWidth + gap) * 10));
      setPage(newPage);
    }
  };

  const scrollToPage = (pageIndex: number) => {
    if (scrollRef.current) {
      scrollRef.current.scrollTo({
        left: (pageWidth + gap) * 10 * pageIndex,
        behavior: 'smooth',
      });
      setPage(pageIndex);
    }
  };

  return (
    <S.Layout>
      <S.ScrollContainer ref={scrollRef} onScroll={handleScroll} gap={gap} offset={offset}>
        {slides.map((slide, index) => (
          <S.Page key={`slide_${index}`} width={pageWidth} height={pageHeight}>
            <S.PageItem onClick={() => scrollToPage(index)}>
              <img src={slide.imageSrc} alt={slide.title} style={{ padding: index === 2 ? '3rem' : '2rem' }} />
              <S.PageTitle>{slide.title}</S.PageTitle>
              <S.PageDescription>
                {slide.description.map((desc, index) => (
                  <S.DescriptionItem key={index}>{desc}</S.DescriptionItem>
                ))}
              </S.PageDescription>
            </S.PageItem>
          </S.Page>
        ))}
      </S.ScrollContainer>
      <S.IndicatorWrapper>
        {slides.map((_, index) => (
          <S.Indicator key={`indicator_${index}`} focused={index === page} onClick={() => scrollToPage(index)} />
        ))}
      </S.IndicatorWrapper>
    </S.Layout>
  );
};

export default Carousel;
