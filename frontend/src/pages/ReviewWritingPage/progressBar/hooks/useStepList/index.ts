import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';

import { useSearchParamAndQuery } from '@/hooks';
import { answerValidationMapAtom, cardSectionListSelector } from '@/recoil';

interface UseStepListProps {
  currentCardIndex: number;
}
const useStepList = ({ currentCardIndex }: UseStepListProps) => {
  const cardSectionList = useRecoilValue(cardSectionListSelector);
  const answerValidationMap = useRecoilValue(answerValidationMapAtom);

  interface Step {
    sectionId: number;
    sectionName: string;
    isMovingAvailable: boolean;
    isDone: boolean;
    isCurrentStep: boolean;
  }
  const [stepList, setStepList] = useState<Step[]>([]);

  const [visitedCardIdList, setVisitedCardIdList] = useState<number[]>([]);

  const updateStepList = () => {
    const newStepList = makeNewStepList();

    setStepList(newStepList);
  };

  const makeNewStepList = () => {
    const newStepList: Step[] = [];

    cardSectionList?.forEach((section, index) => {
      const isPreviousDone = index === 0 || newStepList.every((step) => step.isDone); 
      const isMovingAvailable = isPreviousDone && visitedCardIdList.includes(section.sectionId);
      const isCurrentStep = index === currentCardIndex;

      newStepList.push({
        sectionId: section.sectionId,
        sectionName: section.sectionName,
        isMovingAvailable,
        isDone: section.questions.every((question) => answerValidationMap?.get(question.questionId)),
        isCurrentStep,
      });
    });

    return newStepList;
  };

  const updateVisitedCardIdList = () => {
    setVisitedCardIdList((prev) => {
      const currentCard = cardSectionList[currentCardIndex];
      if (!currentCard) return [];
      // 첫 렌더링 시
      if (prev.length === 0) {
        return [cardSectionList[0].sectionId];
      }
      // 새로운 카드 방문
      if (!prev.includes(currentCard.sectionId)) {
        return prev.concat(currentCard.sectionId);
      }
      // cardList가 변경될 때(currentCardIndex가 변경되었을때에도 새로운 visitedCardList를 만들어서, stepList가 변경됨)
      return prev.filter((id) => cardSectionList.some((section) => section.sectionId === id));
    });
  };

  // 복원 및 저장 로직
  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const handleBeforeUnloadChange = () => {
    if (visitedCardIdList.length > 0) {
      localStorage.setItem(`visitedCardIdList_${reviewRequestCode}`, JSON.stringify(visitedCardIdList));
    }
  };

  // 복원
  useEffect(() => {
    (() => {
      const storedVisitedCardIdList = localStorage.getItem(`visitedCardIdList_${reviewRequestCode}`);
      const defaultVisitedCardIdList = cardSectionList.length > 0 ? [cardSectionList[0].sectionId] : [];
      const parsedVisitedCardIdList = storedVisitedCardIdList
        ? JSON.parse(storedVisitedCardIdList)
        : defaultVisitedCardIdList;

      setVisitedCardIdList(parsedVisitedCardIdList); 
    })();
  }, [reviewRequestCode, cardSectionList]);

  // 저장
  useEffect(() => {
    // TODO: 라우터를 통한 이동에서도 동작할 수 있도록 하기
    window.addEventListener('beforeunload', handleBeforeUnloadChange);
    document.addEventListener('visibilitychange', handleBeforeUnloadChange);

    return () => {
      window.removeEventListener('beforeunload', handleBeforeUnloadChange);
      document.removeEventListener('visibilitychange', handleBeforeUnloadChange);
    };
  }, [reviewRequestCode, visitedCardIdList]);

  useEffect(() => {
    updateVisitedCardIdList();
  }, [cardSectionList, currentCardIndex]);

  useEffect(() => {
    updateStepList();
  }, [visitedCardIdList, answerValidationMap]);

  return {
    stepList,
  };
};

export default useStepList;
