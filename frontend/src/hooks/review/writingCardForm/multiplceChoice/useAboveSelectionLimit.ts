import { useState } from 'react';

import { ReviewWritingCardQuestion } from '@/types';

interface UseAboveSelectionLimit {
  question: ReviewWritingCardQuestion;
  selectedOptionList: number[];
}
const useAboveSelectionLimit = ({ question, selectedOptionList }: UseAboveSelectionLimit) => {
  const [isOpenLimitGuide, setIsOpenLimitGuide] = useState(false);

  const isMaxCheckedNumber = () => {
    if (!question.optionGroup) return false;
    return selectedOptionList.length >= question.optionGroup.maxCount;
  };

  const isSelectedCheckbox = (optionId: number) => {
    return selectedOptionList.includes(optionId);
  };

  /**
   * 선택 가능한 문항 수를 넘어서 문항을 선택하려 하는지 여부
   */
  const isAboveSelectionLimit = (optionId: number) => !!(isMaxCheckedNumber() && !isSelectedCheckbox(optionId));

  /**
   * 최대 문항 수를 넘어서 선택하려는지, 그럴 경우에 대한 핸들링
   * @param id : 객관식 문항의 optionId
   */
  const handleAboveSelectionLimit = (id: number) => {
    // max를 넘어서는 선택하려 할 때
    if (isAboveSelectionLimit(Number(id))) {
      return setIsOpenLimitGuide(true);
    }
    // max를 넘어서는 선택을 하지 않은 경우
    setIsOpenLimitGuide(false);
  };

  return {
    isOpenLimitGuide,
    isSelectedCheckbox,
    handleAboveSelectionLimit,
  };
};

export default useAboveSelectionLimit;
