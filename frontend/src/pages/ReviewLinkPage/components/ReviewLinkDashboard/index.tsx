import { useNavigate } from 'react-router';

import { URLGeneratorForm, EmptyContent } from '@/components';
import { REVIEW_EMPTY, ROUTE } from '@/constants';
import { useGetReviewLinks } from '@/hooks';
import { useGetUserProfile } from '@/hooks/oAuth';

import ReviewLinkLayout from '../layouts/ReviewLinkLayout';
import ReviewLinkItem from '../ReviewLinkItem';

import * as S from './styles';

const ReviewLinkDashboard = () => {
  const { userProfile, isUserLoggedIn } = useGetUserProfile();
  const memberIdProp = isUserLoggedIn && userProfile ? userProfile.memberId : undefined;

  const { data: reviewLinks, refetch } = useGetReviewLinks({
    memberId: memberIdProp,
  });

  const navigate = useNavigate();

  // 새로운 리뷰 링크가 생성된 후, 최신 데이터를 다시 불러오기 위해 refetch() 실행
  const refetchReviewLinks = () => refetch();

  const handleReviewLinkItemClick = (reviewRequestCode: string) => {
    navigate(`/${ROUTE.reviewList}/${reviewRequestCode}`);
  };

  return (
    <S.ReviewLinkDashboardContainer>
      <S.FormSection>
        <URLGeneratorForm isMember={true} refetchReviewLinks={refetchReviewLinks} />
      </S.FormSection>
      <S.Separator />
      <S.LinkSection>
        <ReviewLinkLayout
          title="생성한 리뷰 링크를 확인해보세요"
          subTitle="클릭하면 해당 프로젝트의 리뷰 목록으로 이동해요"
        >
          {reviewLinks.lastReviewGroupId === 0 ? (
            <EmptyContent iconWidth="15rem" messageFontSize="1.8rem" iconMessageGap="2rem" isBorder={true}>
              {REVIEW_EMPTY.noReviewLink}
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
                handleClick={() => handleReviewLinkItemClick(reviewGroup.reviewRequestCode)}
              />
            ))
          )}
        </ReviewLinkLayout>
      </S.LinkSection>
    </S.ReviewLinkDashboardContainer>
  );
};

export default ReviewLinkDashboard;
