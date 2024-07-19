import React from 'react';
import * as S from './styles';

interface ReviewItemProps {
  question: string;
}

const ReviewItem = ({ question }: ReviewItemProps) => {
  return (
    <S.ReviewItem>
      <S.ReviewQuestion>{question}</S.ReviewQuestion>
      <S.ReviewTextarea />
    </S.ReviewItem>
  );
};

export default ReviewItem;
