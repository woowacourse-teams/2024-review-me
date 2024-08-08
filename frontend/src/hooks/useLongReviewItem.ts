import { useState } from 'react';

interface UseLongReviewItemProps {
  minLength: number;
  maxLength: number;
  initialValue?: string;
}

const useLongReviewItem = ({ minLength, maxLength, initialValue }: UseLongReviewItemProps) => {
  const [value, setValue] = useState(initialValue);
  const [isError, setIsError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    if (value.length <= maxLength) setValue(value);
    if (value.length >= minLength) setIsError(false);
  };

  const handleBlur = (e: React.FocusEvent<HTMLTextAreaElement>) => {
    const value = e.target.value;
    if (value.length < minLength) {
      setIsError(true);
      setErrorMessage(`최소 ${minLength}자 이상 작성해 주세요.`);
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
