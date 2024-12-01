import { STORED_DATA_NAME } from '@/constants';
import { useSearchParamAndQuery } from '@/hooks';

const useDeleteReviewInLocalStorage = () => {
  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });
  
  const deleteReviewDataInLocalStorage = (key: keyof typeof STORED_DATA_NAME) => {
    localStorage.removeItem(`${STORED_DATA_NAME[key]}_${reviewRequestCode}`);
  };

  const deleteAllReviewDataInLocalStorage = () => {
    Object.values(STORED_DATA_NAME).forEach((key) => {
      localStorage.removeItem(`${STORED_DATA_NAME[key]}_${reviewRequestCode}`);
    });
  };

  return {
    deleteReviewDataInLocalStorage,
    deleteAllReviewDataInLocalStorage,
  };
};

export default useDeleteReviewInLocalStorage;
