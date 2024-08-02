import { useState } from 'react';

import { REVIEW, REVIEW_MESSAGE } from '@/constants/review';

import * as S from './styles';

interface ReviewItemProps {
  question: string;
  answerValue: string;
  handleWrite: (value: string) => void;
}

const ReviewItem = ({ question, answerValue, handleWrite }: ReviewItemProps) => {
  const [isError, setIsError] = useState(false);

  const errorMessage = isError ? '최소 20자 이상 작성해 주세요.' : '';

  const handleInputChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    if (value.length <= REVIEW.answerMaxLength) {
      handleWrite(value);
    }

    if (value.length >= REVIEW.answerMinLength) {
      setIsError(false);
    }
  };

  const handleTextareaBlur = (e: React.FocusEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    if (value.length < REVIEW.answerMinLength) {
      setIsError(true);
    } else {
      setIsError(false);
    }
  };

  return (
    <S.ReviewItem>
      <S.ReviewQuestion>{question}</S.ReviewQuestion>
      <S.ReviewTextarea
        placeholder={REVIEW_MESSAGE.answerMaxLength}
        value={answerValue}
        onChange={handleInputChange}
        onBlur={handleTextareaBlur}
        $isError={isError}
      />
      <S.Container>
        <S.ReviewTextareaError>{errorMessage}</S.ReviewTextareaError>
        <S.ReviewTextLength>
          {answerValue.length} / {REVIEW.answerMaxLength}
        </S.ReviewTextLength>
      </S.Container>
    </S.ReviewItem>
  );
};

export default ReviewItem;
