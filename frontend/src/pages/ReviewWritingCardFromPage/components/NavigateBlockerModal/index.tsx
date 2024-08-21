import { ConfirmModal } from '@/components';

import * as S from './style';

interface NavigateBlockerModalProps {
  handleNavigateConfirmButtonClick: () => void;
  handleCancelButtonClick: () => void;
  handleCloseModal: () => void;
}

const NavigateBlockerModal = ({
  handleNavigateConfirmButtonClick,
  handleCancelButtonClick,
  handleCloseModal,
}: NavigateBlockerModalProps) => {
  return (
    <ConfirmModal
      confirmButton={{
        styleType: 'primary',
        type: 'submit',
        text: '이동',
        handleClick: handleNavigateConfirmButtonClick,
      }}
      cancelButton={{
        styleType: 'secondary',
        text: '취소',
        handleClick: handleCancelButtonClick,
      }}
      handleClose={handleCloseModal}
      isClosableOnBackground={true}
    >
      <S.ConfirmModalMessage>
        <p>페이지를 이동하면 작성한 답변이 삭제돼요</p>
        <p>페이지 이동을 진행할까요?</p>
      </S.ConfirmModalMessage>
    </ConfirmModal>
  );
};

export default NavigateBlockerModal;
