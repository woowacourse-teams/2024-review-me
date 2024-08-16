import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';

import { cardSectionListSelector, answerValidationMapAtom, answerMapAtom } from '@/recoil';

interface UseCheckNextStepAvailability {
  currentCardIndex: number;
}
const useCheckNextStepAvailability = ({ currentCardIndex }: UseCheckNextStepAvailability) => {
  const cardSectionList = useRecoilValue(cardSectionListSelector);
  const answerValidationMap = useRecoilValue(answerValidationMapAtom);
  const answerMap = useRecoilValue(answerMapAtom);

  const [isAbleNextStep, setIsAbleNextStep] = useState(false);

  const isValidateAnswerList = () => {
    if (!cardSectionList.length) return false;

    return cardSectionList[currentCardIndex].questions.every((question) => {
      const { questionId, required } = question;
      const answerValidation = answerValidationMap?.get(questionId);

      if (!required && answerValidation) return true;
      return !!answerValidation;
    });
  };

  useEffect(() => {
    const answerListValidation = isValidateAnswerList();
    setIsAbleNextStep(answerListValidation);
  }, [answerMap, currentCardIndex]);

  return {
    isAbleNextStep,
  };
};

export default useCheckNextStepAvailability;
