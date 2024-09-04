import { Dispatch, SetStateAction } from 'react';

import { EssentialPropsWithChildren } from '@/types';

import * as S from '../URLGeneratorForm/styles';

interface InputFieldProps {
  id: string;
  labelText: string;
  errorMessage: string;
  inputInfoText?: string;
}

export interface InputValueProps {
  id: string;
  value: string;
  setValue: Dispatch<SetStateAction<string>>;
}

const InputField = ({
  id,
  labelText,
  inputInfoText,
  errorMessage,
  children,
}: EssentialPropsWithChildren<InputFieldProps>) => {
  return (
    <S.InputContainer>
      <S.Label htmlFor={id}>{labelText}</S.Label>
      {inputInfoText && <S.InputInfo>{inputInfoText}</S.InputInfo>}
      {children}
      <S.ErrorMessage>{errorMessage}</S.ErrorMessage>
    </S.InputContainer>
  );
};

export default InputField;
