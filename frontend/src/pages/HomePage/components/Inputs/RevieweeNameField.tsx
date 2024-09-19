import { useEffect, useState } from 'react';

import { Input } from '@/components';
import { isWithinLengthRange, MAX_VALID_REVIEW_GROUP_DATA_INPUT } from '@/pages/HomePage/utils/validateInput';

import { InputValueProps } from './InputField';

import { InputField } from '.';

const RevieweeNameField = ({ id, value: projectName, setValue: setProjectName }: InputValueProps) => {
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    isWithinLengthRange(projectName, MAX_VALID_REVIEW_GROUP_DATA_INPUT)
      ? setErrorMessage('')
      : setErrorMessage(`최대 ${MAX_VALID_REVIEW_GROUP_DATA_INPUT}자까지 입력할 수 있어요`);
  }, [projectName]);

  return (
    <InputField id={id} labelText="본인의 이름을 적어주세요" errorMessage={errorMessage}>
      <Input
        id={id}
        value={projectName}
        type="text"
        onChange={(event) => {
          setProjectName(event.target.value);
        }}
      />
    </InputField>
  );
};

export default RevieweeNameField;
