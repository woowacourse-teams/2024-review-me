import { useNavigate } from 'react-router';

import { URLGeneratorForm, EmptyContent } from '@/components';
import { ROUTE } from '@/constants';
import { useGetReviewLinks } from '@/hooks';

import ReviewLinkLayout from '../layouts/ReviewLinkLayout';
import ReviewLinkItem from '../ReviewLinkItem';

import * as S from './styles';

const ReviewLinkDashboard = () => {
  const { data: reviewLinks, refetch } = useGetReviewLinks();

  const navigate = useNavigate();

  // 새로운 리뷰 링크가 생성된 후, 최신 데이터를 다시 불러오기 위해 refetch() 실행
  const handleNewReviewLink = () => {
    refetch();
  };

  const handleReivewLinkItemClick = (reviewRequestCode: string) => {
    navigate(`/${ROUTE.reviewList}/${reviewRequestCode}`);
  };

  return (
    <S.ReviewLinkDashboardContainer>
      <S.FormSection>
        <URLGeneratorForm isMember={true} handleNewReviewLink={handleNewReviewLink} />
      </S.FormSection>
      <S.Separator />
      <S.LinkSection>
        <ReviewLinkLayout
          title="생성한 리뷰 링크를 확인해보세요"
          subTitle="클릭하면 해당 프로젝트의 리뷰 목록으로 이동해요"
        >
          {reviewLinks.lastReviewGroupId === 0 ? (
            <EmptyContent iconWidth="22rem" messageFontSize="2.2rem">
              생성한 리뷰 링크가 없어요...
            </EmptyContent>
          ) : (
            reviewLinks.reviewGroups.map((reviewGroup) => (
              <ReviewLinkItem
                key={reviewGroup.reviewRequestCode}
                revieweeName={reviewGroup.revieweeName}
                projectName={reviewGroup.projectName}
                createdAt={reviewGroup.createdAt}
                reviewRequestCode={reviewGroup.reviewRequestCode}
                reviewCount={reviewGroup.reviewCount}
                handleClick={() => handleReivewLinkItemClick(reviewGroup.reviewRequestCode)}
              />
            ))
          )}
        </ReviewLinkLayout>
      </S.LinkSection>
    </S.ReviewLinkDashboardContainer>
  );
};

export default ReviewLinkDashboard;
