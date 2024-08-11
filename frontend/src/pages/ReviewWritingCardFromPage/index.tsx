import React, { useEffect, useRef, useState } from 'react';

import { REVIEW_WRITING_FORM_CARD_DATA } from '@/mocks/mockData/reviewWritingCardFormData';
import { ReviewWritingCardSection } from '@/types';

import ReviewWritingCard from './ReviewWritingCard';
import * as S from './styles';

const ReviewWritingCardFormPage = () => {
  const [questionList, setQuestionList] = useState<ReviewWritingCardSection[]>(REVIEW_WRITING_FORM_CARD_DATA.sections);

  const { wrapperRef, slideWidth } = useSlideWidth();
  const { currentCardIndex, handleCurrentCardIndex } = useCurrentCardIndex();

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
