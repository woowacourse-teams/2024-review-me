import React from 'react';

import { ReviewWritingCardSection } from '@/types';

import QnABox from '../QnABox';

import * as S from './style';

interface ReviewWritingCardProps {
  cardSection: ReviewWritingCardSection;
}

const ReviewWritingCard = ({ cardSection }: ReviewWritingCardProps) => {
  return (
    <S.ReviewWritingCard>
      <S.Header>{cardSection.header}</S.Header>
      <S.Main>
        {cardSection.questions.map((question) => (
          <QnABox key={question.questionId} question={question} />
        ))}
      </S.Main>
    </S.ReviewWritingCard>
  );
};

export default React.memo(ReviewWritingCard);
