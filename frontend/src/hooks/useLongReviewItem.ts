import { useState } from 'react';

interface UseLongReviewItemProps {
  minLength: number;
  maxLength: number;
  initialValue?: string;
  required: boolean;
}

const useLongReviewItem = ({ minLength, maxLength, initialValue, required }: UseLongReviewItemProps) => {
  const [value, setValue] = useState(initialValue);
  const [isError, setIsError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    if (value.length <= maxLength) setValue(value);
    if (value.length >= minLength) setIsError(false);
    if (value.length > maxLength) {
      setIsError(true);
      setErrorMessage(`최대 ${maxLength}자까지만 입력 가능해요`);
    }
  };

  const handleBlur = (e: React.FocusEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    const errorOnRequired = required && value.length < minLength;
    const errorOnNotRequired = !required && value.length > 0 && value.length < minLength;

    if (errorOnRequired || errorOnNotRequired) {
      setIsError(true);
      setErrorMessage(`최소 ${minLength}자 이상 작성해 주세요`);
    } else {
      setIsError(false);
      setErrorMessage('');
    }
  };

  return {
    value,
    textLength: `${(value && value.length) || 0} / ${maxLength}`,
    isError,
    errorMessage,
    handleChange,
    handleBlur,
  };
};

export default useLongReviewItem;
