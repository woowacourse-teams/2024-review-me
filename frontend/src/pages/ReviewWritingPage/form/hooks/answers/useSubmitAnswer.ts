import { useNavigate } from 'react-router';
import { useRecoilValue } from 'recoil';

import { ROUTE } from '@/constants/route';
import { CARD_FORM_MODAL_KEY } from '@/pages/ReviewWritingPage/constants';
import { answerMapAtom } from '@/recoil';
import { ReviewWritingFormResult } from '@/types';

import useMutateReview from '../useMutateReview';

interface UseSubmitAnswerProps {
  reviewRequestCode: string | undefined;
  closeModal: (key: string) => void;
}
const useSubmitAnswer = ({ reviewRequestCode, closeModal }: UseSubmitAnswerProps) => {
  const answerMap = useRecoilValue(answerMapAtom);
  const navigate = useNavigate();
  const executeAfterMutateSuccess = () => {
    navigate(`/${ROUTE.reviewWritingComplete}`);
    closeModal(CARD_FORM_MODAL_KEY.submitConfirm);
  };
  const { postReview } = useMutateReview({ executeAfterMutateSuccess });

  const submitAnswer = async (event: React.MouseEvent) => {
    event.preventDefault();

    if (!answerMap || !reviewRequestCode) return;

    const result: ReviewWritingFormResult = {
      reviewRequestCode: reviewRequestCode,
      answers: Array.from(answerMap.values()),
    };
    postReview(result);
  };

  return {
    submitAnswer,
  };
};
export default useSubmitAnswer;
