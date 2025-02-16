import { useNavigate } from 'react-router';

import { Button } from '@/components';
import { ROUTE } from '@/constants';

import * as S from './styles';

const UserLoggedInForm = () => {
  const navigate = useNavigate();

  const handleReviewLinkButtonClick = () => {
    navigate(`${ROUTE.reviewLinks}`);
  };

  return (
    <S.LoginForm>
      <S.Title>함께한 팀원으로부터 리뷰를 받아보세요!</S.Title>
      <S.SubTitleWrapper>
        <S.SubTitle>만든 링크는 리뷰미가 관리해드릴게요.</S.SubTitle>
        <S.SubTitle>작성한 리뷰와 받은 리뷰를 편하게 관리해 보세요.</S.SubTitle>
      </S.SubTitleWrapper>
      <Button styleType="primary" onClick={handleReviewLinkButtonClick}>
        리뷰 받아보기
      </Button>
    </S.LoginForm>
  );
};

export default UserLoggedInForm;
