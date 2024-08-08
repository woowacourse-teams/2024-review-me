import { useSuspenseQuery } from '@tanstack/react-query';

import { getDataToWriteReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEYS } from '@/constants';
import { WritingReviewInfoData } from '@/types';

interface UseGetDataToWriteProps {
  reviewRequestCode: string;
}

interface FetchDataToWriteParams {
  reviewRequestCode: string;
}

const useGetDataToWrite = ({ reviewRequestCode }: UseGetDataToWriteProps) => {
  const fetchDataToWrite = async ({ reviewRequestCode }: FetchDataToWriteParams) => {
    const result = await getDataToWriteReviewApi(reviewRequestCode);
    return result;
  };

  const { data: dataToWrite } = useSuspenseQuery<WritingReviewInfoData>({
    queryKey: [REVIEW_QUERY_KEYS.writingReviewInfo],
    queryFn: () => fetchDataToWrite({ reviewRequestCode }),
  });

  return dataToWrite;
};

export default useGetDataToWrite;
