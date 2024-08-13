import { useState } from 'react';

import { ReviewWritingAnswer, ReviewWritingCardQuestion } from '@/types';

const TEXT_ANSWER_LENGTH = {
  min: 20,
  max: 1000,
};

interface UseTextAnswerProps {
  question: ReviewWritingCardQuestion;
  updateAnswerMap: (answer: ReviewWritingAnswer) => void;
  updateAnswerValidationMap: (answer: ReviewWritingAnswer, isValidatedAnswer: boolean) => void;
}
const useTextAnswer = ({ question, updateAnswerMap, updateAnswerValidationMap }: UseTextAnswerProps) => {
  const [textAnswer, setTextAnswer] = useState('');

  // NOTE: change 시 마다 상태 변경되어서, 디바운스를 적용할 지 고민...

  const handleTextAnswerChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { value } = event.target;
    const { min, max } = TEXT_ANSWER_LENGTH;
    const isValidatedText = value.length >= min && value.length <= max;
    setTextAnswer(value);
    const isNotRequiredEmptyAnswer = !question.required && value === '';
    const newAnswer: ReviewWritingAnswer = {
      questionId: question.questionId,
      selectedOptionIds: null,
      text: isValidatedText ? value : '',
    };
    updateAnswerMap(newAnswer);
    updateAnswerValidationMap(newAnswer, isValidatedText || isNotRequiredEmptyAnswer);
  };

  return {
    textAnswer,
    handleTextAnswerChange,
    TEXT_ANSWER_LENGTH,
  };
};

export default useTextAnswer;
