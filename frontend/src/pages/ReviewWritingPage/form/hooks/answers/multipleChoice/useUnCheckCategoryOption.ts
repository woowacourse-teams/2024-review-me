import { useState } from 'react';

interface UseUnCheckCategoryOptionProps {
  handleModalOpen: (isOpen: boolean) => void;
  updateSelectedOptionList: ({ optionId, checked }: { optionId: number; checked: boolean }) => number[];
  updateAnswerState: (newSelectedOptionList: number[]) => void;
}
/**
 * 이미 답변을 작성한 강점에 대해, 강점 카테고리에서 선택을 해제하는 경우  강점 문항 optionId과 액션들을 관리하는 훅
 */
const useUnCheckCategoryOption = ({
  handleModalOpen,
  updateSelectedOptionList,
  updateAnswerState,
}: UseUnCheckCategoryOptionProps) => {
  /**
   * 선택을 해제하려는 강점 카테고리 문항의 id
   */
  const [unCheckCategoryOptionId, setUnCheckCategoryOptionId] = useState<number | null>(null);

  interface HandleCategoryUncheckModalParams {
    isUnCheckCategory: boolean;
    optionId: number;
  }

  /**
   * 답변이 달린 강점 선택 해제인지 여부에 따라 모달 오픈 여부와 unCheckCategoryOptionId 업데이트
   */
  const handleCategoryUncheckModal = ({ isUnCheckCategory, optionId }: HandleCategoryUncheckModalParams) => {
    setUnCheckCategoryOptionId(isUnCheckCategory ? optionId : null);
    handleModalOpen(isUnCheckCategory);
  };

  /**
   * 작성한 답변이 있는 강점에 대한 선택을 해제할 때 띄우는 confirm modal에서 확인 버튼 클릭 시, 강점 선택을 해제하는 기능
   */
  const unCheckTargetCategoryOption = () => {
    if (unCheckCategoryOptionId) {
      const newSelectedOptionList = updateSelectedOptionList({
        optionId: unCheckCategoryOptionId,
        checked: false,
      });
      updateAnswerState(newSelectedOptionList);
    }
  };

  return { unCheckCategoryOptionId, handleCategoryUncheckModal, unCheckTargetCategoryOption };
};

export default useUnCheckCategoryOption;
