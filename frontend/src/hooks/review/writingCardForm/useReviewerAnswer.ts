import { useEffect, useState } from 'react';

import { ReviewWritingAnswer, ReviewWritingCardSection } from '@/types';

interface UseReviewerAnswerProps {
  currentCardIndex: number;
  questionList: ReviewWritingCardSection[] | null;
  updatedSelectedCategory: (newSelectedCategory: number[]) => void;
}

const useReviewerAnswer = ({ currentCardIndex, questionList, updatedSelectedCategory }: UseReviewerAnswerProps) => {
  const [answerMap, setAnswerMap] = useState<Map<number, ReviewWritingAnswer>>();
  const [isAbleNextStep, setIsAbleNextStep] = useState(false);

  const isCategoryAnswer = (answer: ReviewWritingAnswer) =>
    answer.questionId === questionList?.[0].questions[0].questionId;

  const updateAnswerMap = (answer: ReviewWritingAnswer) => {
    const newAnswerMap = new Map(answerMap);
    newAnswerMap.set(answer.questionId, answer);
    setAnswerMap(newAnswerMap);

    if (isCategoryAnswer(answer)) {
      updatedSelectedCategory(answer.selectedOptionIds ?? []);
    }
  };

  const isValidateAnswerList = () => {
    if (!questionList) return false;

    return questionList[currentCardIndex].questions.every((question) => {
      const { questionId, optionGroup, required } = question;
      // case1. 필수가 아닌 답변
      if (!required) return true;
      // case2. 필수이 답변
      // 2-1 답변 없음
      if (!answerMap) return false;
      const answer = answerMap.get(questionId);
      if (!answer) return false;
      // 2-2 답변이 있음 (세부적인 것을 확인)
      // 전제 조건: 유효한 답변인 경우에만 답변 값이 있고, 그렇지 않으면 답변값이 없음
      // 2-2-1.객관식 인 경우 선택된 문항의 개수의 유효성 검사
      if (optionGroup) return !!answer.selectedOptionIds?.length;
      // 2-2-2. 서술형
      return !!answer.text?.length;
    });
  };

  useEffect(() => {
    const answerListValidation = isValidateAnswerList();
    setIsAbleNextStep(answerListValidation);
  }, [answerMap, currentCardIndex]);

  return {
    answerMap,
    isAbleNextStep,
    updateAnswerMap,
  };
};

export default useReviewerAnswer;
