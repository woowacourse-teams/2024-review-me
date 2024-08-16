import { useState } from 'react';

import { ReviewWritingAnswer, ReviewWritingCardQuestion } from '@/types';

import useUpdateReviewerAnswer from '../useUpdateReviewerAnswer';

interface UseUpdateMultipleChoiceAnswerProps {
  question: ReviewWritingCardQuestion;
}

const useUpdateMultipleChoiceAnswer = ({ question }: UseUpdateMultipleChoiceAnswerProps) => {
  const [selectedOptionList, setSelectedOptionList] = useState<number[]>([]);

  const { updateAnswerMap, updateAnswerValidationMap } = useUpdateReviewerAnswer();

  interface MakeNewSelectedOptionList {
    optionId: number;
    checked: boolean;
  }
  /**
   * checkbox의 change 이벤트에 따라 새로운 selectedOptionList를 반환하는 함수
   */
  const makeNewSelectedOptionList = ({ optionId, checked }: MakeNewSelectedOptionList) => {
    if (checked) {
      return selectedOptionList.concat(optionId);
    }

    return selectedOptionList.filter((option) => option !== optionId);
  };

  const isValidatedChoice = (newSelectedOptionList: number[]) => {
    if (!question.optionGroup) return false;

    const { minCount, maxCount } = question.optionGroup;
    const { length } = newSelectedOptionList;

    return length >= minCount && length <= maxCount;
  };

  const updateAnswerState = ({ optionId, checked }: MakeNewSelectedOptionList) => {
    const newSelectedOptionList = makeNewSelectedOptionList({ optionId, checked });
    setSelectedOptionList(newSelectedOptionList);

    // 유효한 선택(=객관식 문항의 최소,최대 개수를 지켰을 경우)인지에 따라 answer 변경
    const isValidatedAnswer = isValidatedChoice(newSelectedOptionList);
    const isNotRequiredEmptyAnswer = !question.required && newSelectedOptionList.length === 0;

    const newAnswer: ReviewWritingAnswer = {
      questionId: question.questionId,
      selectedOptionIds: isValidatedAnswer ? newSelectedOptionList : [],
      text: null,
    };

    updateAnswerMap(newAnswer);
    updateAnswerValidationMap(newAnswer, isValidatedAnswer || isNotRequiredEmptyAnswer);
  };

  return {
    selectedOptionList,
    updateAnswerState,
  };
};

export default useUpdateMultipleChoiceAnswer;
