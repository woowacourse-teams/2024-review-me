import { useRecoilValue } from 'recoil';

import { ROUTE_PARAM } from '@/constants';
import { useGetDetailedReview, useSearchParamAndQuery } from '@/hooks';
import { ReviewDescription, ReviewSection, KeywordSection } from '@/pages/DetailedReviewPage/components';
import { reviewRequestCodeAtom } from '@/recoil';

import * as S from './styles';

interface DetailedReviewPageContentsProps {
  groupAccessCode: string;
}

const DetailedReviewPageContents = ({ groupAccessCode }: DetailedReviewPageContentsProps) => {
  const storedReviewRequestCode = useRecoilValue(reviewRequestCodeAtom);
  const { param: reviewId } = useSearchParamAndQuery({
    paramKey: ROUTE_PARAM.reviewId,
  });

  const { data: detailedReview } = useGetDetailedReview({
    reviewId: Number(reviewId),
    groupAccessCode,
    reviewRequestCode: storedReviewRequestCode,
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
      {detailedReview.sections.map((section) =>
        section.questions.map((question) => (
          <S.ReviewContentContainer key={question.questionId}>
            <ReviewSection question={question.content} answer={question.answer!} />
            {question.questionType === 'CHECKBOX' && <KeywordSection options={question.optionGroup!.options} />}
          </S.ReviewContentContainer>
        )),
      )}
    </S.DetailedReviewPageContents>
  );
};

export default DetailedReviewPageContents;
