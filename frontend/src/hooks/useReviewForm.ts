import { useState } from 'react';

import { REVIEW } from '@/constants';
import { Keyword, Question, ReviewContent, WritingReviewInfoData } from '@/types';

interface UseReviewFormProps {
  dataToWrite: WritingReviewInfoData;
  openErrorModal: (errorMessage: string) => void;
}

const useReviewForm = ({ dataToWrite, openErrorModal }: UseReviewFormProps) => {
  const [answers, setAnswers] = useState<ReviewContent[]>(
    dataToWrite.questions.map((question: Question) => ({ questionId: question.id, answer: '' })),
  );
  const [selectedKeywords, setSelectedKeywords] = useState<number[]>([]);

  const isValidAnswersLength = !answers.some((id) => id.answer.length < REVIEW.answerMinLength);
  const isValidKeywordSelection =
    selectedKeywords.length >= REVIEW.keywordMinCount && selectedKeywords.length <= REVIEW.keywordMaxCount;
  const isValidForm = isValidAnswersLength && isValidKeywordSelection;

  const handleAnswerChange = (questionId: number, value: string) => {
    setAnswers((prev) =>
      prev.map((answer) => (answer.questionId === questionId ? { ...answer, answer: value } : answer)),
    );
  };

  const handleKeywordButtonClick = (keyword: Keyword) => {
    if (selectedKeywords.length === REVIEW.keywordMaxCount && !selectedKeywords.includes(keyword.id)) {
      openErrorModal('키워드는 최대 5개까지 선택할 수 있어요.');
      return;
    }

    setSelectedKeywords((prev) =>
      prev.includes(keyword.id) ? selectedKeywords.filter((id) => id !== keyword.id) : [...prev, keyword.id],
    );
  };

  return {
    answers,
    selectedKeywords,
    isValidForm,
    handleAnswerChange,
    handleKeywordButtonClick,
  };
};

export default useReviewForm;
