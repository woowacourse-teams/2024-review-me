import { useState } from 'react';

import { Input } from '@/components';
import { REVIEW_URL_GENERATOR_FORM_VALIDATION } from '@/constants';
import { isNotEmptyInput, isValidReviewGroupDataInput } from '@/utils';

import InputField, { InputValueProps } from './InputField';

const EMPTY_ERROR_MESSAGE = '공백이 아닌 내용을 입력해주세요';
const { min, max } = REVIEW_URL_GENERATOR_FORM_VALIDATION.groupData;
const WRONG_LENGTH_ERROR_MESSAGE = `${min}자부터 ${max}자까지 입력할 수 있어요`;
interface ReviewGroupDataFieldProps extends InputValueProps {
  labelText: string;
}
const ReviewGroupDataField = ({ id, labelText, value: data, updateValue: updateData }: ReviewGroupDataFieldProps) => {
  const [errorMessage, setErrorMessage] = useState('');

  const handleBlur = () => {
    if (isValidReviewGroupDataInput(data)) return setErrorMessage('');
    // 공백으로만 이루어진 경우
    if (!isNotEmptyInput(data)) return setErrorMessage(EMPTY_ERROR_MESSAGE);
    // 글자 수 초과
    setErrorMessage(WRONG_LENGTH_ERROR_MESSAGE);
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
