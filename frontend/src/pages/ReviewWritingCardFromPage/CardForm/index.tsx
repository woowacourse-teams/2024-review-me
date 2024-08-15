import { useEffect } from 'react';
import { useNavigate } from 'react-router';

import { ConfirmModal, AnswerListRecheckModal } from '@/components';
import {
  useCurrentCardIndex,
  useGetDataToWrite,
  useMutateReview,
  useQuestionList,
  useReviewerAnswer,
  useSearchParamAndQuery,
  useSlideWidthAndHeight,
} from '@/hooks';
import useModals from '@/hooks/useModals';
import { ReviewWritingFormResult } from '@/types';

import ReviewWritingCard from '../ReviewWritingCard';

import * as S from './styles';

// const PROJECT_IMAGE_SIZE = '5rem';
const INDEX_OFFSET = 1;
const MODAL_KEYS = {
  confirm: 'CONFIRM',
  recheck: 'RECHECK',
};

const CardForm = () => {
  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const { data } = useGetDataToWrite({ reviewRequestCode });
  const { revieweeName, projectName } = data;

  const { currentCardIndex, handleCurrentCardIndex } = useCurrentCardIndex();

  const { wrapperRef, slideWidth, slideHeight, makeId } = useSlideWidthAndHeight({ currentCardIndex });

  const { questionList, updatedSelectedCategory } = useQuestionList({ questionListSectionsData: data.sections });

  const { answerMap, isAbleNextStep, updateAnswerMap, updateAnswerValidationMap } = useReviewerAnswer({
    currentCardIndex,
    questionList,
    updatedSelectedCategory,
  });
  const { isOpen, openModal, closeModal } = useModals();
  const navigate = useNavigate();

  const executeAfterMutateSuccess = () => {
    navigate('/user/review-writing-complete');
    closeModal(MODAL_KEYS.confirm);
  };
  const { postReview } = useMutateReview({ executeAfterMutateSuccess });

  const handleSubmitButtonClick = () => {
    openModal(MODAL_KEYS.confirm);
  };

  const submitAnswer = async () => {
    if (!answerMap || !reviewRequestCode) return;

    const result: ReviewWritingFormResult = {
      reviewRequestCode: reviewRequestCode,
      answers: Array.from(answerMap.values()),
    };
    postReview(result);
  };

  const handleRecheckButtonClick = () => {
    openModal(MODAL_KEYS.recheck);
  };

  return (
    <>
      <S.CardForm>
        <S.RevieweeDescription>
          {/* 현재 프로젝트가 깃헙 연동이 아니라서 주석 처리 <ProjectImg projectName={projectName} $size={PROJECT_IMAGE_SIZE} /> */}
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
                cardIndex={index}
                currentCardIndex={currentCardIndex}
                cardSection={section}
                isAbleNextStep={isAbleNextStep}
                isLastCard={questionList.length - INDEX_OFFSET === currentCardIndex}
                handleCurrentCardIndex={handleCurrentCardIndex}
                updateAnswerMap={updateAnswerMap}
                updateAnswerValidationMap={updateAnswerValidationMap}
                handleRecheckButtonClick={handleRecheckButtonClick}
                handleSubmitButtonClick={handleSubmitButtonClick}
              />
            </S.Slide>
          ))}
        </S.SliderContainer>
      </S.CardForm>
      {isOpen(MODAL_KEYS.confirm) && (
        <ConfirmModal
          confirmButton={{ type: 'primary', text: '제출', handleClick: submitAnswer }}
          cancelButton={{ type: 'secondary', text: '취소', handleClick: () => closeModal(MODAL_KEYS.confirm) }}
          handleClose={() => closeModal(MODAL_KEYS.confirm)}
          isClosableOnBackground={true}
        >
          <S.SubmitErrorMessage>
            <p>리뷰를 제출할까요?</p>
            <p>제출한 뒤에는 수정할 수 없어요.</p>
          </S.SubmitErrorMessage>
        </ConfirmModal>
      )}
      {isOpen(MODAL_KEYS.recheck) && questionList && answerMap && (
        <AnswerListRecheckModal
          questionSectionList={questionList}
          answerMap={answerMap}
          closeModal={() => closeModal(MODAL_KEYS.recheck)}
        />
      )}
    </>
  );
};

export default CardForm;
