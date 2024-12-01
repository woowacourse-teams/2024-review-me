import { useCallback, useEffect } from 'react';
import { useRecoilValue } from 'recoil';

import { STORED_DATA_NAME } from '@/constants';
import { useSearchParamAndQuery } from '@/hooks';
import { answerMapAtom, answerValidationMapAtom, selectedCategoryAtom } from '@/recoil';

/**
 * 리뷰와 관련된 데이터들을 실시간으로 로컬 스토리지에 저장하는 훅
 */
const useSaveReviewToLocalStorage = () => {
  const selectedCategory = useRecoilValue(selectedCategoryAtom);
  const answerMap = useRecoilValue(answerMapAtom);
  const answerValidation = useRecoilValue(answerValidationMapAtom);

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const getCurrentSelectedCategory = useCallback(() => {
    if (!selectedCategory || selectedCategory.length === 0) return null;
    
    return selectedCategory;
  }, [selectedCategory]);

  const getCurrentAnswerValidation = useCallback(() => {
    if (!answerValidation || answerValidation.size === 0) return null;
    
    const plainObjectAnswers = Array.from(answerValidation.entries());
    return plainObjectAnswers.length > 0 ? plainObjectAnswers : null;
  }, [answerValidation]);

  const getCurrentAnswers = useCallback(() => {
    if (!answerMap || answerMap.size === 0) return null;
    
    const plainObjectAnswers = Array.from(answerMap.entries());
    return plainObjectAnswers.length > 0 ? plainObjectAnswers : null;
  }, [answerMap]);

  const saveToLocalStorage = useCallback(() => {
    const selectedCategories = getCurrentSelectedCategory();
    const answers = getCurrentAnswers();
    const answerValidations = getCurrentAnswerValidation();

    if (selectedCategories) {
      localStorage.setItem(
        `${STORED_DATA_NAME.selectedCategories}_${reviewRequestCode}`,
        JSON.stringify(selectedCategories),
      );
    }

    if (answerValidations) {
      localStorage.setItem(
        `${STORED_DATA_NAME.answerValidations}_${reviewRequestCode}`,
        JSON.stringify(answerValidations),
      );
    }

    if (answers) {
      localStorage.setItem(`${STORED_DATA_NAME.answers}_${reviewRequestCode}`, JSON.stringify(answers));
    }
  }, [getCurrentSelectedCategory, getCurrentAnswers, getCurrentAnswerValidation, reviewRequestCode]);

  // 로컬 스토리지 동기화
  useEffect(() => {
    saveToLocalStorage();
  }, [saveToLocalStorage]);
};

export default useSaveReviewToLocalStorage;
