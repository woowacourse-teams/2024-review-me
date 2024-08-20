import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import { useRecoilState, useRecoilValue } from 'recoil';

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
import { answerMapAtom, answerValidationMapAtom, visitedCardListAtom } from '@/recoil';
import { ReviewWritingFormResult } from '@/types';

import ProgressBar from '../ProgressBar';
import ReviewWritingCard from '../ReviewWritingCard';

import * as S from './styles';

// const PROJECT_IMAGE_SIZE = '5rem';
const INDEX_OFFSET = 1;
const MODAL_KEYS = {
  confirm: 'CONFIRM',
  recheck: 'RECHECK',
};

type QuestionContent = {
  [key: number]: string;
};

const QUESTION_CONTENTS: QuestionContent = {
  1: '카테고리 선택',
  2: '커뮤니케이션/협업',
  3: '문제 해결',
  4: '시간 관리',
  5: '기술 역량/전문 지식',
  6: '성장 마인드셋',
  7: '단점 피드백',
  8: '추가 리뷰/응원',
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
  const answerValidateMap = useRecoilValue(answerValidationMapAtom);

  const { resetFormRecoil } = useResetFormRecoil();

  const { isOpen, openModal, closeModal } = useModals();

  const navigate = useNavigate();

  const executeAfterMutateSuccess = () => {
    navigate('/user/review-writing-complete');
    closeModal(MODAL_KEYS.confirm);
  };
  const { postReview } = useMutateReview({ executeAfterMutateSuccess });
  const [visitedCardList, setVisitedCardList] = useRecoilState(visitedCardListAtom);

  const handleConfirmModalOpenButtonClick = () => {
    openModal(MODAL_KEYS.confirm);
  };

  const handleNextClick = () => {
    const nextIndex = currentCardIndex + 1;
    if (nextIndex < cardSectionList.length) {
      const nextSectionId = cardSectionList[nextIndex].sectionId;
      setVisitedCardList((prev) => {
        const newVisitedCardList = [...prev];
        if (!newVisitedCardList.includes(nextSectionId)) {
          newVisitedCardList.push(nextSectionId);
        }
        return newVisitedCardList;
      });

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

  const STEP_LIST = cardSectionList?.reduce(
    (acc, section, index) => {
      const isPreviousDone = index === 0 || acc.every((step) => step.isDone);
      const isMovingAvailable = isPreviousDone && visitedCardList.includes(section.sectionId);

      acc.push({
        sectionId: section.sectionId,
        sectionName: section.sectionName ?? QUESTION_CONTENTS[section.sectionId],
        isMovingAvailable,
        isDone: section.questions.every((question) => answerValidateMap?.get(question.questionId)),
        isCurrentStep: index === currentCardIndex,
        handleClick: () => {
          if (isMovingAvailable) {
            handleCurrentCardIndex(index);
          }
        },
      });

      return acc;
    },
    [] as Array<{
      sectionId: number;
      sectionName: string;
      isMovingAvailable: boolean;
      isDone: boolean;
      isCurrentStep: boolean;
      handleClick: () => void;
    }>,
  );

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
        <ProgressBar stepList={STEP_LIST} />
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
            <p>제출한 뒤에는 수정할 수 없어요</p>
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
