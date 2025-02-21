import { useState, useEffect } from 'react';

import { REVIEW_URL_GENERATOR_FORM_VALIDATION } from '@/constants';
import { isWithinLengthRange, isAlphanumeric } from '@/utils';

const { min, max } = REVIEW_URL_GENERATOR_FORM_VALIDATION.password;

const INVALID_CHAR_ERROR_MESSAGE = `영문(대/소문자) 및 숫자만 입력해주세요`;
const PASSWORD_LENGTH_ERROR_MESSAGE = `${min}자부터 ${max}자까지 입력할 수 있어요`;

const usePasswordValidation = (password: string) => {
  const [passwordErrorMessage, setPasswordErrorMessage] = useState('');
  const [isBlurredOnce, setIsBlurredOnce] = useState(false);

  const initializeIsBlurredOnce = () => {
    setIsBlurredOnce(false);
  };

  const validatePassword = () => {
    if (!isWithinLengthRange(password, max, min)) {
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

  const handlePasswordErrorMessage = (errorMessage: string) => setPasswordErrorMessage(errorMessage);

  useEffect(() => {
    if (isBlurredOnce) validatePassword();
  }, [password, isBlurredOnce]);

  return {
    passwordErrorMessage,
    handlePasswordErrorMessage,
    handlePasswordBlur,
    initializeIsBlurredOnce,
  };
};

export default usePasswordValidation;
