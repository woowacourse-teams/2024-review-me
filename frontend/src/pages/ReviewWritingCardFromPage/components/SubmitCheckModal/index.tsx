import { ConfirmModal } from '@/components';

import * as S from './style';

interface SubmitCheckModalProps {
  handleSubmitButtonClick: (event: React.MouseEvent) => Promise<void>;
  handleCancelButtonClick: () => void;
  handleCloseModal: () => void;
}

const SubmitCheckModal = ({
  handleSubmitButtonClick,
  handleCancelButtonClick,
  handleCloseModal,
}: SubmitCheckModalProps) => {
  return (
    <ConfirmModal
      confirmButton={{ styleType: 'primary', type: 'submit', text: '제출', handleClick: handleSubmitButtonClick }}
      cancelButton={{
        styleType: 'secondary',
        text: '취소',
        handleClick: () => handleCancelButtonClick,
      }}
      handleClose={handleCloseModal}
      isClosableOnBackground={true}
    >
      <S.ConfirmModalMessage>
        <p>리뷰를 제출할까요?</p>
        <p>제출한 뒤에는 수정할 수 없어요</p>
      </S.ConfirmModalMessage>
    </ConfirmModal>
  );
};

export default SubmitCheckModal;
