import { useSuspenseQuery } from '@tanstack/react-query';

import { getDataToWriteReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEYS } from '@/constants';
import { ReviewWritingFrom } from '@/types';

interface UseGetDataToWriteProps {
  reviewRequestCode: string | undefined;
}
const useGetDataToWrite = ({ reviewRequestCode }: UseGetDataToWriteProps) => {
  const fetchReviewFormData = async (reviewRequestCode: string | undefined) => {
    if (!reviewRequestCode) throw new Error('reviewRequestCode가 undefined에요.');

    const result = await getDataToWriteReviewApi(reviewRequestCode);
    return result;
  };

  const result = useSuspenseQuery<ReviewWritingFrom>({
    queryKey: [REVIEW_QUERY_KEYS.writingReviewInfo, reviewRequestCode],
    queryFn: () => fetchReviewFormData(reviewRequestCode),
  });

  return result;
};

export default useGetDataToWrite;
