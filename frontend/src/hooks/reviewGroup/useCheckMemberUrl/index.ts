import useSearchParamAndQuery from '@/hooks/useSearchParamAndQuery';

import useGetReviewGroupData from '../useGetReviewGroupData';

/**
 * 현재 URL이 회원이 생성한 URL인지 확인하는 훅
 */
const useCheckMemberUrl = () => {
  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  if (!reviewRequestCode) throw new Error('올바르지 않은 리뷰 요청 코드예요');

  const { data: reviewGroupData } = useGetReviewGroupData({ reviewRequestCode });
  const isMemberUrl = reviewGroupData.revieweeId !== null;

  return { isMemberUrl, reviewRequestCode };
};

export default useCheckMemberUrl;
