import { CheckboxItem, ConfirmModal } from '@/components';
import { useModals } from '@/hooks';
import { useMultipleChoice } from '@/pages/ReviewWritingPage/form/hooks';
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
        <ConfirmModal
          confirmButton={{
            styleType: 'primary',
            text: '확인',
            type: 'button',
            handleClick: handleModalConfirmButtonClick,
          }}
          cancelButton={{
            styleType: 'secondary',
            text: '취소',
            type: 'button',
            handleClick: handleModalCancelButtonClick,
          }}
          handleClose={handleModalCancelButtonClick}
          isClosableOnBackground={true}
        >
          <S.ConfirmModalContainer>
            <S.ConfirmModalTitle>잠깐만요!</S.ConfirmModalTitle>
            <p>선택을 해제하시면 앞서 작성한 답변이 모두 사라져요</p>
            <p>변경하시겠어요?</p>
          </S.ConfirmModalContainer>
        </ConfirmModal>
      )}
    </>
  );
};

export default MultipleChoiceAnswer;
