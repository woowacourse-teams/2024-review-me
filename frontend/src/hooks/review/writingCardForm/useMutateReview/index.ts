import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEYS } from '@/constants';
import { ReviewWritingFormResult } from '@/types';

const useMutateReview = () => {
  const queryClient = useQueryClient();

  const reviewMutation = useMutation({
    mutationFn: (formResult: ReviewWritingFormResult) => postReviewApi(formResult),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [REVIEW_QUERY_KEYS.postReview] });
    },
  });

  const postReview = (formResult: ReviewWritingFormResult) => {
    reviewMutation.mutate(formResult);
  };

  return { ...reviewMutation, postReview };
};

export default useMutateReview;
