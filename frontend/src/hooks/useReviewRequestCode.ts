import { useLocation } from 'react-router';

const useReviewRequestCode = () => {
  const location = useLocation();
  const params = location.pathname.split('/');
  const reviewRequestCode = params.slice(-1).toString();

  return { reviewRequestCode };
};

export default useReviewRequestCode;
