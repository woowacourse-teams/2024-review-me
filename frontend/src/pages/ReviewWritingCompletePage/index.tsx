import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router';

import PrimaryHomeIcon from '@/assets/primaryHome.svg';
import SmileIcon from '@/assets/smile.svg';
import { Button, ErrorSection } from '@/components';

import * as S from './styles';

const ReviewWritingCompletePage = () => {
  const [isValid, setIsValid] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    if (!location.state || !location.state.isValidAccess) setIsValid(false);
  }, [location]);

  const handleClickHomeButton = () => {
    navigate('/', { replace: true });
  };

  if (!isValid) {
    return (
      <ErrorSection
        errorMessage="유효하지 않은 접근이에요"
        handleReload={() => navigate(0)}
        handleGoOtherPage={() => navigate('/', { replace: true })}
        errorType="invalidAccess"
      />
    );
  }

  return (
    <S.Layout>
      <S.Container>
        <S.ReviewComplete>
          <img src={SmileIcon} alt="작성 완료 스마일 이미지" />
          <S.Title>리뷰 작성 완료!</S.Title>
        </S.ReviewComplete>
        <Button styleType="secondary" type="button" onClick={handleClickHomeButton}>
          <S.HomeIcon src={PrimaryHomeIcon} />
          <S.HomeText>홈으로 돌아가기</S.HomeText>
        </Button>
      </S.Container>
    </S.Layout>
  );
};

export default ReviewWritingCompletePage;
