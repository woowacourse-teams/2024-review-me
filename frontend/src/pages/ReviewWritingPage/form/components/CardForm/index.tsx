import { useEffect } from 'react';
import { useSetRecoilState } from 'recoil';

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
import { reviewRequestCodeAtom } from '@/recoil';
import { calculateParticle } from '@/utils';

import * as S from './styles';

const CardForm = () => {
  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

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
      // 페이지 나갈때 관련 recoil 상태 초기화
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
      />
    </S.CardFormContainer>
  );
};

export default CardForm;
