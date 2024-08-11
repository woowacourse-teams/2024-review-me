import { useCurrentCardIndex, useQuestionList, useReviewerAnswer, useSlideWidth } from '@/hooks';

import ReviewWritingCard from './ReviewWritingCard';
import * as S from './styles';

const ReviewWritingCardFormPage = () => {
  const { wrapperRef, slideWidth } = useSlideWidth();
  const { currentCardIndex, handleCurrentCardIndex } = useCurrentCardIndex();
  const { questionList, updatedSelectedCategory } = useQuestionList();
  const { answerMap, isAbleNextStep, updateAnswerMap } = useReviewerAnswer({
    currentCardIndex,
    questionList,
    updatedSelectedCategory,
  });

  return (
    <S.CardLayout>
      <S.SliderContainer ref={wrapperRef} $translateX={currentCardIndex * slideWidth}>
        {questionList?.map((section) => (
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
