import { useEffect } from 'react';
import { useBlocker } from 'react-router';
import { useRecoilValue } from 'recoil';

import { answerMapAtom } from '@/recoil';

interface UseNavigateBlockerProps {
  isOpenModalDisablingBlocker: boolean;
  openNavigateConfirmModal: () => void;
}

/**
 * @param isOpenModalDisablingBlocker : 이동 확인 모달과 리뷰 제출 확인 모달 모달들이 열려있는 지 여부 (모달의 이동/제출 버튼 클릭 시 페이지 이동해야하기 때문에 필요)
 * 작성한 답변이 있는 상태에서 작성한 페이지에서 다른 페이지로 이동하려할때 이동을 막거나, 이동을 진행하는 blocker를 반환하는 훅
 */
const useNavigateBlocker = ({ isOpenModalDisablingBlocker, openNavigateConfirmModal }: UseNavigateBlockerProps) => {
  const answerMap = useRecoilValue(answerMapAtom);

  const isAnswerInProgress = () => {
    if (!answerMap) return false;
    return [...answerMap.values()].some((answer) => !!answer.selectedOptionIds?.length || !!answer.text?.length);
  };

  // 페이지 새로고침 및 닫기에 대한 처리 by BeforeUnloadEvent
  const handleNavigationBlock = (event: BeforeUnloadEvent) => {
    if (isAnswerInProgress() && !isOpenModalDisablingBlocker) {
      event.preventDefault();
    }
  };

  // 페이지 히스토리에 영향을 주는 페이지 이동은 useBlocker가 처리
  const blocker = useBlocker(({ currentLocation, nextLocation }) => {
    if (isOpenModalDisablingBlocker) return false;
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
  }, [answerMap, isOpenModalDisablingBlocker]);

  return {
    blocker,
  };
};

export default useNavigateBlocker;
