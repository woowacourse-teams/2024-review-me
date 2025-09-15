import { ConfirmModal } from '@/components';
import { useDeleteReviewInLocalStorage } from '@/pages/ReviewWritingPage/form/hooks';

import * as S from './style';

interface SubmitCheckModalProps {
  restoreAnswer: () => void;
  closeModal: () => void;
}

const RestoreAnswerCheckModal = ({ restoreAnswer, closeModal }: SubmitCheckModalProps) => {
  const { deleteAllReviewDataInLocalStorage } = useDeleteReviewInLocalStorage();

  const handleRestoreButtonClick = () => {
    restoreAnswer();
    closeModal();
  };

  const handleDeleteButtonClick = () => {
    deleteAllReviewDataInLocalStorage();
    closeModal();
  };

  return (
    <ConfirmModal
      confirmButton={{
        styleType: 'primary',
        text: '복원',
        handleClick: handleRestoreButtonClick,
      }}
      cancelButton={{
        styleType: 'secondary',
        text: '삭제',
        handleClick: handleDeleteButtonClick,
      }}
      handleClose={null}
      isClosableOnBackground={false}
    >
      <S.RestoreAnswerCheckModal>
        <S.ConfirmModalTitle>작성했던 리뷰가 있어요</S.ConfirmModalTitle>
        <p>진행 상황을 복원할까요?</p>
        <p>삭제를 선택하면 기존에 작성했던 내용이 삭제돼요</p>
      </S.RestoreAnswerCheckModal>
    </ConfirmModal>
  );
};

export default RestoreAnswerCheckModal;
