import { useState, useEffect } from 'react';

import {
  isWithinLengthRange,
  isAlphanumeric,
  MAX_PASSWORD_INPUT,
  MIN_PASSWORD_INPUT,
} from '@/pages/HomePage/utils/validateInput';

const INVALID_CHAR_ERROR_MESSAGE = `영문(대/소문자) 및 숫자만 입력해주세요`;
const PASSWORD_LENGTH_ERROR_MESSAGE = `${MIN_PASSWORD_INPUT}자부터 ${MAX_PASSWORD_INPUT}자까지 입력할 수 있어요`;

const usePasswordValidation = (password: string) => {
  const [passwordErrorMessage, setPasswordErrorMessage] = useState('');
  const [isBlurredOnce, setIsBlurredOnce] = useState(false);

  const validatePassword = () => {
    if (!isWithinLengthRange(password, MAX_PASSWORD_INPUT, MIN_PASSWORD_INPUT)) {
      return setPasswordErrorMessage(PASSWORD_LENGTH_ERROR_MESSAGE);
    }
    if (!isAlphanumeric(password)) {
      return setPasswordErrorMessage(INVALID_CHAR_ERROR_MESSAGE);
    }
    return setPasswordErrorMessage('');
  };

  const handlePasswordBlur = () => {
    setIsBlurredOnce(true);
    validatePassword();
  };

  useEffect(() => {
    if (isBlurredOnce) validatePassword();
  }, [password, isBlurredOnce]);

  return {
    passwordErrorMessage,
    handlePasswordBlur,
  };
};

export default usePasswordValidation;
