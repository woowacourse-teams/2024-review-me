import { REVIEW } from '@/constants/review';

import * as S from './styles';

interface ReviewItemProps {
  question: string;
  answerValue: string;
  onWrite: (value: string) => void;
}

const ReviewItem = ({ question, answerValue, onWrite }: ReviewItemProps) => {
  const handleInputChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    if (value.length <= REVIEW.answerMaxLength) {
      onWrite(value);
    }
  };

  return (
    <S.ReviewItem>
      <S.ReviewQuestion>{question}</S.ReviewQuestion>
      <S.ReviewTextarea placeholder={REVIEW.answerMaxLengthMessage} value={answerValue} onChange={handleInputChange} />
      <S.ReviewTextLength>
        {answerValue.length} / {REVIEW.answerMaxLength}
      </S.ReviewTextLength>
    </S.ReviewItem>
  );
};

export default ReviewItem;
