import { useCallback } from 'react';
import { useSetRecoilState } from 'recoil';

import { STORED_DATA_NAME } from '@/constants';
import { useSearchParamAndQuery } from '@/hooks';
import { CARD_FORM_MODAL_KEY } from '@/pages/ReviewWritingPage/constants';
import { selectedCategoryAtom, answerMapAtom, answerValidationMapAtom } from '@/recoil';

const useRestoreFromLocalStorage = () => {
  const setSelectedCategory = useSetRecoilState(selectedCategoryAtom);
  const setAnswerMap = useSetRecoilState(answerMapAtom);
  const setAnswerValidation = useSetRecoilState(answerValidationMapAtom);

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const initialModalsState = {
    [CARD_FORM_MODAL_KEY.restoreConfirm]:
      !!localStorage.getItem(`${STORED_DATA_NAME.selectedCategories}_${reviewRequestCode}`) ||
      !!localStorage.getItem(`${STORED_DATA_NAME.answers}_${reviewRequestCode}`) ||
      !!localStorage.getItem(`${STORED_DATA_NAME.answerValidations}_${reviewRequestCode}`),
  };

  const restoreData = useCallback(() => {
    const storedSelectedCategories = localStorage.getItem(
      `${STORED_DATA_NAME.selectedCategories}_${reviewRequestCode}`,
    );
    const storedAnswerValidations = localStorage.getItem(`${STORED_DATA_NAME.answerValidations}_${reviewRequestCode}`);
    const storedAnswers = localStorage.getItem(`${STORED_DATA_NAME.answers}_${reviewRequestCode}`);

    const selectedCategories = storedSelectedCategories ? JSON.parse(storedSelectedCategories) : null;
    const answerValidations = storedAnswerValidations ? new Map(JSON.parse(storedAnswerValidations)) : new Map();
    const answers = storedAnswers ? JSON.parse(storedAnswers) : null;

    setSelectedCategory(selectedCategories);
    setAnswerValidation(answerValidations);
    setAnswerMap(new Map(answers));
  }, [reviewRequestCode, setSelectedCategory, setAnswerMap, setAnswerValidation]);

  return { restoreData, initialModalsState };
};

export default useRestoreFromLocalStorage;
