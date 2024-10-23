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

    // 리뷰 작성 카드에서, tab으로 접근 가능한 요소들은
    // 활성화된 버튼(이동 버튼, 프로그레스 바 버튼), CheckboxItem, textarea
    const lastTabCandidateList = currentCardElement?.querySelectorAll(
      'textarea, .checkbox-item, button:not([disabled])',
    );
    if (!lastTabCandidateList || lastTabCandidateList.length === 0) return;

    const lastTabElementInCard = lastTabCandidateList[lastTabCandidateList.length - 1];
    if (document.activeElement !== lastTabElementInCard) return;

    // 리뷰 작성 카드에서 tab 가능한 마지막 요소에 focus가 있을 때 tab키를 누를 경우
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
