import React, { TextareaHTMLAttributes } from 'react';

import useLongReviewItem from '@/hooks/useLongReviewItem';

import * as S from './styles';

interface LongReviewItemProps extends TextareaHTMLAttributes<HTMLTextAreaElement> {
  minLength: number;
  maxLength: number;
  initialValue?: string;
}

const LongReviewItem = ({ minLength, maxLength, initialValue = '', style, ...rest }: LongReviewItemProps) => {
  const { value, textLength, isError, errorMessage, handleChange, handleBlur } = useLongReviewItem({
    minLength,
    maxLength,
    initialValue,
  });

  return (
    <S.TextareaContainer>
      <S.Textarea
        value={value}
        $isError={isError}
        $style={style}
        onChange={handleChange}
        onBlur={handleBlur}
        {...rest}
      />
      <S.TextareaInfoContainer>
        <S.ReviewTextareaError>{isError && errorMessage}</S.ReviewTextareaError>
        <S.ReviewTextLength>{textLength}</S.ReviewTextLength>
      </S.TextareaInfoContainer>
    </S.TextareaContainer>
  );
};

export default LongReviewItem;
