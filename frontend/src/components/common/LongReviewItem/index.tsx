import React, { TextareaHTMLAttributes } from 'react';

import useLongReviewItem from '@/hooks/useLongReviewItem';

import * as S from './styles';

interface LongReviewItemProps extends TextareaHTMLAttributes<HTMLTextAreaElement> {
  minLength: number;
  maxLength: number;
  initialValue?: string;
  required: boolean;
  handleTextareaChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
}

const LongReviewItem = ({
  minLength,
  maxLength,
  initialValue = '',
  required,
  handleTextareaChange,
  style,
  ...rest
}: LongReviewItemProps) => {
  const { value, textLength, isError, errorMessage, handleChange, handleBlur } = useLongReviewItem({
    minLength,
    maxLength,
    initialValue,
    required,
  });

  const handleWriteTextarea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    handleChange(e);
    handleTextareaChange(e);
  };

  return (
    <S.TextareaContainer>
      <S.Textarea
        value={value}
        $isError={isError}
        $style={style}
        onChange={handleWriteTextarea}
        onBlur={handleBlur}
        placeholder={`최대 ${maxLength}자까지 입력 가능해요.`}
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
