import { useEffect } from 'react';
import { useNavigate } from 'react-router';
import { useRecoilValue } from 'recoil';

import { ROUTE } from '@/constants/route';
import {
  useCurrentCardIndex,
  useGetDataToWrite,
  useMutateReview,
  useCardSectionList,
  useResetFormRecoil,
  useSearchParamAndQuery,
  useSlideWidthAndHeight,
  useUpdateDefaultAnswers,
  useNavigateBlocker,
  useModals,
} from '@/hooks';
import {
  AnswerListRecheckModal,
  NavigateBlockerModal,
  ProgressBar,
  ReviewWritingCard,
  SubmitCheckModal,
} from '@/pages/ReviewWritingPage/components';
import { answerMapAtom } from '@/recoil';
import { ReviewWritingFormResult } from '@/types';

import * as S from './styles';

// const PROJECT_IMAGE_SIZE = '5rem';
const INDEX_OFFSET = 1;

const MODAL_KEYS = {
  submitConfirm: 'SUBMIT_CONFIRM',
  navigateConfirm: 'NAVIGATE_CONFIRM',
  recheck: 'RECHECK',
};

const CardForm = () => {
  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const { currentCardIndex, handleCurrentCardIndex } = useCurrentCardIndex();

  const { wrapperRef, slideWidth, slideHeight, makeId } = useSlideWidthAndHeight({ currentCardIndex });
  // 질문지 생성
  const { data } = useGetDataToWrite({ reviewRequestCode });
  const { revieweeName, projectName } = data;
  const { cardSectionList } = useCardSectionList({ cardSectionListData: data.sections });

  // 답변
  // 생성된 질문지를 바탕으로 답변 기본값 및 답변의 유효성 기본값 설정
  useUpdateDefaultAnswers();
  const answerMap = useRecoilValue(answerMapAtom);

  const handleNavigateConfirmButtonClick = () => {
    closeModal(MODAL_KEYS.navigateConfirm);

    if (blocker.proceed) {
      blocker.proceed();
    }
  };

  const { resetFormRecoil } = useResetFormRecoil();

  const { isOpen, openModal, closeModal } = useModals();
  // 작성 중인 답변이 있는 경우 페이지 이동을 막는 기능
  const { blocker } = useNavigateBlocker({
    isOpenModal: isOpen(MODAL_KEYS.navigateConfirm) || isOpen(MODAL_KEYS.submitConfirm),
    openModal,
    modalKey: MODAL_KEYS.navigateConfirm,
  });

  const navigate = useNavigate();

  const executeAfterMutateSuccess = () => {
    navigate(`/${ROUTE.reviewWritingComplete}`);
    closeModal(MODAL_KEYS.submitConfirm);
  };
  const { postReview } = useMutateReview({ executeAfterMutateSuccess });

  const handleSubmitConfirmModalOpenButtonClick = () => {
    openModal(MODAL_KEYS.submitConfirm);
  };

  const handleNextClick = () => {
    const nextIndex = currentCardIndex + 1;
    if (nextIndex < cardSectionList.length) {
      handleCurrentCardIndex('next');
    }
  };

  const submitAnswer = async (event: React.MouseEvent) => {
    event.preventDefault();

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

  useEffect(() => {
    return () => {
      // 페이지 나갈때 관련 recoil 상태 초기화
      resetFormRecoil();
    };
  }, []);

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
        <ProgressBar
          currentCardIndex={currentCardIndex}
          cardSectionList={cardSectionList}
          handleCurrentCardIndex={handleCurrentCardIndex}
        />
        <S.SliderContainer ref={wrapperRef} $translateX={currentCardIndex * slideWidth} $height={slideHeight}>
          {cardSectionList?.map((section, index) => (
            <S.Slide id={makeId(index)} key={section.sectionId}>
              <ReviewWritingCard
                cardIndex={index}
                currentCardIndex={currentCardIndex}
                cardSection={section}
                isLastCard={cardSectionList.length - INDEX_OFFSET === currentCardIndex}
                handleNextClick={handleNextClick}
                handleCurrentCardIndex={handleCurrentCardIndex}
                handleRecheckButtonClick={handleRecheckButtonClick}
                handleSubmitConfirmModalOpenButtonClick={handleSubmitConfirmModalOpenButtonClick}
              />
            </S.Slide>
          ))}
        </S.SliderContainer>
      </S.CardForm>
      {isOpen(MODAL_KEYS.submitConfirm) && (
        <SubmitCheckModal
          handleSubmitButtonClick={submitAnswer}
          handleCancelButtonClick={() => closeModal(MODAL_KEYS.submitConfirm)}
          handleCloseModal={() => closeModal(MODAL_KEYS.submitConfirm)}
        />
      )}
      {isOpen(MODAL_KEYS.recheck) && cardSectionList && answerMap && (
        <AnswerListRecheckModal
          questionSectionList={cardSectionList}
          answerMap={answerMap}
          closeModal={() => closeModal(MODAL_KEYS.recheck)}
        />
      )}

      {isOpen(MODAL_KEYS.navigateConfirm) && (
        <NavigateBlockerModal
          handleNavigateConfirmButtonClick={handleNavigateConfirmButtonClick}
          handleCancelButtonClick={() => closeModal(MODAL_KEYS.navigateConfirm)}
          handleCloseModal={() => closeModal(MODAL_KEYS.navigateConfirm)}
        />
      )}
    </>
  );
};

export default CardForm;
