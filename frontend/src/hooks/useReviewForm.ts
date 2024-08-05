import { useEffect, useState } from 'react';

import { getDataToWriteReviewApi } from '@/apis/review';
import { REVIEW } from '@/constants';
import { Keyword, ReviewContent, WritingReviewInfoData } from '@/types';

interface UseReviewFormProps {
  reviewRequestCode: string;
  openErrorModal: (errorMessage: string) => void;
}

const useReviewForm = ({ reviewRequestCode, openErrorModal }: UseReviewFormProps) => {
  const [dataToWrite, setDataToWrite] = useState<WritingReviewInfoData | null>(null);
  const [answers, setAnswers] = useState<ReviewContent[]>([]);
  const [selectedKeywords, setSelectedKeywords] = useState<number[]>([]);

  const isValidAnswersLength = !answers.some((id) => id.answer.length < REVIEW.answerMinLength);
  const isValidKeywordSelection =
    selectedKeywords.length >= REVIEW.keywordMinCount && selectedKeywords.length <= REVIEW.keywordMaxCount;
  const isValidForm = isValidAnswersLength && isValidKeywordSelection;

  useEffect(() => {
    const getDataToWrite = async () => {
      const data = await getDataToWriteReviewApi(reviewRequestCode);
      setDataToWrite(data);
      setAnswers(data.questions.map((question) => ({ questionId: question.id, answer: '' })));
    };

    getDataToWrite();
  }, [reviewRequestCode]);

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
    dataToWrite,
    answers,
    selectedKeywords,
    isValidForm,
    handleAnswerChange,
    handleKeywordButtonClick,
  };
};

export default useReviewForm;
