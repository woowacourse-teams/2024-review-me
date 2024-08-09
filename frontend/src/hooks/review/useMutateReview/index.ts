import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEYS } from '@/constants';
import { ReviewData } from '@/types';

interface PostReviewArgs {
  reviewData: ReviewData;
}

const useMutateReview = () => {
  const queryClient = useQueryClient();

  const reviewMutation = useMutation({
    mutationFn: ({ reviewData }: PostReviewArgs) => postReviewApi({ reviewData }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [REVIEW_QUERY_KEYS.postReview] });
    },
    onError: () => {
      console.error('리뷰 제출에 실패했어요.');
    },
  });

  const postReview = ({ reviewData }: PostReviewArgs) => {
    reviewMutation.mutate({ reviewData });
  };

  return { reviewMutation, postReview };
};

export default useMutateReview;
