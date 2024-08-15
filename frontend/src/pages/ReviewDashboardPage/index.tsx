import { Button } from '@/components';
import ContentModal from '@/components/common/modals/ContentModal'; // 경로?
import useModals from '@/hooks/useModals';

import * as S from './styles';

interface ReviewDashboardPageProps {
  revieweeName: string;
}

const MODAL_KEYS = {
  contentModal: 'CONTENT_MODAL',
};

const ReviewDashboardPage = ({ revieweeName }: ReviewDashboardPageProps) => {
  const { openModal, closeModal } = useModals();

  const handleReviewWritingButtonClick = () => {};
  const handleReviewListButtonClick = () => {};
  //openModal();
  return (
    <S.ReviewDashboardPage>
      <S.RevieweeGuide>{`${revieweeName}의 리뷰 방이에요`}</S.RevieweeGuide>
      <S.ButtonContainer>
        <Button styleType="primary" type="button" onClick={handleReviewWritingButtonClick}>
          리뷰 쓰기
        </Button>
        <Button styleType="secondary" type="button" onClick={handleReviewWritingButtonClick}>
          리뷰 확인하기
        </Button>
      </S.ButtonContainer>
    </S.ReviewDashboardPage>
  );
};

export default ReviewDashboardPage;
