import { ProjectImg } from '@/components';
import { useCurrentCardIndex, useQuestionList, useReviewerAnswer, useSlideWidthAndHeight } from '@/hooks';
import { REVIEW_WRITING_FORM_CARD_DATA } from '@/mocks/mockData/reviewWritingCardFormData';

import ReviewWritingCard from '../ReviewWritingCard';

import * as S from './styles';

const PROJECT_IMAGE_SIZE = '5rem';
const INDEX_OFFSET = 1;

const CardForm = () => {
  const { revieweeName, projectName } = REVIEW_WRITING_FORM_CARD_DATA;
  const { currentCardIndex, handleCurrentCardIndex } = useCurrentCardIndex();
  const { wrapperRef, slideWidth, slideHeight, makeId } = useSlideWidthAndHeight({ currentCardIndex });
  const { questionList, updatedSelectedCategory } = useQuestionList();
  const { answerMap, isAbleNextStep, updateAnswerMap } = useReviewerAnswer({
    currentCardIndex,
    questionList,
    updatedSelectedCategory,
  });
  // TODO: POST API 연결, api 성공 후 reviewWritingCompletedPage로 이동
  const handleSubmitButtonClick = () => {
    console.log('submit answerMap', answerMap?.values());
  };
  // TODO: 미리보기 모달 연결
  const handlePreviewButtonClick = () => {
    console.log('answerMap preview', answerMap?.values());
  };

  return (
    <S.CardForm>
      <S.RevieweeDescription>
        <ProjectImg projectName={projectName} $size={PROJECT_IMAGE_SIZE} />
        <S.ProjectInfoContainer>
          <S.ProjectName>{projectName}</S.ProjectName>
          <p>
            <S.RevieweeName>{revieweeName}</S.RevieweeName>을(를) 리뷰해주세요!
          </p>
        </S.ProjectInfoContainer>
      </S.RevieweeDescription>
      <S.SliderContainer ref={wrapperRef} $translateX={currentCardIndex * slideWidth} $height={slideHeight}>
        {questionList?.map((section, index) => (
          <S.Slide id={makeId(index)} key={section.sectionId}>
            <ReviewWritingCard
              currentCardIndex={currentCardIndex}
              cardSection={section}
              isAbleNextStep={isAbleNextStep}
              isLastCard={questionList.length - INDEX_OFFSET === currentCardIndex}
              handleCurrentCardIndex={handleCurrentCardIndex}
              updateAnswerMap={updateAnswerMap}
              handlePreviewButtonClick={handlePreviewButtonClick}
              handleSubmitButtonClick={handleSubmitButtonClick}
            />
          </S.Slide>
        ))}
      </S.SliderContainer>
    </S.CardForm>
  );
};

export default CardForm;
