import { useEffect } from 'react';

import { EyeButton, Input } from '@/components';
import { useEyeButton } from '@/hooks';
import { usePasswordValidation } from '@/hooks/usePasswordValidation';
import { URLGeneratorFormField } from '@/recoil/urlGeneratorForm';

import { useFormInput } from '../../hooks';
import { MAX_PASSWORD_INPUT, MIN_PASSWORD_INPUT } from '../../utils/validateInput';
import * as S from '../URLGeneratorForm/styles';

import { InputField } from './';

interface PasswordFieldProps {
  id: string;
}

const PasswordField = ({ id }: PasswordFieldProps) => {
  const [value, setValue] = useFormInput(URLGeneratorFormField.password);

  const { isOff, handleEyeButtonToggle } = useEyeButton();
  const { passwordErrorMessage, handlePasswordBlur, initializeIsBlurredOnce } = usePasswordValidation(value);

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
          value={value}
          onBlur={handlePasswordBlur}
          type={isOff ? 'password' : 'text'}
          $style={{ width: '100%', paddingRight: '3rem' }}
          onChange={(event) => {
            setValue(event.target.value);
          }}
        />
        <EyeButton isOff={isOff} handleEyeButtonToggle={handleEyeButtonToggle} />
      </S.PasswordInputContainer>
    </InputField>
  );
};

export default PasswordField;
