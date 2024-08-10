import React, { useEffect, useRef, useState } from 'react';

import { REVIEW_WRITING_FORM_CARD_DATA } from '@/mocks/mockData/reviewWritingCardFormData';
import { ReviewWritingCardSection } from '@/types';

import ReviewWritingCard from './ReviewWritingCard';
import * as S from './styles';

const ReviewWritingCardFormPage = () => {
  const [currentCardIndex, setCurrentCardIndex] = useState(0);
  const [questionList, setQuestionList] = useState<ReviewWritingCardSection[]>(REVIEW_WRITING_FORM_CARD_DATA.sections);
  const [slideWidth, setSlideWidth] = useState(0);

  const wrapperRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (wrapperRef.current) setSlideWidth(wrapperRef.current.clientWidth);
  }, [wrapperRef]);

  const handleCurrentCardIndex = (direction: 'prev' | 'next') => {
    const STEP = {
      next: 1,
      prev: -1,
    };

    setCurrentCardIndex((prev) => prev + STEP[direction]);
  };

  return (
    <S.CardLayout>
      <S.SliderContainer ref={wrapperRef} $translateX={currentCardIndex * slideWidth}>
        {questionList.map((section, index) => (
          <S.Slide key={section.sectionId}>
            <ReviewWritingCard
              currentCardIndex={currentCardIndex}
              cardSection={section}
              cardIndex={index}
              isLastCard={questionList.length - 1 === currentCardIndex}
              handleCurrentCardIndex={handleCurrentCardIndex}
            />
          </S.Slide>
        ))}
      </S.SliderContainer>
    </S.CardLayout>
  );
};

export default ReviewWritingCardFormPage;
