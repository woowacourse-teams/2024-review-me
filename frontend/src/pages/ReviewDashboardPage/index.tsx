import DashboardIcon from '@/assets/dashboard.svg';
import { Button } from '@/components';
import useModals from '@/hooks/useModals';

import PasswordModal from './components/PasswordModal';
import * as S from './styles';

interface ReviewDashboardPageProps {
  revieweeName: string;
}

const MODAL_KEYS = {
  content: 'CONTENT_MODAL',
};

const ReviewDashboardPage = ({ revieweeName }: ReviewDashboardPageProps) => {
  const { isOpen, openModal, closeModal } = useModals();

  const handleReviewWritingButtonClick = () => {};
  const handleReviewListButtonClick = () => {
    openModal(MODAL_KEYS.content);
    //openModal();
  };
  return (
    <S.ReviewDashboardPage>
      <S.DashboardMainImg src={DashboardIcon} alt="" />
      <S.RevieweeGuide>{`${revieweeName}의 리뷰 방이에요`}</S.RevieweeGuide>
      <S.ButtonContainer>
        <Button
          styleType="primary"
          type="button"
          onClick={handleReviewWritingButtonClick}
          style={{ width: '25rem', height: '6.3rem' }}
        >
          리뷰 쓰기
        </Button>
        <Button
          styleType="secondary"
          type="button"
          onClick={handleReviewListButtonClick}
          style={{ width: '25rem', height: '6.3rem' }}
        >
          리뷰 확인하기
        </Button>
      </S.ButtonContainer>
      {isOpen(MODAL_KEYS.content) && <PasswordModal closeModal={() => closeModal(MODAL_KEYS.content)} />}
    </S.ReviewDashboardPage>
  );
};

export default ReviewDashboardPage;
