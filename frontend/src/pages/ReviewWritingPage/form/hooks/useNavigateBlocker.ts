import { useEffect } from 'react';
import { useBlocker } from 'react-router';
import { useRecoilValue } from 'recoil';

import { answerMapAtom } from '@/recoil';

interface UseNavigateBlockerProps {
  isNavigationUnblocked: boolean;
  openNavigateConfirmModal: () => void;
}

/**
 * @param isNavigationUnblocked : useBlocker로 라우팅을 막지 않는(다른 페이지로 이동 가능한) 상태인지를 의미함
 * 작성한 답변이 있는 상태에서 작성한 페이지에서 다른 페이지로 이동하려할때 이동을 막거나, 이동을 진행하는 blocker를 반환하는 훅
 */
const useNavigateBlocker = ({ isNavigationUnblocked, openNavigateConfirmModal }: UseNavigateBlockerProps) => {
  const answerMap = useRecoilValue(answerMapAtom);

  const isAnswerInProgress = () => {
    if (!answerMap) return false;
    return [...answerMap.values()].some((answer) => !!answer.selectedOptionIds?.length || !!answer.text?.length);
  };

  // 페이지 새로고침 및 닫기에 대한 처리: 브라우저 기본 alert 등장
  const handleNavigationBlock = (event: BeforeUnloadEvent) => {
    if (isAnswerInProgress() && !isNavigationUnblocked) event.preventDefault();
  };

  // 페이지 히스토리에 영향을 주는 페이지 이동은 useBlocker가 처리
  const blocker = useBlocker(({ currentLocation, nextLocation }) => {
    if (isNavigationUnblocked) return false;
    const isLeavingPage = currentLocation.pathname !== nextLocation.pathname;
    return isAnswerInProgress() && isLeavingPage;
  });

  useEffect(() => {
    if (blocker.state === 'blocked') {
      openNavigateConfirmModal();
    }
  }, [blocker]);

  useEffect(() => {
    window.addEventListener('beforeunload', handleNavigationBlock);

    return () => {
      window.removeEventListener('beforeunload', handleNavigationBlock);
    };
  }, [answerMap, isNavigationUnblocked]);

  return {
    blocker,
  };
};

export default useNavigateBlocker;
