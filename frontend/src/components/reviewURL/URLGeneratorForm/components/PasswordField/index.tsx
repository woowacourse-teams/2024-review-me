import { useEffect } from 'react';

import { EyeButton, Input } from '@/components';
import { REVIEW_URL_GENERATOR_FORM_VALIDATION } from '@/constants';
import { useEyeButton, usePasswordValidation } from '@/hooks';

import InputField, { InputValueProps } from '../InputField';

import * as S from './style';

const PasswordField = ({ id, value: password, updateValue: updatePassword }: InputValueProps) => {
  const { isOff, handleEyeButtonToggle } = useEyeButton();
  const { passwordErrorMessage, handlePasswordErrorMessage, handlePasswordBlur, initializeIsBlurredOnce } =
    usePasswordValidation(password);

  const { min, max } = REVIEW_URL_GENERATOR_FORM_VALIDATION.password;

  useEffect(() => {
    initializeIsBlurredOnce();
  }, [initializeIsBlurredOnce]);

  return (
    <InputField
      id={id}
      labelText="리뷰 확인에 사용할 비밀번호를 적어주세요"
      inputInfoText={`${min}~${max}자의 영문(대/소문자),숫자만 사용가능해요`}
      errorMessage={passwordErrorMessage}
    >
      <S.PasswordInputContainer>
        <Input
          id={id}
          value={password}
          onBlur={handlePasswordBlur}
          type={isOff ? 'password' : 'text'}
          $style={{ width: '100%', paddingRight: '3rem' }}
          onChange={(event) => {
            updatePassword(event.target.value);
            handlePasswordErrorMessage('');
          }}
        />
        <EyeButton isOff={isOff} handleEyeButtonToggle={handleEyeButtonToggle} />
      </S.PasswordInputContainer>
    </InputField>
  );
};

export default PasswordField;
