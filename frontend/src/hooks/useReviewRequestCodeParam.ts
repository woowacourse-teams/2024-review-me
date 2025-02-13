import { ROUTE_PARAM } from '@/constants';

import useSearchParamAndQuery from './useSearchParamAndQuery';

const useRequestCodeParam = () => {
  const { param } = useSearchParamAndQuery({ paramKey: ROUTE_PARAM.reviewRequestCode });
  if (!param) console.error('리뷰 요청 코드를 찾을 수 없어요.');

  return { reviewRequestCode: param ?? '' };
};

export default useRequestCodeParam;
