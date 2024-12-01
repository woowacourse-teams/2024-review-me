import { useEffect } from 'react';
import { useSetRecoilState } from 'recoil';

import { useSearchParamAndQuery } from '@/hooks';
import {
  useCurrentCardIndex,
  useResetFormRecoil,
  useUpdateDefaultAnswers,
  useLoadAndPrepareReview,
  useSaveReviewToLocalStorage,
  useRestoreFromLocalStorage,
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

  const { resetFormRecoil } = useResetFormRecoil();
  const { currentCardIndex, handleCurrentCardIndex } = useCurrentCardIndex();

  // 로컬 스토리지에 저장된 값을 기반으로 모달의 isOpen 여부 설정
  const { restoreData, initialModalsState } = useRestoreFromLocalStorage();
  const { handleOpenModal, closeModal, isOpen } = useCardFormModal({ initialStates: initialModalsState });

  const setReviewRequestCode = useSetRecoilState(reviewRequestCodeAtom);

  // 프로젝트 정보 및 질문지를 서버에서 가져옴
  const { revieweeName, projectName } = useLoadAndPrepareReview({ reviewRequestCode });

  // 생성된 질문지를 바탕으로 답변 기본값 및 답변의 유효성 기본값 설정
  useUpdateDefaultAnswers();

  useSaveReviewToLocalStorage();

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

  const handleRestoreAnswers = () => restoreData();

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
      <CardFormModalContainer isOpen={isOpen} closeModal={closeModal} handleRestoreButtonClick={handleRestoreAnswers} />
    </S.CardFormContainer>
  );
};

export default CardForm;
