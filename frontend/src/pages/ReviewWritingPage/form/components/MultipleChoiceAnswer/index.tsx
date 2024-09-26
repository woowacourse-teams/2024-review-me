import { CheckboxItem } from '@/components';
import { useModals } from '@/hooks';
import { useMultipleChoice } from '@/pages/ReviewWritingPage/form/hooks';
import { StrengthUnCheckModal } from '@/pages/ReviewWritingPage/modals/components';
import { ReviewWritingCardQuestion } from '@/types';

import * as S from './style';

interface MultipleChoiceAnswerProps {
  question: ReviewWritingCardQuestion;
}

const MODAL_KEY = {
  confirm: 'CONFIRM',
};

const MultipleChoiceAnswer = ({ question }: MultipleChoiceAnswerProps) => {
  const { isOpen, openModal, closeModal } = useModals();

  const handleModalOpen = (isOpen: boolean) => {
    isOpen ? openModal(MODAL_KEY.confirm) : closeModal(MODAL_KEY.confirm);
  };

  const { isOpenLimitGuide, handleCheckboxChange, isSelectedCheckbox, unCheckTargetCategoryOption } = useMultipleChoice(
    {
      question,
      handleModalOpen,
    },
  );

  const handleModalCancelButtonClick = () => {
    closeModal(MODAL_KEY.confirm);
  };

  const handleModalConfirmButtonClick = () => {
    unCheckTargetCategoryOption();
    closeModal(MODAL_KEY.confirm);
  };

  return (
    <>
      {question.optionGroup?.options.map((option) => (
        <CheckboxItem
          key={option.optionId}
          id={option.optionId.toString()}
          isChecked={isSelectedCheckbox(option.optionId)}
          isDisabled={isOpenLimitGuide && !isSelectedCheckbox(option.optionId)}
          label={option.content}
          handleChange={handleCheckboxChange}
        />
      ))}
      <S.LimitGuideMessage>
        {isOpenLimitGuide && (
          <p data-testid="limitGuideMessage">😅 최대 {question.optionGroup?.maxCount}개까지 선택가능해요</p>
        )}
      </S.LimitGuideMessage>
      {isOpen(MODAL_KEY.confirm) && (
        <StrengthUnCheckModal
          handleModalCancelButtonClick={handleModalCancelButtonClick}
          handleModalConfirmButtonClick={handleModalConfirmButtonClick}
        />
      )}
    </>
  );
};

export default MultipleChoiceAnswer;
