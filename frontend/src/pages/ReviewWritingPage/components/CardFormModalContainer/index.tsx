import { useRecoilValue } from 'recoil';

import { answerMapAtom, cardSectionListSelector } from '@/recoil';

import { CARD_FORM_MODAL_KEY } from '../CardForm';

import { AnswerListRecheckModal, NavigateBlockerModal, SubmitCheckModal } from './components';

interface CardFormModalContainerProps {
  isOpen: (key: string) => boolean;
  closeModal: (key: string) => void;
  handleNavigateConfirmButtonClick: () => void;
  submitAnswer: (event: React.MouseEvent) => Promise<void>;
}

const CardFormModalContainer = ({
  isOpen,
  closeModal,
  handleNavigateConfirmButtonClick,
  submitAnswer,
}: CardFormModalContainerProps) => {
  const answerMap = useRecoilValue(answerMapAtom);
  const cardSectionList = useRecoilValue(cardSectionListSelector);

  return (
    <>
      {isOpen(CARD_FORM_MODAL_KEY.submitConfirm) && (
        <SubmitCheckModal
          handleSubmitButtonClick={submitAnswer}
          handleCancelButtonClick={() => closeModal(CARD_FORM_MODAL_KEY.submitConfirm)}
          handleCloseModal={() => closeModal(CARD_FORM_MODAL_KEY.submitConfirm)}
        />
      )}
      {isOpen(CARD_FORM_MODAL_KEY.recheck) && cardSectionList && answerMap && (
        <AnswerListRecheckModal
          questionSectionList={cardSectionList}
          answerMap={answerMap}
          closeModal={() => closeModal(CARD_FORM_MODAL_KEY.recheck)}
        />
      )}

      {isOpen(CARD_FORM_MODAL_KEY.navigateConfirm) && (
        <NavigateBlockerModal
          handleNavigateConfirmButtonClick={handleNavigateConfirmButtonClick}
          handleCancelButtonClick={() => closeModal(CARD_FORM_MODAL_KEY.navigateConfirm)}
          handleCloseModal={() => closeModal(CARD_FORM_MODAL_KEY.navigateConfirm)}
        />
      )}
    </>
  );
};

export default CardFormModalContainer;
