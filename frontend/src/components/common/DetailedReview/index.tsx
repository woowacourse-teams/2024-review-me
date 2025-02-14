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
  const { data } = useGetDetailedReview({ reviewId: useReviewId(selectedReviewId) });

  const transformedSections = data.sections.map(({ header, questions, ...rest }) => ({
    ...rest,
    header: substituteString({
      content: header,
      variables: { revieweeName: data.revieweeName, projectName: data.projectName },
    }),
    questions: questions.map(({ content, ...rest }) => ({
      ...rest,
      content: substituteString({
        content,
        variables: { revieweeName: data.revieweeName, projectName: data.projectName },
      }),
    })),
  }));

  return (
    <S.DetailedReview $layoutStyle={$layoutStyle}>
      <ReviewDescription
        projectName={data.projectName}
        date={new Date(data.createdAt)}
        revieweeName={data.revieweeName}
      />
      <S.Separator />
      <S.DetailedReviewContainer>
        {transformedSections.flatMap(({ questions }) =>
          questions.map(({ questionId, content, questionType, answer, optionGroup }) => (
            <S.ReviewContentContainer key={questionId}>
              <QuestionAnswerSection
                question={content}
                questionType={questionType}
                answer={answer}
                options={optionGroup?.options}
              />
            </S.ReviewContentContainer>
          )),
        )}
      </S.DetailedReviewContainer>
    </S.DetailedReview>
  );
};

export default DetailedReview;
