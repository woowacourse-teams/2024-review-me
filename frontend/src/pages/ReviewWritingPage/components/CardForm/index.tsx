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
  useUpdateDefaultAnswers,
  useNavigateBlocker,
  useModals,
} from '@/hooks';
import { ProgressBar } from '@/pages/ReviewWritingPage/components';
import { answerMapAtom } from '@/recoil';
import { ReviewWritingFormResult } from '@/types';

import CardFormModalContainer from '../CardFormModalContainer';
import CardSlider from '../CardSlider';

import * as S from './styles';

// const PROJECT_IMAGE_SIZE = '5rem';

export const CARD_FORM_MODAL_KEY = {
  submitConfirm: 'SUBMIT_CONFIRM',
  navigateConfirm: 'NAVIGATE_CONFIRM',
  recheck: 'RECHECK',
};

const CardForm = () => {
  const answerMap = useRecoilValue(answerMapAtom);
  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const { currentCardIndex, handleCurrentCardIndex } = useCurrentCardIndex();

  // 질문지 생성
  const { data } = useGetDataToWrite({ reviewRequestCode });
  const { revieweeName, projectName } = data;
  const { cardSectionList } = useCardSectionList({ cardSectionListData: data.sections });

  // 답변
  // 생성된 질문지를 바탕으로 답변 기본값 및 답변의 유효성 기본값 설정
  useUpdateDefaultAnswers();

  // 모달
  const { isOpen, openModal, closeModal } = useModals();
  const handleOpenModal = (key: keyof typeof CARD_FORM_MODAL_KEY) => {
    openModal(CARD_FORM_MODAL_KEY[key]);
  };

  const handleNavigateConfirmButtonClick = () => {
    closeModal(CARD_FORM_MODAL_KEY.navigateConfirm);

    if (blocker.proceed) {
      blocker.proceed();
    }
  };

  // 작성 중인 답변이 있는 경우 페이지 이동을 막는 기능
  const { blocker } = useNavigateBlocker({
    isOpenModal: isOpen(CARD_FORM_MODAL_KEY.navigateConfirm) || isOpen(CARD_FORM_MODAL_KEY.submitConfirm),
    openModal,
    modalKey: CARD_FORM_MODAL_KEY.navigateConfirm,
  });

  // 답변 제출
  const navigate = useNavigate();
  const executeAfterMutateSuccess = () => {
    navigate(`/${ROUTE.reviewWritingComplete}`);
    closeModal(CARD_FORM_MODAL_KEY.submitConfirm);
  };
  const { postReview } = useMutateReview({ executeAfterMutateSuccess });

  const submitAnswer = async (event: React.MouseEvent) => {
    event.preventDefault();

    if (!answerMap || !reviewRequestCode) return;

    const result: ReviewWritingFormResult = {
      reviewRequestCode: reviewRequestCode,
      answers: Array.from(answerMap.values()),
    };
    postReview(result);
  };

  const { resetFormRecoil } = useResetFormRecoil();

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
        <CardSlider
          currentCardIndex={currentCardIndex}
          cardSectionList={cardSectionList}
          handleCurrentCardIndex={handleCurrentCardIndex}
          handleOpenModal={handleOpenModal}
        />
      </S.CardForm>
      <CardFormModalContainer
        isOpen={isOpen}
        closeModal={closeModal}
        handleNavigateConfirmButtonClick={handleNavigateConfirmButtonClick}
        submitAnswer={submitAnswer}
      />
    </>
  );
};

export default CardForm;
