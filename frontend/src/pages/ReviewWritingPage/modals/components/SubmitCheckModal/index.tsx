import { ConfirmModal } from '@/components';
import { REVIEW_WRITING_EVENT_NAME } from '@/constants';
import { useSubmitAnswers } from '@/pages/ReviewWritingPage/form/hooks';
import { trackEventInAmplitude } from '@/utils';

import * as S from './style';

interface SubmitCheckModalProps {
  handleCancelButtonClick: () => void;
  handleCloseModal: () => void;
}

const SubmitCheckModal = ({ handleCancelButtonClick, handleCloseModal }: SubmitCheckModalProps) => {
  const { submitAnswers } = useSubmitAnswers({ closeSubmitConfirmModal: handleCloseModal });

  const handleConfirmButtonClick = (event: React.MouseEvent) => {
    trackEventInAmplitude(REVIEW_WRITING_EVENT_NAME.submitReview);
    submitAnswers(event);
  };

  return (
    <ConfirmModal
      confirmButton={{ styleType: 'primary', type: 'submit', text: '제출', handleClick: handleConfirmButtonClick }}
      cancelButton={{
        styleType: 'secondary',
        text: '취소',
        handleClick: handleCancelButtonClick,
      }}
      handleClose={handleCloseModal}
      isClosableOnBackground={true}
    >
      <S.Message>
        <p>리뷰를 제출할까요?</p>
        <p>제출한 뒤에는 수정할 수 없어요</p>
      </S.Message>
    </ConfirmModal>
  );
};

export default SubmitCheckModal;
