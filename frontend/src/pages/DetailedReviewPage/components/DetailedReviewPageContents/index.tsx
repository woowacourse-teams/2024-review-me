import { useMemo } from 'react';

import { ROUTE_PARAM } from '@/constants';
import { useGetDetailedReview, useSearchParamAndQuery } from '@/hooks';
import { ReviewDescription, QuestionAnswerSection } from '@/pages/DetailedReviewPage/components';
import { substituteString } from '@/utils';

import * as S from './styles';

const DetailedReviewPageContents = () => {
  const { param: reviewId } = useSearchParamAndQuery({
    paramKey: ROUTE_PARAM.reviewId,
  });

  const { data: detailedReview } = useGetDetailedReview({
    reviewId: Number(reviewId),
  });

  const parsedDetailedReview = useMemo(() => {
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

  return (
    <S.DetailedReviewPageContents>
      <ReviewDescription
        projectName={parsedDetailedReview.projectName}
        date={new Date(parsedDetailedReview.createdAt)}
        revieweeName={parsedDetailedReview.revieweeName}
        isPublic={true}
        handleClickToggleButton={() => console.log('click toggle ')}
      />
      <S.Separator />
      <S.DetailedReviewContainer>
        {parsedDetailedReview.sections.map((section) =>
          section.questions.map((question) => (
            <S.ReviewContentContainer key={question.questionId}>
              <QuestionAnswerSection
                question={question.content}
                questionType={question.questionType}
                answer={question.answer}
                options={question.optionGroup?.options}
              />
            </S.ReviewContentContainer>
          )),
        )}
      </S.DetailedReviewContainer>
    </S.DetailedReviewPageContents>
  );
};

export default DetailedReviewPageContents;
