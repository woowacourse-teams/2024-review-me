import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';

import { urlGeneratorFormAtom, urlGeneratorFormValidationAtom } from '@/recoil/urlGeneratorForm/index';

const useURLGeneratorFormState = () => {
  const [formData, setFormData] = useRecoilState(urlGeneratorFormAtom);
  const [formDataValidation, setFormDataValidation] = useRecoilState(urlGeneratorFormValidationAtom);
  const [isValidForm, setIsValidForm] = useState(false);

  const initializeFormData = () => {
    setFormData({
      revieweeName: '',
      projectName: '',
      password: '',
    });
  };

  const initializeIsValidForm = () => {
    setIsValidForm(false);
  };

  useEffect(() => {
    const isFormValid = Object.values(formDataValidation).every((isValid) => isValid);

    setIsValidForm(isFormValid);
  }, [formDataValidation]);

  return {
    formData,
    setFormData,
    isValidForm,
    setFormDataValidation,
    initializeFormData,
    initializeIsValidForm,
  };
};

export default useURLGeneratorFormState;
