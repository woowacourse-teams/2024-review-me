import { URLGeneratorFormField } from '@/recoil/urlGeneratorForm';

import { isValidReviewGroupDataInput, isValidPasswordInput } from '../utils/validateInput';

import useURLGeneratorFormState from './useURLGeneratorFormState';

const useFormInput = (name: URLGeneratorFormField) => {
  const { formData, setFormData, setFormDataValidation } = useURLGeneratorFormState();

  const fieldValue = formData[name];

  const validateNewValue = (value: string) => {
    if (name === URLGeneratorFormField.password) {
      const isValid = isValidPasswordInput(value);
      setFormDataValidation((prev) => ({
        ...prev,
        [name]: isValid,
      }));
      return;
    }

    // NOTE: 비밀번호가 아닌 input들은 ReviewGroupDataInput으로 검사 가능
    const isValidFormData = isValidReviewGroupDataInput(value);
    setFormDataValidation((prev) => ({
      ...prev,
      [name]: isValidFormData,
    }));
  };

  const setFieldValue = (value: string) => {
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));

    validateNewValue(value);
  };

  return [fieldValue, setFieldValue] as const;
};

export default useFormInput;
