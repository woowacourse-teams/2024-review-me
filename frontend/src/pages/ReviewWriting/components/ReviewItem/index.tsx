import { REVIEW, REVIEW_MESSAGE } from '@/constants/review';

import * as S from './styles';

interface ReviewItemProps {
  question: string;
  answerValue: string;
  handleWrite: (value: string) => void;
}

const ReviewItem = ({ question, answerValue, handleWrite }: ReviewItemProps) => {
  const handleInputChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    if (value.length <= REVIEW.answerMaxLength) {
      handleWrite(value);
    }
  };

  return (
    <S.ReviewItem>
      <S.ReviewQuestion>{question}</S.ReviewQuestion>
      <S.ReviewTextarea placeholder={REVIEW_MESSAGE.answerMaxLength} value={answerValue} onChange={handleInputChange} />
      <S.ReviewTextLength>
        {answerValue.length} / {REVIEW.answerMaxLength}
      </S.ReviewTextLength>
    </S.ReviewItem>
  );
};

export default ReviewItem;
