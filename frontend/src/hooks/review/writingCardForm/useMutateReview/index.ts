import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEYS } from '@/constants';
import { ReviewWritingFormResult } from '@/types';

interface UseMutateReviewProps {
  openErrorModal: (message: string) => void;
}

const useMutateReview = ({ openErrorModal }: UseMutateReviewProps) => {
  const queryClient = useQueryClient();

  const reviewMutation = useMutation({
    mutationFn: (formResult: ReviewWritingFormResult) => postReviewApi(formResult),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [REVIEW_QUERY_KEYS.postReview] });
    },
    onError: () => {
      openErrorModal('리뷰 제출에 실패했어요');
    },
  });

  const postReview = (formResult: ReviewWritingFormResult) => {
    reviewMutation.mutate(formResult);
  };

  return { reviewMutation, postReview };
};

export default useMutateReview;
