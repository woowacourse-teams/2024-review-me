import { useState } from 'react';

import { ReviewWritingAnswer, ReviewWritingCardQuestion } from '@/types';

const TEXT_ANSWER_LENGTH = {
  min: 20,
  max: 1000,
};

interface UseTextAnswerProps {
  question: ReviewWritingCardQuestion;
  updateAnswerMap: (answer: ReviewWritingAnswer) => void;
}
const useTextAnswer = ({ question, updateAnswerMap }: UseTextAnswerProps) => {
  const [textAnswer, setTextAnswer] = useState('');

  // NOTE: change 시 마다 상태 변경되어서, 디바운스를 적용할 지 고민...

  const handelTextAnswerChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { value } = event.target;
    const { min, max } = TEXT_ANSWER_LENGTH;
    const isValidatedText = value.length >= min && value.length <= max;
    // TODO: XSS 방어 되는 지 확인해봐야함
    if (isValidatedText) {
      setTextAnswer(value);
      updateAnswerMap({ questionId: question.questionId, selectedOptionIds: null, text: value });
    }
  };

  return {
    textAnswer,
    handelTextAnswerChange,
    TEXT_ANSWER_LENGTH,
  };
};

export default useTextAnswer;
