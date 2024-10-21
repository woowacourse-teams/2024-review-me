import { useEffect } from 'react';
import { useRecoilValue } from 'recoil';

import { cardSectionListSelector } from '@/recoil';
import { isExistentElement } from '@/utils';

interface UseTabNavigationOnValidityProps {
  cardId: string;
}
/**
 * 현재 리뷰 작성 카드안에서 tab할 수 있는 마지막 요소를 감별해, 해당 요소에 포커스가 있을 때 tab키가 눌리면 footer의 첫번째 a로 포커스를 이동시키는 훅
 */
const useTabNavigationOnValidity = ({ cardId }: UseTabNavigationOnValidityProps) => {
  const cardSectionList = useRecoilValue(cardSectionListSelector);

  const findCurrentCardElement = () => {
    const currentCardElement = document.getElementById(cardId);
    if (!isExistentElement(currentCardElement, '현재 리뷰 작성 카드')) return;

    return currentCardElement;
  };

  const handleTabKeydown = (event: KeyboardEvent) => {
    if (event.code !== 'Tab') return;
    const currentCardElement = findCurrentCardElement();
    const lastTabCandidateList = currentCardElement?.querySelectorAll('input, textarea, button:not([disabled])');
    if (!lastTabCandidateList || lastTabCandidateList.length === 0) return;

    const lastTabElementInCard = lastTabCandidateList[lastTabCandidateList.length - 1];
    if (document.activeElement !== lastTabElementInCard) return;

    //카드 속에서 마지막 탭 요소에 focus되어있고, tab키 누를 경우
    event.preventDefault();
    (document.querySelector('footer a') as HTMLElement | null)?.focus();
  };

  useEffect(() => {
    document.addEventListener('keydown', handleTabKeydown);
    return () => {
      document.removeEventListener('keydown', handleTabKeydown);
    };
  }, [cardId, cardSectionList]);
};

export default useTabNavigationOnValidity;
