import { useNavigate } from 'react-router';

import { Button } from '@/components';
import { ROUTE } from '@/constants';

import { ContentLayout } from '../layouts';

const ReviewLinkPrompt = () => {
  const navigate = useNavigate();

  const handleReviewLinkButtonClick = () => {
    navigate(`${ROUTE.reviewLinks}`);
  };

  return (
    <ContentLayout
      title="함께한 팀원으로부터 리뷰를 받아보세요!"
      subTitleList={['만든 링크는 리뷰미가 관리해드릴게요.', '작성한 리뷰와 받은 리뷰를 편하게 관리해보세요.']}
    >
      <Button styleType="primary" onClick={handleReviewLinkButtonClick}>
        리뷰 받아보기
      </Button>
    </ContentLayout>
  );
};

export default ReviewLinkPrompt;
