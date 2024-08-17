import { useNavigate } from 'react-router';

import DashboardIcon from '@/assets/dashboard.svg';
import { Button } from '@/components';

// TODO: 상수명 단수로 고치기
import { ROUTES } from '@/constants/routes';
import useModals from '@/hooks/useModals';

import PasswordModal from './components/PasswordModal';
import * as S from './styles';

interface ReviewDashboardPageProps {
  revieweeName: string;
  projectName: string;
}

const MODAL_KEYS = {
  content: 'CONTENT_MODAL',
};

const ReviewDashboardPage = ({ revieweeName, projectName }: ReviewDashboardPageProps) => {
  const { isOpen, openModal, closeModal } = useModals();
  const navigate = useNavigate();

  const handleReviewWritingButtonClick = () => {
    navigate(`/${ROUTES.reviewWriting}/ABCD1234`);
  };

  const handleReviewListButtonClick = () => {
    openModal(MODAL_KEYS.content);
  };

  return (
    <S.ReviewDashboardPage>
      <S.DashboardMainImg src={DashboardIcon} alt="" />
      <S.ReviewGuideContainer>
        <S.ReviewGuide>{`${projectName}를 함께한`}</S.ReviewGuide>
        <S.ReviewGuide>{`${revieweeName}의 리뷰 공간이에요`}</S.ReviewGuide>
      </S.ReviewGuideContainer>
      <S.ButtonContainer>
        <Button
          styleType="primary"
          type="button"
          onClick={handleReviewWritingButtonClick}
          style={{ width: '28rem', height: '8.5rem' }}
        >
          <S.ButtonTextContainer>
            <S.ButtonText>리뷰 쓰기</S.ButtonText>
            <S.ButtonDescription>작성한 리뷰는 익명으로 제출돼요</S.ButtonDescription>
          </S.ButtonTextContainer>
        </Button>
        <Button
          styleType="secondary"
          type="button"
          onClick={handleReviewListButtonClick}
          style={{ width: '28rem', height: '8.5rem' }}
        >
          <S.ButtonTextContainer>
            <S.ButtonText>리뷰 확인하기</S.ButtonText>
            <S.ButtonDescription>리뷰 링크가 있다면 비밀번호로 확인할 수 있어요</S.ButtonDescription>
          </S.ButtonTextContainer>
        </Button>
      </S.ButtonContainer>
      {isOpen(MODAL_KEYS.content) && <PasswordModal closeModal={() => closeModal(MODAL_KEYS.content)} />}
    </S.ReviewDashboardPage>
  );
};

export default ReviewDashboardPage;
