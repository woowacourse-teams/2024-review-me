import { useState } from 'react';

import { ReviewWritingCardQuestion } from '@/types';

import useAboveSelectionLimit from './useAboveSelectionLimit';
import useCheckTailQuestionAnswer from './useCheckTailQuestionAnswer';
import useUpdateMultipleChoiceAnswer from './useUpdateMultipleChoiceAnswer';

interface UseMultipleChoiceProps {
  question: ReviewWritingCardQuestion;
  handleModalOpen: (isOpen: boolean) => void;
}
/**
 * 하나의 객관식 질문에서 선택된 문항, 문항 선택 관리(최대를 넘는 문항 선택 시, 안내 문구 표시)등을 하는 훅
 */
const useMultipleChoice = ({ question, handleModalOpen }: UseMultipleChoiceProps) => {
  const [unCheckTargetCategoryOptionId, setUnCheckTargetCategoryOptionId] = useState<number | null>(null);

  const { isAnsweredTailQuestion, updateVisitedCardList } = useCheckTailQuestionAnswer({ question });

  const { selectedOptionList, updateAnswerState } = useUpdateMultipleChoiceAnswer({ question });

  const { isOpenLimitGuide, isSelectedCheckbox, isAboveSelectionLimit, handleLimitGuideOpen } = useAboveSelectionLimit({
    question,
    selectedOptionList,
  });

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { id, checked } = event.currentTarget;
    const optionId = Number(id);

    if (isAboveSelectionLimit(optionId)) {
      return handleLimitGuideOpen(true);
    }
    handleLimitGuideOpen(false);
    // 답변이 달린 카테고리를 해제하려는 경우
    const isUnCheckCategory = isAnsweredCategoryChanged(optionId);
    setUnCheckTargetCategoryOptionId(isUnCheckCategory ? optionId : null);
    handleModalOpen(!!isUnCheckCategory);

    if (!isUnCheckCategory) {
      updateAnswerState({ optionId, checked });
    }
  };

  const unCheckTargetCategoryOption = () => {
    if (unCheckTargetCategoryOptionId) {
      updateAnswerState({ optionId: unCheckTargetCategoryOptionId, checked: false });
    }
  };
  return {
    isOpenLimitGuide,
    handleCheckboxChange,
    isSelectedCheckbox,
    unCheckTargetCategoryOption,
    updateVisitedCardList,
    unCheckTargetCategoryOptionId,
  };
};
export default useMultipleChoice;
