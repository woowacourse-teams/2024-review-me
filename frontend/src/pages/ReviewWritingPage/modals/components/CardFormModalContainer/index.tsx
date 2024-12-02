import { QueryErrorResetBoundary } from '@tanstack/react-query';
import { useRecoilValue } from 'recoil';

import { ErrorBoundary } from '@/components';
import { CARD_FORM_MODAL_KEY } from '@/pages/ReviewWritingPage/constants';
import {
  AnswerListRecheckModal,
  SubmitCheckModal,
  SubmitErrorModal,
  RestoreAnswerCheckModal,
} from '@/pages/ReviewWritingPage/modals/components';
import { answerMapAtom, cardSectionListSelector } from '@/recoil';

interface CardFormModalContainerProps {
  isOpen: (key: string) => boolean;
  closeModal: (key: string) => void;
  handleRestoreButtonClick: () => void;
}

const CardFormModalContainer = ({ isOpen, closeModal, handleRestoreButtonClick }: CardFormModalContainerProps) => {
  const answerMap = useRecoilValue(answerMapAtom);
  const cardSectionList = useRecoilValue(cardSectionListSelector);

  return (
    <>
      <QueryErrorResetBoundary>
        {({ reset }) => (
          <ErrorBoundary
            fallback={(fallbackProps) => (
              <SubmitErrorModal
                closeSubmitCheckModal={() => closeModal(CARD_FORM_MODAL_KEY.submitConfirm)}
                {...fallbackProps}
              />
            )}
            resetQueryError={reset}
          >
            {isOpen(CARD_FORM_MODAL_KEY.submitConfirm) && (
              <SubmitCheckModal
                handleCancelButtonClick={() => closeModal(CARD_FORM_MODAL_KEY.submitConfirm)}
                handleCloseModal={() => closeModal(CARD_FORM_MODAL_KEY.submitConfirm)}
              />
            )}
          </ErrorBoundary>
        )}
      </QueryErrorResetBoundary>
      {isOpen(CARD_FORM_MODAL_KEY.recheck) && cardSectionList && answerMap && (
        <AnswerListRecheckModal
          questionSectionList={cardSectionList}
          answerMap={answerMap}
          closeModal={() => closeModal(CARD_FORM_MODAL_KEY.recheck)}
        />
      )}
      {isOpen(CARD_FORM_MODAL_KEY.restoreConfirm) && (
        <RestoreAnswerCheckModal
          restoreAnswer={handleRestoreButtonClick}
          closeModal={() => closeModal(CARD_FORM_MODAL_KEY.restoreConfirm)}
        ></RestoreAnswerCheckModal>
      )}
    </>
  );
};

export default CardFormModalContainer;
