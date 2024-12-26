import { useState } from 'react';

import { isValidPasswordInput, isValidReviewGroupDataInput } from '@/pages/HomePage/utils/validateInput';

interface useURLGeneratorStateProps {
  isMember?: boolean;
}
const useURLGeneratorState = ({ isMember }: useURLGeneratorStateProps) => {
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

  const urlGeneratorStateHandler: Record<string, React.Dispatch<React.SetStateAction<string>>> = {
    revieweeName: setRevieweeName,
    projectName: setProjectName,
    password: setPassword,
  };

  return {
    revieweeName,
    projectName,
    password,
    isFormValid,
    resetForm,
    urlGeneratorStateHandler,
  };
};

export default useURLGeneratorState;
