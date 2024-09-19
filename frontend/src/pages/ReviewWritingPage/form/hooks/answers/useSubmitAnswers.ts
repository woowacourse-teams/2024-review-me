import { useNavigate } from 'react-router';
import { useRecoilValue } from 'recoil';

import { ROUTE } from '@/constants/route';
import { answerMapAtom, reviewRequestCodeAtom } from '@/recoil';
import { ReviewWritingFormResult } from '@/types';

import useMutateReview from '../useMutateReview';

interface UseSubmitAnswersProps {
  closeSubmitConfirmModal: () => void;
}
/**
 * 리뷰 제출 모달에서 제출 버튼 클릭 시 실행되는 서버에 리뷰 답변 제출 및 기타 액션을 관리하는 훅
 */
const useSubmitAnswers = ({ closeSubmitConfirmModal }: UseSubmitAnswersProps) => {
  const reviewRequestCode = useRecoilValue(reviewRequestCodeAtom);

  const answerMap = useRecoilValue(answerMapAtom);

  const navigate = useNavigate();

  const executeAfterMutateSuccess = () => {
    navigate(`/${ROUTE.reviewWritingComplete}`);
    closeSubmitConfirmModal();
  };

  const { postReview } = useMutateReview({ executeAfterMutateSuccess });

  const submitAnswers = async (event: React.MouseEvent) => {
    event.preventDefault();

    if (!answerMap || !reviewRequestCode) return;

    const result: ReviewWritingFormResult = {
      reviewRequestCode: reviewRequestCode,
      answers: Array.from(answerMap.values()),
    };

    postReview(result);
  };

  return {
    submitAnswers,
  };
};
export default useSubmitAnswers;
