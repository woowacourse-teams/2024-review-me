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

  return {
    blocker,
  };
};

export default useNavigateBlocker;
