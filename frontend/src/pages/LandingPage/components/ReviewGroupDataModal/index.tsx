import { AlertModal } from '@/components';
import { useGroupAccessCode } from '@/hooks';

import CopyIcon from '../../../../assets/copy.svg';

import * as S from './styles';

interface URLModalProps {
  reviewRequestCode: string;
  closeModal: () => void;
}

const ReviewGroupDataModal = ({ reviewRequestCode, closeModal }: URLModalProps) => {
  const { groupAccessCode } = useGroupAccessCode();

  return (
    <AlertModal
      closeButton={{ content: '닫기', type: 'secondary', handleClick: closeModal }}
      handleClose={null}
      isClosableOnBackground={false}
    >
      <S.ReviewGroupDataModal>
        <S.ReviewGroupDataTitle>아래 확인 코드와 요청 URL을 확인해주세요.</S.ReviewGroupDataTitle>
        <S.ReviewGroupDataContainer>
          <S.ReviewGroupDataItem>
            <S.DataName>리뷰 요청 URL</S.DataName>
            <S.Data>{reviewRequestCode}</S.Data>
            <img src={CopyIcon} alt="리뷰 요청 URL 복사하기" />
          </S.ReviewGroupDataItem>
          <S.ReviewGroupDataItem>
            <S.DataName>리뷰 확인 코드</S.DataName>
            <S.Data>{groupAccessCode}</S.Data>
            <img src={CopyIcon} alt="리뷰 확인 코드 복사하기" />
          </S.ReviewGroupDataItem>
          <S.CheckContainer>
            <S.Checkbox />
            <S.CheckMessage>URL과 코드를 다른 곳에 저장해두었어요!</S.CheckMessage>
          </S.CheckContainer>
          <S.Warning>* 창이 닫히면 코드를 다시 확인할 수 없어요!</S.Warning>
        </S.ReviewGroupDataContainer>
      </S.ReviewGroupDataModal>
    </AlertModal>
  );
};

export default ReviewGroupDataModal;
