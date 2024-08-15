import { useState } from 'react';

import { AlertModal } from '@/components';
import Checkbox from '@/components/common/Checkbox';

import { CopyTextButton } from '../index';

import * as S from './styles';
interface URLModalProps {
  reviewURL: string;
  closeModal: () => void;
}

const ReviewGroupDataModal = ({ reviewURL, closeModal }: URLModalProps) => {
  const [isChecked, setIsChecked] = useState(false);

  const handleCheckboxClick = () => {
    setIsChecked(!isChecked);
  };

  const handleCloseButtonClick = () => {
    if (isChecked) closeModal();
    return;
  };

  return (
    <AlertModal
      closeButton={{ content: '닫기', type: isChecked ? 'secondary' : 'disabled', handleClick: handleCloseButtonClick }}
      handleClose={null}
      isClosableOnBackground={false}
    >
      <S.ReviewGroupDataModal>
        <S.ReviewGroupDataTitle>아래 요청 URL을 확인해주세요</S.ReviewGroupDataTitle>
        <S.ReviewGroupDataContainer>
          <S.ReviewGroupDataItem>
            <S.DataName>리뷰 요청 URL</S.DataName>
            <S.Data>{reviewURL}</S.Data>
            <CopyTextButton targetText={reviewURL} alt="리뷰 URL 복사하기"></CopyTextButton>
          </S.ReviewGroupDataItem>
        </S.ReviewGroupDataContainer>
        <S.WarningContainer>
          <S.CheckContainer>
            <Checkbox
              id="is-confirmed-checkbox"
              isChecked={isChecked}
              handleChange={handleCheckboxClick}
              $style={{ width: '2.3rem', height: '2.3rem' }}
            />
            <S.CheckMessage>URL을 저장해두었어요!</S.CheckMessage>
          </S.CheckContainer>
          <S.Warning>* 창이 닫히면 URL을 다시 확인할 수 없어요!</S.Warning>
        </S.WarningContainer>
      </S.ReviewGroupDataModal>
    </AlertModal>
  );
};

export default ReviewGroupDataModal;
