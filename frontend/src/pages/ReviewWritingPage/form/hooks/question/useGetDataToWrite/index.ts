import { useSuspenseQuery } from '@tanstack/react-query';

import { getDataToWriteReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';
import { ReviewWritingFormData } from '@/types';

interface UseGetDataToWriteProps {
  reviewRequestCode: string | undefined;
}
const useGetDataToWrite = ({ reviewRequestCode }: UseGetDataToWriteProps) => {
  const fetchReviewFormData = async (reviewRequestCode: string | undefined) => {
    if (!reviewRequestCode) throw new Error('reviewRequestCode가 유효하지 않아요');

    const result = await getDataToWriteReviewApi(reviewRequestCode);
    return result;
  };

  const result = useSuspenseQuery<ReviewWritingFormData>({
    queryKey: [REVIEW_QUERY_KEY.writingReviewInfo, reviewRequestCode],
    queryFn: () => fetchReviewFormData(reviewRequestCode),
    staleTime: 5 * 60 * 1000,
  });

  return result;
};

export default useGetDataToWrite;
