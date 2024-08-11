import { useCurrentCardIndex, useQuestionList, useReviewerAnswer, useSlideWidthAndHeight } from '@/hooks';

import ReviewWritingCard from './ReviewWritingCard';
import * as S from './styles';

const ReviewWritingCardFormPage = () => {
  const { currentCardIndex, handleCurrentCardIndex } = useCurrentCardIndex();
  const { wrapperRef, slideWidth, slideHeight, makeId } = useSlideWidthAndHeight({ currentCardIndex });
  const { questionList, updatedSelectedCategory } = useQuestionList();
  const { answerMap, isAbleNextStep, updateAnswerMap } = useReviewerAnswer({
    currentCardIndex,
    questionList,
    updatedSelectedCategory,
  });

  return (
    <S.CardLayout>
      <S.SliderContainer ref={wrapperRef} $translateX={currentCardIndex * slideWidth} $height={slideHeight}>
        {questionList?.map((section, index) => (
          <S.Slide id={makeId(index)} key={section.sectionId}>
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
