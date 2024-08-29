import { ReviewWritingCardQuestion } from '@/types';

import useAboveSelectionLimit from './useAboveSelectionLimit';
import useCheckTailQuestionAnswer from './useCheckTailQuestionAnswer';
import useOptionSelection from './useOptionSelection';
import useUnCheckCategoryOption from './useUnCheckCategoryOption';
import useUpdateMultipleChoiceAnswer from './useUpdateMultipleChoiceAnswer';

interface UseMultipleChoiceProps {
  question: ReviewWritingCardQuestion;
  handleModalOpen: (isOpen: boolean) => void;
}
/**
 * 하나의 객관식 질문에서 선택된 문항, 문항 선택 관리(최대를 넘는 문항 선택 시, 안내 문구 표시)등을 하는 훅
 */
const useMultipleChoice = ({ question, handleModalOpen }: UseMultipleChoiceProps) => {
  const { isAnsweredTailQuestion, updateVisitedCardList } = useCheckTailQuestionAnswer({ question });

  const { selectedOptionList, isSelectedCheckbox, updateSelectedOptionList } = useOptionSelection();

  const { updateAnswerState } = useUpdateMultipleChoiceAnswer({ question });

  const { isOpenLimitGuide, handleLimitGuideOpen } = useAboveSelectionLimit({
    question,
    selectedOptionList,
    isSelectedCheckbox,
  });

  const { unCheckCategoryOptionId, handleCategoryUncheckModal, unCheckTargetCategoryOption } = useUnCheckCategoryOption(
    {
      handleModalOpen,
      updateSelectedOptionList,
      updateAnswerState,
    },
  );

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { id, checked } = event.currentTarget;
    const optionId = Number(id);

    if (checked) return handleCheckOption(optionId);

    handleUnCheckOption(optionId);
  };

  const handleCheckOption = (optionId: number) => {
    // 최대 문항 개수을 넘는 추가 선택 여부인지 판단해 안내 문구를 띄우거나 지움
    const isOpen = handleLimitGuideOpen(optionId);
    if (isOpen) return;

    updateOptionChoice({ optionId, checked: true });
  };

  const handleUnCheckOption = (optionId: number) => {
    // 최대 문항 개수 선택 안내 문구 해제
    handleLimitGuideOpen(optionId);
    /**
     * 답변이 달린 카테고리를 해제하려는 경우
     */
    const isUnCheckCategory = !!isAnsweredTailQuestion(optionId);

    // 답변이 달린 카테고리를 해제하려는 지 여부에 따른  액션
    // 1. 모달
    handleCategoryUncheckModal({ isUnCheckCategory, optionId });
    // 2. 선택된 문항과 답변 업데이트
    if (!isUnCheckCategory) {
      updateOptionChoice({ optionId, checked: false });
    }
  };

  interface UpdateOptionChoiceParams {
    optionId: number;
    checked: boolean;
  }
  /**
   * change 이벤트가 일어난 객관식 문항의 선택/해제에 따라 상태를 업데이트
   */
  const updateOptionChoice = ({ optionId, checked }: UpdateOptionChoiceParams) => {
    const newSelectedOptionList = updateSelectedOptionList({ optionId, checked });
    updateAnswerState(newSelectedOptionList);
  };

  return {
    isOpenLimitGuide,
    handleCheckboxChange,
    isSelectedCheckbox,
    unCheckTargetCategoryOption,
    updateVisitedCardList,
    unCheckCategoryOptionId,
  };
};
export default useMultipleChoice;
