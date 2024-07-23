import React from 'react';
import * as S from './styles';
import { REVIEW } from '@/constants/review';

interface ReviewItemProps {
  question: string;
}

const ReviewItem = ({ question }: ReviewItemProps) => {
  return (
    <S.ReviewItem>
      <S.ReviewQuestion>{question}</S.ReviewQuestion>
      <S.ReviewTextarea placeholder={REVIEW.answerMaxLengthMessage} />
    </S.ReviewItem>
  );
};

export default ReviewItem;
