import { useMemo } from 'react';

import { ROUTE_PARAM } from '@/constants';
import { useGetDetailedReview, useSearchParamAndQuery } from '@/hooks';
import { ReviewDescription, ReviewSection, KeywordSection } from '@/pages/DetailedReviewPage/components';
import { substituteString } from '@/utils';

import * as S from './styles';

const DetailedReviewPageContents = () => {
  const { param: reviewId } = useSearchParamAndQuery({
    paramKey: ROUTE_PARAM.reviewId,
  });

  const { data: detailedReview } = useGetDetailedReview({
    reviewId: Number(reviewId),
  });

  const newDetailedReview = useMemo(() => {
    return {
      ...detailedReview,
      sections: detailedReview.sections.map((section) => {
        const newHeader = substituteString({
          content: section.header,
          variables: { revieweeName: detailedReview.revieweeName, projectName: detailedReview.projectName },
        });

        const newQuestions = section.questions.map((question) => {
          const newContent = substituteString({
            content: question.content,
            variables: { revieweeName: detailedReview.revieweeName, projectName: detailedReview.projectName },
          });

          return {
            ...question,
            content: newContent,
          };
        });

        return {
          ...section,
          header: newHeader,
          questions: newQuestions,
        };
      }),
    };
  }, [detailedReview]);

  // TODO: 리뷰 공개/비공개 토글 버튼 기능
  return (
    <S.DetailedReviewPageContents>
      <ReviewDescription
        projectName={newDetailedReview.projectName}
        date={new Date(newDetailedReview.createdAt)}
        revieweeName={newDetailedReview.revieweeName}
        isPublic={true}
        handleClickToggleButton={() => console.log('click toggle ')}
      />
      {newDetailedReview.sections.map((section) =>
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
