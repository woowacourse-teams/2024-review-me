import { useEffect } from 'react';
import { useBlocker } from 'react-router';
import { useRecoilValue } from 'recoil';

import { answerMapAtom } from '@/recoil';

interface UseNavigateBlockerProps {
  openNavigateConfirmModal: () => void;
}

const useNavigateBlocker = ({ openNavigateConfirmModal }: UseNavigateBlockerProps) => {
  const answerMap = useRecoilValue(answerMapAtom);

  const isAnswerInProgress = () => {
    if (!answerMap) return false;
    return [...answerMap.values()].some((answer) => !!answer.selectedOptionIds?.length || !!answer.text?.length);
  };

  // 페이지 새로고침 및 닫기에 대한 처리: 브라우저 기본 alert 등장
  const handleNavigationBlock = (event: BeforeUnloadEvent) => {
    if (isAnswerInProgress()) event.preventDefault();
  };

  // 페이지 히스토리에 영향을 주는 페이지 이동 처리: useBlocker 이용
  const blocker = useBlocker(({ currentLocation, nextLocation }) => {
    const isLeavingPage = currentLocation.pathname !== nextLocation.pathname;
    const isMoveToCompletePage = nextLocation.pathname.includes('complete');
    // 리뷰 작성 완료 페이지로 이동하는 url 변경인 경우에는 navigateConfirm 모달을 띄우지 않음
    return isAnswerInProgress() && isLeavingPage && !isMoveToCompletePage;
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
  }, [answerMap]);

  return {
    blocker,
  };
};

export default useNavigateBlocker;
