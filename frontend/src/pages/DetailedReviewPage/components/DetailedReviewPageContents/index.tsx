import { DETAILED_REVIEW_API_PARAMS } from '@/apis/endpoints';
import { useGetDetailedReview, useSearchParamAndQuery } from '@/hooks';
import { ReviewDescription, ReviewSection, KeywordSection } from '@/pages/DetailedReviewPage/components';

import * as S from './styles';

interface DetailedReviewPageContentsProps {
  groupAccessCode: string;
}

const DetailedReviewPageContents = ({ groupAccessCode }: DetailedReviewPageContentsProps) => {
  const { param: reviewId } = useSearchParamAndQuery({
    paramKey: 'reviewId',
    queryStringKey: DETAILED_REVIEW_API_PARAMS.queryString.memberId,
  });

  const { data: detailedReview } = useGetDetailedReview({
    reviewId: Number(reviewId),
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
      {/* {detailedReview.contents.map(({ id, question, answer }, index) => (
        <ReviewSection key={id} question={question} answer={answer} index={index} />
      ))} */}
      {detailedReview.sections.map((section) =>
        section.questions.map((question, index) => (
          <S.ReviewContentContainer key={index}>
            <ReviewSection question={question.content} answer={question.answer!} />
            {question.questionType === 'CHECKBOX' && <KeywordSection options={question.optionGroup!.options} />}
          </S.ReviewContentContainer>
        )),
      )}
    </S.DetailedReviewPageContents>
  );
};

export default DetailedReviewPageContents;
