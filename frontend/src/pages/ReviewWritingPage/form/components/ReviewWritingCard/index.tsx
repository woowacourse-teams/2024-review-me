import React from 'react';

import { ReviewWritingCardLayout } from '@/pages/ReviewWritingPage/layout';
import { ReviewWritingCardSection } from '@/types';

import QnABox from '../QnABox';

interface ReviewWritingCardProps {
  cardSection: ReviewWritingCardSection;
}

const ReviewWritingCard = ({ cardSection }: ReviewWritingCardProps) => {
  return (
    <ReviewWritingCardLayout cardSection={cardSection}>
      {cardSection.questions.map((question) => (
        <QnABox key={question.questionId} question={question} />
      ))}
    </ReviewWritingCardLayout>
  );
};

export default React.memo(ReviewWritingCard);
