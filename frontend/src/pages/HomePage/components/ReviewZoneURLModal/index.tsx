import { useState } from 'react';

import { AlertModal } from '@/components';
import Checkbox from '@/components/common/Checkbox';

import { CopyTextButton } from '../index';

import * as S from './styles';
interface ReviewZoneURLModalProps {
  reviewZoneURL: string;
  closeModal: () => void;
}

const ReviewZoneURLModal = ({ reviewZoneURL, closeModal }: ReviewZoneURLModalProps) => {
  const [isChecked, setIsChecked] = useState(false);

  const handleCheckboxClick = () => {
    setIsChecked(!isChecked);
  };

  const handleCloseButtonClick = () => {
    if (isChecked) closeModal();
  };

  return (
    <AlertModal
      closeButton={{ content: '닫기', type: isChecked ? 'primary' : 'disabled', handleClick: handleCloseButtonClick }}
      handleClose={null}
      isClosableOnBackground={false}
    >
      <S.ReviewZoneURLModal>
        <S.ModalTitle>아래 요청 URL을 확인해주세요</S.ModalTitle>
        <S.ReviewZoneURLModalItem>
          <S.RequestURLContainer>
            <S.DataName>리뷰 요청 URL</S.DataName>
            <CopyTextButton targetText={reviewZoneURL} alt="리뷰 공간 페이지 링크 복사하기" />
          </S.RequestURLContainer>
          <S.Data>{reviewZoneURL}</S.Data>
        </S.ReviewZoneURLModalItem>
        <S.CheckContainer>
          <Checkbox
            id="is-confirmed-checkbox"
            isChecked={isChecked}
            handleChange={handleCheckboxClick}
            $style={{
              width: '2.7rem',
              height: '2.7rem',
            }}
          />
          <S.CheckMessage>링크를 저장해두었어요!</S.CheckMessage>
        </S.CheckContainer>
        <S.WarningMessage>* 창이 닫히면 링크를 다시 확인할 수 없어요!</S.WarningMessage>
      </S.ReviewZoneURLModal>
    </AlertModal>
  );
};

export default ReviewZoneURLModal;
