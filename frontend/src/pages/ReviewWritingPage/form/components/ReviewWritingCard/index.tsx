import React from 'react';

import { ReviewWritingCardLayout } from '@/pages/ReviewWritingPage/layout/components';
import { EssentialPropsWithChildren, ReviewWritingCardSection } from '@/types';

import QnABox from '../QnABox';

interface ReviewWritingCardProps {
  cardSection: ReviewWritingCardSection;
}

const ReviewWritingCard = ({
  cardSection,
  children: sliderController,
}: EssentialPropsWithChildren<ReviewWritingCardProps>) => {
  return (
    <ReviewWritingCardLayout cardSection={cardSection}>
      {cardSection.questions.map((question) => (
        <QnABox key={question.questionId} question={question} />
      ))}
      {sliderController}
    </ReviewWritingCardLayout>
  );
};

export default React.memo(ReviewWritingCard);
