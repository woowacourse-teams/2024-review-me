import { useEffect } from 'react';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import { useSearchParamAndQuery } from '@/hooks';
import { CARD_FORM_MODAL_KEY } from '@/pages/ReviewWritingPage/constants';
import {
  useCurrentCardIndex,
  useResetFormRecoil,
  useUpdateDefaultAnswers,
  useNavigateBlocker,
  useLoadAndPrepareReview,
} from '@/pages/ReviewWritingPage/form/hooks';
import { CardFormModalContainer } from '@/pages/ReviewWritingPage/modals/components';
import useCardFormModal from '@/pages/ReviewWritingPage/modals/hooks/useCardFormModal';
import MobileProgressBar from '@/pages/ReviewWritingPage/progressBar/components/MobileProgressBar';
import ProgressBar from '@/pages/ReviewWritingPage/progressBar/components/ProgressBar';
import { CardSlider } from '@/pages/ReviewWritingPage/slider/components';
import { answerMapAtom, answerValidationMapAtom, reviewRequestCodeAtom, selectedCategoryAtom } from '@/recoil';
import { calculateParticle } from '@/utils';

import * as S from './styles';

const CardForm = () => {
  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  // 작성했던 내용들을 저장하는 로직
  const selectedCategory = useRecoilValue(selectedCategoryAtom);
  const answerMap = useRecoilValue(answerMapAtom);
  const answerValidation = useRecoilValue(answerValidationMapAtom);

  const getCurrentSelectedCategory = () => {
    if (selectedCategory && selectedCategory.length > 0) {
      return selectedCategory;
    }
  };

  const getAnswerValidation = () => {
    if (answerValidation && answerValidation.size > 0) {
      const plainObjectAnswers = Array.from(answerValidation.entries());
      return plainObjectAnswers.length > 0 ? plainObjectAnswers : null;
    }
  };

  const getCurrentAnswers = () => {
    if (answerMap) {
      const plainObjectAnswers = Array.from(answerMap.entries());
      return plainObjectAnswers.length > 0 ? plainObjectAnswers : null;
    }
  };

  const handleBeforeUnloadChange = () => {
    const selectedCategories = getCurrentSelectedCategory();
    const answers = getCurrentAnswers();
    const answerValidations = getAnswerValidation();

    // 빈 상태인지 확인 후 저장
    if (selectedCategories) {
      localStorage.setItem(`selectedCategories_${reviewRequestCode}`, JSON.stringify(selectedCategories));
    }
    if (answerValidations) {
      localStorage.setItem(`answerValidations_${reviewRequestCode}`, JSON.stringify(answerValidations));
    }
    if (answers) {
      localStorage.setItem(`answers_${reviewRequestCode}`, JSON.stringify(answers));
    }
  };

  useEffect(() => {
    // TODO: 라우터를 통한 이동에서도 동작할 수 있도록 하기

    window.addEventListener('beforeunload', handleBeforeUnloadChange);
    document.addEventListener('visibilitychange', handleBeforeUnloadChange);
    // window.addEventListener('pagehide', handleBeforeUnloadChange);

    return () => {
      window.removeEventListener('beforeunload', handleBeforeUnloadChange);
      document.removeEventListener('visibilitychange', handleBeforeUnloadChange);
      // window.removeEventListener('pagehide', handleBeforeUnloadChange);
    };
  }, [reviewRequestCode, answerMap, selectedCategory, answerValidation]);

  //// 복원 로직
  const setAnswerMap = useSetRecoilState(answerMapAtom); // 답변 상태를 설정할 수 있게 해줌

  ////////////////// 기존 로직

  const setReviewRequestCode = useSetRecoilState(reviewRequestCodeAtom);

  const { currentCardIndex, handleCurrentCardIndex } = useCurrentCardIndex();

  // 리뷰에 필요한 질문지,프로젝트 정보 가져오기
  const { revieweeName, projectName } = useLoadAndPrepareReview({ reviewRequestCode });
  // 답변
  // 생성된 질문지를 바탕으로 답변 기본값 및 답변의 유효성 기본값 설정
  useUpdateDefaultAnswers();

  // 모달
  const { handleOpenModal, closeModal, isOpen } = useCardFormModal();

  const handleNavigateConfirmButtonClick = () => {
    closeModal(CARD_FORM_MODAL_KEY.navigateConfirm);

    if (blocker.proceed) blocker.proceed();
  };

  // 작성 중인 답변이 있는 경우 페이지 이동을 막는 기능
  const { blocker } = useNavigateBlocker({
    openNavigateConfirmModal: () => handleOpenModal('navigateConfirm'),
  });

  const { resetFormRecoil } = useResetFormRecoil();

  useEffect(() => {
    if (reviewRequestCode) setReviewRequestCode(reviewRequestCode);
  }, [reviewRequestCode]);

  useEffect(() => {
    return () => {
      // 페이지 나갈 때 관련 recoil 상태 초기화
      resetFormRecoil();
    };
  }, []);

  const revieweeNameSuffix = `${calculateParticle({
    target: revieweeName,
    particles: { withFinalConsonant: '을', withoutFinalConsonant: '를' },
  })} 리뷰해주세요!`;

  return (
    <S.CardFormContainer>
      <S.CardForm>
        <S.RevieweeDescription>
          <S.ProjectInfoContainer>
            <S.ProjectName>{projectName}</S.ProjectName>
            <p>
              <S.RevieweeName>{revieweeName}</S.RevieweeName>
              {revieweeNameSuffix}
            </p>
          </S.ProjectInfoContainer>
        </S.RevieweeDescription>
        <S.NormalProgressBar>
          <ProgressBar currentCardIndex={currentCardIndex} handleCurrentCardIndex={handleCurrentCardIndex} />
        </S.NormalProgressBar>
        <S.MiniProgressBar>
          <MobileProgressBar currentCardIndex={currentCardIndex} handleCurrentCardIndex={handleCurrentCardIndex} />
        </S.MiniProgressBar>
        <CardSlider
          currentCardIndex={currentCardIndex}
          handleCurrentCardIndex={handleCurrentCardIndex}
          handleOpenModal={handleOpenModal}
        />
      </S.CardForm>
      <CardFormModalContainer
        isOpen={isOpen}
        closeModal={closeModal}
        handleNavigateConfirmButtonClick={handleNavigateConfirmButtonClick}
        handleRestoreButtonClick={() => {}}
        // handleRestoreButtonClick={handleRestoreAnswers}
      />
    </S.CardFormContainer>
  );
};

export default CardForm;
