import { useLocation, useParams } from 'react-router';

interface UseSearchParamAndQueryProps {
  paramKey: string;
  queryStringKey?: string;
}
/**
 * url에서 원하는 param, queryString의 값을 가져온다.
 * @param paramKey: 가져오고 싶은 param의 key
 * @param queryStringKey: 가져오고 싶은 queryString의 key (옵셔널)
 */
const useSearchParamAndQuery = ({ paramKey, queryStringKey }: UseSearchParamAndQueryProps) => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);

  return {
    param: useParams()[`${paramKey}`],
    queryString: queryStringKey ? queryParams.get(queryStringKey) : null,
  };
};

export default useSearchParamAndQuery;
