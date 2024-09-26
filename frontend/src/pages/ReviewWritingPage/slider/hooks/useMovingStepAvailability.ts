import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';

import { cardSectionListSelector, answerValidationMapAtom, answerMapAtom } from '@/recoil';

const INDEX_OFFSET = 1;

interface UseMovingStepAvailability {
  currentCardIndex: number;
}
const useMovingStepAvailability = ({ currentCardIndex }: UseMovingStepAvailability) => {
  const cardSectionList = useRecoilValue(cardSectionListSelector);
  const answerValidationMap = useRecoilValue(answerValidationMapAtom);
  const answerMap = useRecoilValue(answerMapAtom);

  const [isAbleNextStep, setIsAbleNextStep] = useState(false);

  const isLastCard = () => cardSectionList.length - INDEX_OFFSET === currentCardIndex;

  const isAblePrevStep = (cardIndex: number) => !!currentCardIndex && !!cardIndex;

  const isValidateAnswerList = () => {
    if (!cardSectionList.length) return false;

    return cardSectionList[currentCardIndex].questions.every((question) => {
      const { questionId } = question;
      const answerValidation = answerValidationMap?.get(questionId);

      return !!answerValidation;
    });
  };

  useEffect(() => {
    const answerListValidation = isValidateAnswerList();
    setIsAbleNextStep(answerListValidation);
  }, [answerMap, currentCardIndex]);

  return {
    isAblePrevStep,
    isAbleNextStep,
    isLastCard,
  };
};

export default useMovingStepAvailability;
