import React, { TextareaHTMLAttributes } from 'react';

import * as S from './styles';

interface TextareaProps extends TextareaHTMLAttributes<HTMLTextAreaElement> {
  value: string;
  textLength: string;
  isError?: boolean;
  errorMessage?: string;
}

const Textarea = ({ value, textLength, isError = false, errorMessage = '', style, ...rest }: TextareaProps) => {
  return (
    <S.TextareaContainer>
      <S.Textarea value={value} $isError={isError} $style={style} {...rest} />
      <div>
        <S.ReviewTextareaError>{errorMessage}</S.ReviewTextareaError>
        <S.ReviewTextLength>{textLength}</S.ReviewTextLength>
      </div>
    </S.TextareaContainer>
  );
};

export default Textarea;
