import { useCallback, useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';

import { STORED_DATA_NAME } from '@/constants';
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

  const handleBeforeUnloadChange = useCallback(() => {
    if (visitedCardIdList.length === 0) return;

    localStorage.setItem(
      `${STORED_DATA_NAME.visitedCardIdList}_${reviewRequestCode}`,
      JSON.stringify(visitedCardIdList),
    );
  }, [reviewRequestCode, visitedCardIdList]);

  // 복원
  useEffect(() => {
    if (cardSectionList.length === 0 || !reviewRequestCode) return;

    const storedVisitedCardIdList = localStorage.getItem(`${STORED_DATA_NAME.visitedCardIdList}_${reviewRequestCode}`);
    const parsedVisitedCardIdList = storedVisitedCardIdList ? JSON.parse(storedVisitedCardIdList) : [];

    setVisitedCardIdList(parsedVisitedCardIdList);
  }, [reviewRequestCode, cardSectionList]);

  // 로컬 스토리지와의 동기화를 위한 useEffect
  useEffect(() => {
    handleBeforeUnloadChange();
  }, [visitedCardIdList]);

  useEffect(() => {
    if (cardSectionList.length === 0 || visitedCardIdList.length === 0) return;

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
