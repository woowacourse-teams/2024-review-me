import { useState } from 'react';

import { isValidPasswordInput, isValidReviewGroupDataInput } from '@/utils';

interface UseURLGeneratorStateProps {
  isMember?: boolean;
}
const useURLGeneratorState = ({ isMember }: UseURLGeneratorStateProps) => {
  const [revieweeName, setRevieweeName] = useState('');
  const [projectName, setProjectName] = useState('');
  const [password, setPassword] = useState('');

  const isCommonFormValid = isValidReviewGroupDataInput(revieweeName) && isValidReviewGroupDataInput(projectName);

  const isFormValid = isMember ? isCommonFormValid : isCommonFormValid && isValidPasswordInput(password);

  const resetForm = () => {
    setRevieweeName('');
    setProjectName('');
    !isMember && setPassword('');
  };

  const urlGeneratorStateUpdater = {
    revieweeName: (value: string) => setRevieweeName(value),
    projectName: (value: string) => setProjectName(value),
    password: (value: string) => setPassword(value),
  };

  return {
    revieweeName,
    projectName,
    password,
    isFormValid,
    resetForm,
    urlGeneratorStateUpdater,
  };
};

export default useURLGeneratorState;
