import { useMemo } from 'react';

import { useGetDetailedReview, useReviewId } from '@/hooks';
import { substituteString } from '@/utils';

import QuestionAnswerSection from './QuestionAnswerSection';
import ReviewDescription from './ReviewDescription';
import * as S from './styles';

interface DetailedReviewProps {
  $layoutStyle?: React.CSSProperties;
  selectedReviewId?: number;
}

const DetailedReview = ({ selectedReviewId, $layoutStyle }: DetailedReviewProps) => {
  const reviewId = useReviewId(selectedReviewId);

  const { data: detailedReview } = useGetDetailedReview({
    reviewId: reviewId,
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
    <S.DetailedReview $layoutStyle={$layoutStyle}>
      <ReviewDescription
        projectName={parsedDetailedReview.projectName}
        date={new Date(parsedDetailedReview.createdAt)}
        revieweeName={parsedDetailedReview.revieweeName}
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
    </S.DetailedReview>
  );
};

export default DetailedReview;
