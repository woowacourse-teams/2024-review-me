import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';
import { ReviewWritingFormResult } from '@/types';

interface UseMutateReviewProps {
  executeAfterMutateSuccess: () => void;
}
const useMutateReview = ({ executeAfterMutateSuccess }: UseMutateReviewProps) => {
  const queryClient = useQueryClient();

  const reviewMutation = useMutation({
    mutationFn: (formResult: ReviewWritingFormResult) => postReviewApi(formResult),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [REVIEW_QUERY_KEY.postReview] });
      executeAfterMutateSuccess();
    },
    onError: (error) => {
      console.error(error.message);
    },
  });

  const postReview = (formResult: ReviewWritingFormResult) => {
    reviewMutation.mutate(formResult);
  };

  return { ...reviewMutation, postReview };
};

export default useMutateReview;
