import { useEffect, useState } from 'react';

import { Input } from '@/components';
import { isWithinLengthRange, MAX_VALID_REVIEW_GROUP_DATA_INPUT } from '@/pages/HomePage/utils/validateInput';

import { InputValueProps } from './InputField';

import { InputField } from './';

const ProjectNameField = ({ id, value: revieweeName, setValue: setRevieweeName }: InputValueProps) => {
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    isWithinLengthRange(revieweeName, MAX_VALID_REVIEW_GROUP_DATA_INPUT)
      ? setErrorMessage('')
      : setErrorMessage(`최대 ${MAX_VALID_REVIEW_GROUP_DATA_INPUT}자까지 입력할 수 있어요`);
  }, [revieweeName]);

  return (
    <InputField id={id} labelText="함께한 프로젝트 이름을 입력해주세요" errorMessage={errorMessage}>
      <Input
        id={id}
        value={revieweeName}
        type="text"
        onChange={(event) => {
          setRevieweeName(event.target.value);
        }}
      />
    </InputField>
  );
};

export default ProjectNameField;
