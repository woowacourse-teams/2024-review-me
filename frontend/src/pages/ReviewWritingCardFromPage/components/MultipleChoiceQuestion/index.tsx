import { CheckboxItem } from '@/components';
import { useMultipleChoice } from '@/hooks';
import useModals from '@/hooks/useModals';
import { ReviewWritingCardQuestion } from '@/types';

import * as S from './style';

interface MultipleChoiceQuestionProps {
  question: ReviewWritingCardQuestion;
}

const MODAL_KEY = {
  confirm: 'CONFIRM',
};

const MultipleChoiceQuestion = ({ question }: MultipleChoiceQuestionProps) => {
  const { isOpen, openModal, closeModal } = useModals();

  const handleModalOpen = (isOpen: boolean) => {
    isOpen ? openModal(MODAL_KEY.confirm) : closeModal(MODAL_KEY.confirm);
  };

  const { isOpenLimitGuide, handleCheckboxChange, isSelectedCheckbox, unCheckTargetOption } = useMultipleChoice({
    question,
    handleModalOpen,
  });

  const handleModalCancelButtonClick = () => {
    closeModal(MODAL_KEY.confirm);
  };

  const handleModalConfirmButtonClick = () => {
    unCheckTargetOption();
    closeModal(MODAL_KEY.confirm);
  };

  return (
    <>
      {question.optionGroup?.options.map((option) => (
        <CheckboxItem
          key={option.optionId}
          id={option.optionId.toString()}
          isChecked={isSelectedCheckbox(option.optionId)}
          disabled={isOpenLimitGuide && !isSelectedCheckbox(option.optionId)}
          label={option.content}
          handleChange={handleCheckboxChange}
        />
      ))}
      <S.LimitGuideMessage>
        {isOpenLimitGuide && <p>😅 최대 {question.optionGroup?.maxCount}개까지 선택가능해요.</p>}
      </S.LimitGuideMessage>
      {isOpen(MODAL_KEY.confirm) && (
        <div>
          <button onClick={handleModalConfirmButtonClick}>확인</button>
          <button onClick={handleModalCancelButtonClick}>취소</button>
        </div>
      )}
    </>
  );
};

export default MultipleChoiceQuestion;
