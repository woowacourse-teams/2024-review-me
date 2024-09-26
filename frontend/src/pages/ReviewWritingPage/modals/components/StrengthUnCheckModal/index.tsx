import { ConfirmModal } from '@/components';

import * as S from './style';

interface StrengthUnCheckModalProps {
  handleModalConfirmButtonClick: () => void;
  handleModalCancelButtonClick: () => void;
}
const StrengthUnCheckModal = ({
  handleModalCancelButtonClick,
  handleModalConfirmButtonClick,
}: StrengthUnCheckModalProps) => {
  return (
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
      <S.Contents>
        <S.ConfirmModalTitle>잠깐만요!</S.ConfirmModalTitle>
        <p>선택을 해제하시면 앞서 작성한 답변이 모두 사라져요</p>
        <p>변경하시겠어요?</p>
      </S.Contents>
    </ConfirmModal>
  );
};

export default StrengthUnCheckModal;
