import { useRecoilValue } from 'recoil';

import { CARD_FORM_MODAL_KEY } from '@/pages/ReviewWritingPage/constants';
import {
  AnswerListRecheckModal,
  NavigateBlockerModal,
  SubmitCheckModal,
} from '@/pages/ReviewWritingPage/modals/components';
import { answerMapAtom, cardSectionListSelector } from '@/recoil';

interface CardFormModalContainerProps {
  isOpen: (key: string) => boolean;
  closeModal: (key: string) => void;
  handleNavigateConfirmButtonClick: () => void;
  submitAnswers: (event: React.MouseEvent) => Promise<void>;
}

const CardFormModalContainer = ({
  isOpen,
  closeModal,
  handleNavigateConfirmButtonClick,
  submitAnswers,
}: CardFormModalContainerProps) => {
  const answerMap = useRecoilValue(answerMapAtom);
  const cardSectionList = useRecoilValue(cardSectionListSelector);

  return (
    <>
      {isOpen(CARD_FORM_MODAL_KEY.submitConfirm) && (
        <SubmitCheckModal
          handleSubmitButtonClick={submitAnswers}
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
