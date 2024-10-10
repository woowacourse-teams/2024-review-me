import { useSuspenseQuery } from '@tanstack/react-query';

import { getGroupedReviews } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';
import { GroupedReviews } from '@/types';

interface UseGetGroupedReviewsProps {
  sectionId: number;
}

const useGetGroupedReviews = ({ sectionId }: UseGetGroupedReviewsProps) => {
  const fetchGroupedReviews = async () => {
    const result = await getGroupedReviews({ sectionId });
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
