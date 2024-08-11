import React, { useState } from 'react';

import { useCurrentCardIndex, useReviewerAnswer, useSlideWidth } from '@/hooks';
import { REVIEW_WRITING_FORM_CARD_DATA } from '@/mocks/mockData/reviewWritingCardFormData';
import { ReviewWritingCardSection } from '@/types';

import ReviewWritingCard from './ReviewWritingCard';
import * as S from './styles';

const ReviewWritingCardFormPage = () => {
  const [questionList, setQuestionList] = useState<ReviewWritingCardSection[]>(REVIEW_WRITING_FORM_CARD_DATA.sections);

  const { wrapperRef, slideWidth } = useSlideWidth();
  const { currentCardIndex, handleCurrentCardIndex } = useCurrentCardIndex();
  const { answerMap, isAbleNextStep, updateAnswerMap } = useReviewerAnswer({ currentCardIndex, questionList });

  return (
    <S.CardLayout>
      <S.SliderContainer ref={wrapperRef} $translateX={currentCardIndex * slideWidth}>
        {questionList.map((section) => (
          <S.Slide key={section.sectionId}>
            <ReviewWritingCard
              currentCardIndex={currentCardIndex}
              cardSection={section}
              isAbleNextStep={isAbleNextStep}
              isLastCard={questionList.length - 1 === currentCardIndex}
              handleCurrentCardIndex={handleCurrentCardIndex}
              updateAnswerMap={updateAnswerMap}
            />
          </S.Slide>
        ))}
      </S.SliderContainer>
    </S.CardLayout>
  );
};

export default ReviewWritingCardFormPage;
