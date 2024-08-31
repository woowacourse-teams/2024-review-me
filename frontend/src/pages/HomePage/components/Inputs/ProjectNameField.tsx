import { useEffect, useState } from 'react';

import { Input } from '@/components';
import { URLGeneratorFormField } from '@/types';

import { useFormInput } from '../../hooks';
import { isWithinLengthRange, MAX_VALID_REVIEW_GROUP_DATA_INPUT } from '../../utils/validateInput';

import { InputField } from './';

interface ProjectNameFieldProps {
  id: string;
}

const ProjectNameField = ({ id }: ProjectNameFieldProps) => {
  const [projectName, setProjectName] = useFormInput(URLGeneratorFormField.projectName);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    isWithinLengthRange(projectName, MAX_VALID_REVIEW_GROUP_DATA_INPUT)
      ? setErrorMessage('')
      : setErrorMessage(`최대 ${MAX_VALID_REVIEW_GROUP_DATA_INPUT}자까지 입력할 수 있어요`);
  }, [projectName]);

  return (
    <InputField id={id} labelText="함께한 프로젝트 이름을 입력해주세요" errorMessage={errorMessage}>
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

export default ProjectNameField;
