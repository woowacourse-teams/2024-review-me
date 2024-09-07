import { useState } from 'react';

import { ReviewWritingCardQuestion } from '@/types';

interface UseAboveSelectionLimit {
  question: ReviewWritingCardQuestion;
  selectedOptionList: number[];
  isSelectedCheckbox: (optionId: number) => boolean;
}
/**
 * 객관식에서 선택가능한 최대 문항수를 넘어서 추가 선택을 시도 여부에 따라 최대 선택 개수 안내 문구를 띄워주는 역할을 담당
 * @param question
 * @param selectedOptionList 현재 선택된 문항 리스트
 */
const useAboveSelectionLimit = ({ question, selectedOptionList, isSelectedCheckbox }: UseAboveSelectionLimit) => {
  const [isOpenLimitGuide, setIsOpenLimitGuide] = useState(false);

  const isMaxCheckedNumber = () => {
    if (!question.optionGroup) return false;
    return selectedOptionList.length >= question.optionGroup.maxCount;
  };

  /**
   * 선택 가능한 문항 수를 넘어서 문항을 선택하려 하는지 여부
   */
  const isAboveSelectionLimit = (optionId: number) => !!(isMaxCheckedNumber() && !isSelectedCheckbox(optionId));

  /**
   * 최대 문항 수를 넘어서는 선택을 하려는 지 여부에 따라 최대 문항 개수 제한 안내를 띄우는지를 핸들링
   * @param id : 객관식 문항의 optionId
   */
  const handleLimitGuideOpen = (optionId: number) => {
    const isOpen = isAboveSelectionLimit(optionId);
    setIsOpenLimitGuide(isOpen);

    return isOpen;
  };

  return {
    isOpenLimitGuide,
    handleLimitGuideOpen,
  };
};

export default useAboveSelectionLimit;
