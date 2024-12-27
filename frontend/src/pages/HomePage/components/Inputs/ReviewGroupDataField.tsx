import { useState } from 'react';

import { Input } from '@/components';
import { REVIEW_URL_GENERATOR_FORM_VALIDATION } from '@/constants';
import { isValidReviewGroupDataInput } from '@/pages/HomePage/utils/validateInput';

import { InputValueProps } from './InputField';

import { InputField } from '.';

interface ReviewGroupDataFieldProps extends InputValueProps {
  labelText: string;
}
const ReviewGroupDataField = ({ id, labelText, value: data, updateValue: updateData }: ReviewGroupDataFieldProps) => {
  const [errorMessage, setErrorMessage] = useState('');
  const { max, min } = REVIEW_URL_GENERATOR_FORM_VALIDATION.groupData;

  const handleBlur = () => {
    isValidReviewGroupDataInput(data)
      ? setErrorMessage('')
      : setErrorMessage(`${min}자부터 ${max}자까지 입력할 수 있어요`);
  };

  return (
    <InputField id={id} labelText={labelText} errorMessage={errorMessage}>
      <Input
        id={id}
        value={data}
        type="text"
        onChange={(event) => {
          updateData(event.target.value);
          setErrorMessage('');
        }}
        onBlur={handleBlur}
      />
    </InputField>
  );
};

export default ReviewGroupDataField;
