import React, { TextareaHTMLAttributes } from 'react';

import useLongReviewValidate from '@/hooks/useLongReviewValidate';

import * as S from './styles';

interface ReviewQuestion {
  order?: number;
  content: string;
}
interface ReviewAnswer {
  minLength: number;
  maxLength: number;
  handleTextareaChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  handleErrorChange: (isError: boolean) => void;
}
interface LongReviewItemProps extends TextareaHTMLAttributes<HTMLTextAreaElement> {
  question: ReviewQuestion;
  answer: ReviewAnswer;
}

const LongReviewItem = ({ question, answer, style, ...rest }: LongReviewItemProps) => {
  const { value, isError, errorMessage, handleChangeValidation, handleBlurValidation } = useLongReviewValidate({
    minLength: answer.minLength,
    maxLength: answer.maxLength,
    handleErrorChange: answer.handleErrorChange,
  });

  const handleWrite = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    handleChangeValidation(e);
    answer.handleTextareaChange(e);
  };

  const textLength = `${value.length} / ${answer.maxLength}`;

  return (
    <S.LongReviewContainer>
      <S.Question>
        {question.order && question.order + '. '}
        {question.content}
      </S.Question>
      <S.TextareaContainer>
        <S.Textarea
          value={value}
          $isError={isError}
          $style={style}
          onChange={handleWrite}
          onBlur={handleBlurValidation}
          {...rest}
        />
        <S.TextareaInfoContainer>
          <S.ReviewTextareaError>{isError && errorMessage}</S.ReviewTextareaError>
          <S.ReviewTextLength>{textLength}</S.ReviewTextLength>
        </S.TextareaInfoContainer>
      </S.TextareaContainer>
    </S.LongReviewContainer>
  );
};

export default LongReviewItem;
