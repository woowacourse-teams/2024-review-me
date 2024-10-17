import { ErrorAlertModal } from '@/components';
import { ErrorAlertModalCloseButton } from '@/components/common/modals/ErrorAlertModal';
import { FallbackProps } from '@/components/error/ErrorBoundary';

import * as S from './style';

interface SubmitErrorModalProps extends FallbackProps {
  closeSubmitCheckModal: () => void;
}

const SubmitErrorModal = ({ resetErrorBoundary, closeSubmitCheckModal }: SubmitErrorModalProps) => {
  const handleClose = () => {
    closeSubmitCheckModal();
    resetErrorBoundary();
  };

  const closeButton: ErrorAlertModalCloseButton = {
    type: 'primary',
    handleClick: handleClose,
    content: '닫기',
  };

  return (
    <ErrorAlertModal handleClose={handleClose} closeButton={closeButton}>
      <S.Message>
        <p>리뷰 제출에 실패했어요</p>
        <p>다시 시도해주세요</p>
      </S.Message>
    </ErrorAlertModal>
  );
};

export default SubmitErrorModal;
