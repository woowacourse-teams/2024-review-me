import { useLocation, useParams } from 'react-router';

import { DETAILED_REVIEW_API_PARAMS } from '@/apis/endpoints';
import { useGetDetailedReview } from '@/hooks';
import { ReviewDescription, ReviewSection, KeywordSection } from '@/pages/DetailedReviewPage/components';

import * as S from './styles';

interface DetailedReviewPageContentsProps {
  groupAccessCode: string;
}
const DetailedReviewPageContents = ({ groupAccessCode }: DetailedReviewPageContentsProps) => {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const memberId = queryParams.get(DETAILED_REVIEW_API_PARAMS.queryString.memberId);

  const { detailedReview } = useGetDetailedReview({
    id: Number(id),
    memberId: Number(memberId),
    groupAccessCode,
  });

  // TODO: 리뷰 공개/비공개 토글 버튼 기능
  return (
    <S.DetailedReviewPageContents>
      <ReviewDescription
        projectName={detailedReview.projectName}
        date={new Date(detailedReview.createdAt)}
        revieweeName={detailedReview.revieweeName}
        isPublic={true}
        handleClickToggleButton={() => console.log('click toggle ')}
      />
      {/* 시연 때 숨김 <RevieweeComments comment={detailedReview.reviewerGroup.description} /> */}
      {detailedReview.contents.map(({ id, question, answer }, index) => (
        <ReviewSection key={id} question={question} answer={answer} index={index} />
      ))}
      <KeywordSection keywords={detailedReview.keywords} index={detailedReview.contents.length} />
    </S.DetailedReviewPageContents>
  );
};

export default DetailedReviewPageContents;
