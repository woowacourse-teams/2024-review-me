import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';

import { answerMapAtom, answerValidationMapAtom, cardSectionListSelector, selectedCategoryAtom } from '@/recoil';
import { ReviewWritingAnswer } from '@/types';

/**
 * 리뷰어가 작성한 답변에 따라 answerMap ,answerValidationMap의 상태를 변경하는 핸들러는 반환하는 훅
 */
const useUpdateReviewerAnswer = () => {
  const cardSectionList = useRecoilValue(cardSectionListSelector);
  const setSelectedCategory = useSetRecoilState(selectedCategoryAtom);

  const [answerMap, setAnswerMap] = useRecoilState(answerMapAtom);
  const [answerValidationMap, setAnswerValidationMap] = useRecoilState(answerValidationMapAtom);

  const isCategoryAnswer = (answer: ReviewWritingAnswer) =>
    answer.questionId === cardSectionList?.[0].questions[0].questionId;

  /**
   * 생성,수정,삭제된 답변을 answerMap에 반영하는 함수
   * @param answer : 반영한 답변
   */
  const updateAnswerMap = (answer: ReviewWritingAnswer) => {
    const newAnswerMap = new Map(answerMap);
    newAnswerMap.set(answer.questionId, answer);
    setAnswerMap(newAnswerMap);

    if (isCategoryAnswer(answer)) {
      setSelectedCategory(answer.selectedOptionIds ?? []);
    }
  };
  /**
   * 변경된 답변에 따라 답변의 유효성 여부를 판단해 answerValidationMap에 반영하는 함수
   * @param answer : 변경된 답변
   * @param isValidatedAnswer: 변경된 답변의 유효성
   */
  const updateAnswerValidationMap = (answer: ReviewWritingAnswer, isValidatedAnswer: boolean) => {
    const newAnswerValidationMap = new Map(answerValidationMap);
    newAnswerValidationMap.set(answer.questionId, isValidatedAnswer);
    setAnswerValidationMap(newAnswerValidationMap);
  };

  return {
    updateAnswerMap,
    updateAnswerValidationMap,
  };
};

export default useUpdateReviewerAnswer;
