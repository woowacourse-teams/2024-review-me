import { useNavigate } from 'react-router';

import PrimaryHomeIcon from '@/assets/primaryHome.svg';
import { Button } from '@/components';

import * as S from './styles';

const ReviewWritingCompletePage = () => {
  const navigate = useNavigate();

  const handleClickHomeButton = () => {
    navigate('/', { replace: true });
  };

  return (
    <S.Layout>
      <S.Container>
        <S.Title>😊 리뷰 작성을 완료했어요!</S.Title>
        <Button styleType="secondary" type="button" onClick={handleClickHomeButton}>
          <S.HomeIcon src={PrimaryHomeIcon} />
          <S.HomeText>홈으로 돌아가기</S.HomeText>
        </Button>
      </S.Container>
    </S.Layout>
  );
};

export default ReviewWritingCompletePage;
