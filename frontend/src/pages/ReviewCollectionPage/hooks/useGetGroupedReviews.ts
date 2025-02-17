import { useSuspenseQuery } from '@tanstack/react-query';

import { getGroupedReviews } from '@/apis/review';
import { REVIEW_QUERY_KEY, SESSION_STORAGE_KEY } from '@/constants';
import { GroupedReviews } from '@/types';

interface UseGetGroupedReviewsProps {
  reviewRequestCode: string;
  sectionId: number;
}

const useGetGroupedReviews = ({ reviewRequestCode, sectionId }: UseGetGroupedReviewsProps) => {
  const fetchGroupedReviews = async () => {
    const result = await getGroupedReviews({ reviewRequestCode, sectionId });
    sessionStorage.setItem(SESSION_STORAGE_KEY.currentReviewCollectionSectionId, sectionId.toString());
    return result;
  };

  const result = useSuspenseQuery<GroupedReviews>({
    queryKey: [REVIEW_QUERY_KEY.groupedReviews, sectionId],
    queryFn: () => fetchGroupedReviews(),
    staleTime: 1 * 60 * 1000,
  });

  return result;
};

export default useGetGroupedReviews;
