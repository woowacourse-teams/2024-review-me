import { useSuspenseQuery } from '@tanstack/react-query';

import { getSectionList } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';
import { GroupedSection } from '@/types';

const useGetSectionList = () => {
  const fetchSectionList = async () => {
    const result = await getSectionList();
    return result;
  };

  const result = useSuspenseQuery<GroupedSection>({
    queryKey: [REVIEW_QUERY_KEY.sectionList],
    queryFn: () => fetchSectionList(),
    staleTime: 60 * 60 * 1000,
  });

  return result;
};

export default useGetSectionList;
