import { useEffect } from 'react';
import { useNavigate } from 'react-router';
import { useRecoilValue } from 'recoil';

import { ConfirmModal, AnswerListRecheckModal } from '@/components';
import {
  useCurrentCardIndex,
  useGetDataToWrite,
  useMutateReview,
  useCardSectionList,
  useResetFormRecoil,
  useSearchParamAndQuery,
  useSlideWidthAndHeight,
  useUpdateDefaultAnswers,
} from '@/hooks';
import useModals from '@/hooks/useModals';
import { answerMapAtom } from '@/recoil';
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

  const { resetFormRecoil } = useResetFormRecoil();

  const { isOpen, openModal, closeModal } = useModals();

  const navigate = useNavigate();

  const executeAfterMutateSuccess = () => {
    navigate('/user/review-writing-complete');
    closeModal(MODAL_KEYS.confirm);
  };
  const { postReview } = useMutateReview({ executeAfterMutateSuccess });

  const handleConfirmModalOpenButtonClick = () => {
    openModal(MODAL_KEYS.confirm);
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
        <S.SliderContainer ref={wrapperRef} $translateX={currentCardIndex * slideWidth} $height={slideHeight}>
          {cardSectionList?.map((section, index) => (
            <S.Slide id={makeId(index)} key={section.sectionId}>
              <ReviewWritingCard
                cardIndex={index}
                currentCardIndex={currentCardIndex}
                cardSection={section}
                isLastCard={cardSectionList.length - INDEX_OFFSET === currentCardIndex}
                handleCurrentCardIndex={handleCurrentCardIndex}
                handleRecheckButtonClick={handleRecheckButtonClick}
                handleConfirmModalOpenButtonClick={handleConfirmModalOpenButtonClick}
              />
            </S.Slide>
          ))}
        </S.SliderContainer>
      </S.CardForm>
      {isOpen(MODAL_KEYS.confirm) && (
        <ConfirmModal
          confirmButton={{ styleType: 'primary', type: 'submit', text: '제출', handleClick: submitAnswer }}
          cancelButton={{ styleType: 'secondary', text: '취소', handleClick: () => closeModal(MODAL_KEYS.confirm) }}
          handleClose={() => closeModal(MODAL_KEYS.confirm)}
          isClosableOnBackground={true}
        >
          <S.SubmitErrorMessage>
            <p>리뷰를 제출할까요?</p>
            <p>제출한 뒤에는 수정할 수 없어요.</p>
          </S.SubmitErrorMessage>
        </ConfirmModal>
      )}
      {isOpen(MODAL_KEYS.recheck) && cardSectionList && answerMap && (
        <AnswerListRecheckModal
          questionSectionList={cardSectionList}
          answerMap={answerMap}
          closeModal={() => closeModal(MODAL_KEYS.recheck)}
        />
      )}
    </>
  );
};

export default CardForm;
