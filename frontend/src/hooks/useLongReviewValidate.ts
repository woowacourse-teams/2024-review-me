import { useEffect, useState } from 'react';

interface UseLongReviewItemProps {
  minLength: number;
  maxLength: number;
  initialValue?: string;
  handleErrorChange: (isError: boolean) => void;
}

const useLongReviewValidate = ({
  minLength,
  maxLength,
  initialValue = '',
  handleErrorChange,
}: UseLongReviewItemProps) => {
  const [value, setValue] = useState(initialValue);
  const [isError, setIsError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const REACHED_MAX_LENGTH = '더 이상 입력할 수 없어요.';
  const UNDER_MIN_LENGTH = `최소 ${minLength}자 이상 작성해 주세요.`;

  useEffect(() => {
    handleErrorChange(isError);
  }, [isError]);

  const handleChangeValidation = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    if (value.length >= minLength) setIsError(false);

    if (value.length <= maxLength) setValue(value);
    else {
      setIsError(true);
      setErrorMessage(REACHED_MAX_LENGTH);
    }
  };

  const handleBlurValidation = (e: React.FocusEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    if (value.length < minLength) {
      setIsError(true);
      setErrorMessage(UNDER_MIN_LENGTH);
    } else {
      setIsError(false);
      setErrorMessage('');
    }
  };

  return {
    value,
    isError,
    errorMessage,
    handleChangeValidation,
    handleBlurValidation,
  };
};

export default useLongReviewValidate;
