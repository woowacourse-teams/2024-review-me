import { useEffect, useState } from 'react';

import { Input } from '@/components';
import { URLGeneratorFormField } from '@/recoil/urlGeneratorForm';

import { useFormInput } from '../../hooks';
import { isWithinLengthRange, MAX_VALID_REVIEW_GROUP_DATA_INPUT } from '../../utils/validateInput';

import { InputField } from './';

interface ProjectNameFieldProps {
  id: string;
}

const ProjectNameField = ({ id }: ProjectNameFieldProps) => {
  const [value, setValue] = useFormInput(URLGeneratorFormField.projectName);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    isWithinLengthRange(value, MAX_VALID_REVIEW_GROUP_DATA_INPUT)
      ? setErrorMessage('')
      : setErrorMessage(`최대 ${MAX_VALID_REVIEW_GROUP_DATA_INPUT}자까지 입력할 수 있어요`);
  }, [value]);

  return (
    <InputField id={id} labelText="함께한 프로젝트 이름을 입력해주세요" errorMessage={errorMessage}>
      <Input
        id={id}
        value={value}
        type="text"
        onChange={(event) => {
          setValue(event.target.value);
        }}
      />
    </InputField>
  );
};

export default ProjectNameField;
