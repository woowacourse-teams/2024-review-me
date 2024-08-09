import { useState } from 'react';

import { AlertModal } from '@/components';
import Checkbox from '@/components/common/Checkbox';
import { useGroupAccessCode } from '@/hooks';

import { CopyTextButton } from '../index';

import * as S from './styles';
interface URLModalProps {
  reviewRequestCode: string;
  closeModal: () => void;
}

const ReviewGroupDataModal = ({ reviewRequestCode, closeModal }: URLModalProps) => {
  const [isChecked, setIsChecked] = useState(false);
  const { groupAccessCode } = useGroupAccessCode();

  const handleCheckboxClick = () => {
    setIsChecked(!isChecked);
  };

  const handleCloseButtonClick = () => {
    if (isChecked) closeModal();
    return;
  };

  return (
    <AlertModal
      closeButton={{ content: '닫기', type: 'secondary', handleClick: handleCloseButtonClick }}
      handleClose={null}
      isClosableOnBackground={false}
    >
      <S.ReviewGroupDataModal>
        <S.ReviewGroupDataTitle>아래 확인 코드와 요청 URL을 확인해주세요.</S.ReviewGroupDataTitle>
        <S.ReviewGroupDataContainer>
          <S.ReviewGroupDataItem>
            <S.DataName>리뷰 요청 URL</S.DataName>
            <S.Data>{reviewRequestCode}</S.Data>
            <CopyTextButton targetText={reviewRequestCode} alt="리뷰 요청 URL 복사하기"></CopyTextButton>
          </S.ReviewGroupDataItem>
          <S.ReviewGroupDataItem>
            <S.DataName>리뷰 확인 코드</S.DataName>
            <S.Data>{groupAccessCode}</S.Data>
            <CopyTextButton targetText={groupAccessCode ?? ''} alt="리뷰 확인 코드 복사하기"></CopyTextButton>
          </S.ReviewGroupDataItem>
        </S.ReviewGroupDataContainer>
        <S.WarningContainer>
          <S.CheckContainer>
            <Checkbox
              id="is-confirmed-checkbox"
              isChecked={isChecked}
              onChange={handleCheckboxClick}
              $style={{ width: '2.3rem', height: '2.3rem' }}
            />
            <S.CheckMessage>URL과 코드를 다른 곳에 저장해두었어요!</S.CheckMessage>
          </S.CheckContainer>
          <S.Warning>* 창이 닫히면 코드를 다시 확인할 수 없어요!</S.Warning>
        </S.WarningContainer>
      </S.ReviewGroupDataModal>
    </AlertModal>
  );
};

export default ReviewGroupDataModal;
