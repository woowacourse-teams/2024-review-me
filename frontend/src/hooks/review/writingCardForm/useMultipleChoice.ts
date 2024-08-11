import { useState } from 'react';

import { ReviewWritingAnswer, ReviewWritingCardQuestion } from '@/types';

interface UseMultipleChoiceProps {
  question: ReviewWritingCardQuestion;
  updateAnswerMap: (answer: ReviewWritingAnswer) => void;
}

const useMultipleChoice = ({ question, updateAnswerMap }: UseMultipleChoiceProps) => {
  const [selectedOptionList, setSelectedOptionList] = useState<number[]>([]);
  const [isOpenLimitGuide, setIsOpenLimitGuide] = useState(false);

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { id } = event.currentTarget;
    // max를 넘어서는 선택하려 할 때
    if (isAboveSelectionLimit(Number(id))) {
      return setIsOpenLimitGuide(true);
    }
    // 유효한 객관식 문항 선택
    setIsOpenLimitGuide(false);

    const newSelectedOptionList = makeNewSelectedOptionList(event);
    setSelectedOptionList(newSelectedOptionList);
    updateAnswerMap({ questionId: question.questionId, selectedOptionIds: newSelectedOptionList, text: null });
  };
  /**
   * checkbox의 change 이벤트에 따라 새로운 selectedOptionList를 반환하는 함수
   */
  const makeNewSelectedOptionList = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { id, checked } = event.currentTarget;
    const optionId = Number(id);

    if (checked) {
      return selectedOptionList.concat(optionId);
    }
    return selectedOptionList.filter((option) => option !== optionId);
  };

  const isMaxCheckedNumber = () => {
    if (!question.optionGroup) return;
    return selectedOptionList.length >= question.optionGroup.maxCount;
  };

  /**
   * 선택 가능한 문항 수를 넘어서 문항을 선택하려 하는지 여부
   */
  const isAboveSelectionLimit = (optionId: number) => !!(isMaxCheckedNumber() && !isSelectedCheckbox(optionId));

  const isSelectedCheckbox = (optionId: number) => {
    return selectedOptionList.includes(optionId);
  };

  return {
    isOpenLimitGuide,
    handleCheckboxChange,
    isSelectedCheckbox,
  };
};
export default useMultipleChoice;
