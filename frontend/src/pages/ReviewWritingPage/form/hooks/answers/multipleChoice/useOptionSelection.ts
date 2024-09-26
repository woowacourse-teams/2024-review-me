import { useState } from 'react';

/**
 * 객관식 문항에서 선택된 문항들의 id들을 관리
 */
const useOptionSelection = () => {
  const [selectedOptionList, setSelectedOptionList] = useState<number[]>([]);

  const isSelectedCheckbox = (optionId: number) => {
    return selectedOptionList.includes(optionId);
  };

  interface MakeNewSelectedOptionListParams {
    optionId: number;
    checked: boolean;
  }

  /**
   * checkbox의 change 이벤트에 따라 새로운 selectedOptionList를 반환하는 함수
   */
  const makeNewSelectedOptionList = ({ optionId, checked }: MakeNewSelectedOptionListParams) => {
    if (checked) {
      return selectedOptionList.concat(optionId);
    }

    return selectedOptionList.filter((option) => option !== optionId);
  };

  const updateSelectedOptionList = ({ optionId, checked }: MakeNewSelectedOptionListParams) => {
    const newSelectedOptionList = makeNewSelectedOptionList({ optionId, checked });
    setSelectedOptionList(newSelectedOptionList);

    return newSelectedOptionList;
  };

  return {
    selectedOptionList,
    isSelectedCheckbox,
    updateSelectedOptionList,
  };
};

export default useOptionSelection;
