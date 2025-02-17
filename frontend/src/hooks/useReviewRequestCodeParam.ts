import { ROUTE_PARAM } from '@/constants';

import useSearchParamAndQuery from './useSearchParamAndQuery';

const useRequestCodeParam = () => {
  const { param } = useSearchParamAndQuery({ paramKey: ROUTE_PARAM.reviewRequestCode });
  if (!param) throw new Error('유효하지 않은 리뷰 요청 코드예요');

  return { reviewRequestCode: param };
};

export default useRequestCodeParam;
