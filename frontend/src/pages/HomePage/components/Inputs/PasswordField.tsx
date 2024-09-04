import { useEffect } from 'react';

import { EyeButton, Input } from '@/components';
import { useEyeButton } from '@/hooks';
import { usePasswordValidation } from '@/hooks/usePasswordValidation';

import { MAX_PASSWORD_INPUT, MIN_PASSWORD_INPUT } from '../../utils/validateInput';
import * as S from '../URLGeneratorForm/styles';

import { InputValueProps } from './InputField';

import { InputField } from '.';

const PasswordField = ({ id, value: password, setValue: setPassword }: InputValueProps) => {
  const { isOff, handleEyeButtonToggle } = useEyeButton();
  const { passwordErrorMessage, handlePasswordBlur, initializeIsBlurredOnce } = usePasswordValidation(password);

  useEffect(() => {
    initializeIsBlurredOnce();
  }, [initializeIsBlurredOnce]);

  return (
    <InputField
      id={id}
      labelText="리뷰 확인에 사용할 비밀번호를 적어주세요"
      inputInfoText={`${MIN_PASSWORD_INPUT}~${MAX_PASSWORD_INPUT}자의 영문(대/소문자),숫자만 사용가능해요`}
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
            setPassword(event.target.value);
          }}
        />
        <EyeButton isOff={isOff} handleEyeButtonToggle={handleEyeButtonToggle} />
      </S.PasswordInputContainer>
    </InputField>
  );
};

export default PasswordField;
