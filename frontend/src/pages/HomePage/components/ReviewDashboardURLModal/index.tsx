import { useState } from 'react';

import { AlertModal } from '@/components';
import Checkbox from '@/components/common/Checkbox';

import { CopyTextButton } from '../index';

import * as S from './styles';
interface ReviewDashboardURLModalProps {
  reviewDashboardURL: string;
  closeModal: () => void;
}

const ReviewURLModal = ({ reviewDashboardURL, closeModal }: ReviewDashboardURLModalProps) => {
  const [isChecked, setIsChecked] = useState(false);

  const handleCheckboxClick = () => {
    setIsChecked(!isChecked);
  };

  const handleCloseButtonClick = () => {
    if (isChecked) closeModal();
  };

  return (
    <AlertModal
      closeButton={{ content: '닫기', type: isChecked ? 'secondary' : 'disabled', handleClick: handleCloseButtonClick }}
      handleClose={null}
      isClosableOnBackground={false}
    >
      <S.ReviewDashboardURLModal>
        <S.ModalTitle>아래 요청 URL을 확인해주세요</S.ModalTitle>
        <S.ReviewDashboardURLModalItem>
          <S.DataName>리뷰 요청 URL</S.DataName>
          <S.Data>{reviewDashboardURL}</S.Data>
          <CopyTextButton targetText={reviewDashboardURL} alt="리뷰 대시보드 페이지 링크 복사하기"></CopyTextButton>
        </S.ReviewDashboardURLModalItem>
        <S.CheckContainer>
          <Checkbox
            id="is-confirmed-checkbox"
            isChecked={isChecked}
            handleChange={handleCheckboxClick}
            $style={{ width: '2.3rem', height: '2.3rem' }}
          />
          <S.CheckMessage>링크를 저장해두었어요!</S.CheckMessage>
        </S.CheckContainer>
        <S.WarningMessage>* 창이 닫히면 링크를 다시 확인할 수 없어요!</S.WarningMessage>
      </S.ReviewDashboardURLModal>
    </AlertModal>
  );
};

export default ReviewURLModal;
