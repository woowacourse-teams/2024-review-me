import { REVIEW } from '@/constants/review';

import * as S from './styles';

interface ReviewItemProps {
  question: string;
  answerValue: string;
  onWrite: (value: string) => void;
}

const ReviewItem = ({ question, answerValue, onWrite }: ReviewItemProps) => {
  return (
    <S.ReviewItem>
      <S.ReviewQuestion>{question}</S.ReviewQuestion>
      <S.ReviewTextarea
        placeholder={REVIEW.answerMaxLengthMessage}
        value={answerValue}
        onChange={(e) => onWrite(e.target.value)}
      />
    </S.ReviewItem>
  );
};

export default ReviewItem;
