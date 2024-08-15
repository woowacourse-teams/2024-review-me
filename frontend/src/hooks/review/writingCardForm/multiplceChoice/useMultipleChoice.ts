import { useState } from 'react';

import { ReviewWritingCardQuestion } from '@/types';

import useAboveSelectionLimit from './useAboveSelectionLimit';
import useCancelAnsweredCategory from './useCancelAnsweredCategory';
import useUpdateMultipleChoiceAnswer from './useUpdateMultipleChoiceAnswer';

interface UseMultipleChoiceProps {
  question: ReviewWritingCardQuestion;
  handleModalOpen: (isOpen: boolean) => void;
}
/**
 * 하나의 객관식 질문에서 선택된 문항, 문항 선택 관리(최대를 넘는 문항 선택 시, 안내 문구 표시)등을 하는 훅
 */
const useMultipleChoice = ({ question, handleModalOpen }: UseMultipleChoiceProps) => {
  const [unCheckTargetOptionId, setUnCheckTargetOptionId] = useState<number | null>(null);

  const { isAnsweredCategoryChanged } = useCancelAnsweredCategory({ question });

  const { selectedOptionList, updateAnswerState } = useUpdateMultipleChoiceAnswer({ question });

  const { isOpenLimitGuide, isSelectedCheckbox, handleAboveSelectionLimit } = useAboveSelectionLimit({
    question,
    selectedOptionList,
  });

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { id, checked } = event.currentTarget;
    const optionId = Number(id);
    // max 판단
    handleAboveSelectionLimit(optionId);
    // 답변이 달린 카테고리를 해제하려는 경우
    const isUnCheckCategory = isAnsweredCategoryChanged(optionId);
    setUnCheckTargetOptionId(isUnCheckCategory ? optionId : null);
    handleModalOpen(isUnCheckCategory);

    if (!isUnCheckCategory) {
      updateAnswerState({ optionId, checked });
    }
  };

  const unCheckTargetOption = () => {
    if (unCheckTargetOptionId) {
      updateAnswerState({ optionId: unCheckTargetOptionId, checked: false });
    }
  };
  return {
    isOpenLimitGuide,
    handleCheckboxChange,
    isSelectedCheckbox,
    unCheckTargetOption,
  };
};
export default useMultipleChoice;
