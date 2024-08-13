import { useEffect, useState } from 'react';

import { ReviewWritingAnswer, ReviewWritingCardSection } from '@/types';

interface UseReviewerAnswerProps {
  currentCardIndex: number;
  questionList: ReviewWritingCardSection[] | null;
  updatedSelectedCategory: (newSelectedCategory: number[]) => void;
}

const useReviewerAnswer = ({ currentCardIndex, questionList, updatedSelectedCategory }: UseReviewerAnswerProps) => {
  const [answerMap, setAnswerMap] = useState<Map<number, ReviewWritingAnswer>>();
  const [answerValidationMap, SetAnswerValidationMap] = useState<Map<number, boolean>>();

  useEffect(() => {
    const newAnswerMap: Map<number, ReviewWritingAnswer> = new Map();
    const newAnswerValidationMap: Map<number, boolean> = new Map();
    questionList?.forEach((section) => {
      section.questions.forEach((question) => {
        const answer = answerMap?.get(question.questionId);
        newAnswerMap.set(question.questionId, {
          questionId: question.questionId,
          selectedOptionIds: question.questionType === 'CHECKBOX' ? (answer?.selectedOptionIds ?? []) : null,
          text: question.questionType === 'TEXT' ? (answer?.text ?? '') : null,
        });
        newAnswerValidationMap.set(
          question.questionId,
          answerValidationMap?.get(question.questionId) ?? !question.required,
        );
      });
    });
    setAnswerMap(newAnswerMap);
    SetAnswerValidationMap(newAnswerValidationMap);
  }, [questionList]);

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

  const updateAnswerValidationMap = (answer: ReviewWritingAnswer, isValidatedAnswer: boolean) => {
    const newAnswerValidationMap = new Map(answerValidationMap);
    newAnswerValidationMap.set(answer.questionId, isValidatedAnswer);
    SetAnswerValidationMap(newAnswerValidationMap);
  };

  const isValidateAnswerList = () => {
    if (!questionList) return false;

    return questionList[currentCardIndex].questions.every((question) => {
      const { questionId, required } = question;
      const answerValidation = answerValidationMap?.get(questionId);

      if (!required && answerValidation) return true;
      return !!answerValidation;
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
    updateAnswerValidationMap,
  };
};

export default useReviewerAnswer;
