import { useMutation, useQueryClient } from '@tanstack/react-query';

import { DataForReviewRequestCode, postDataForReviewRequestCodeApi } from '@/apis/group';
import { GROUP_QUERY_KEY } from '@/constants';

const usePostDataForReviewRequestCode = () => {
  const queryClient = useQueryClient();

  const { mutate, isSuccess, data } = useMutation({
    mutationFn: (dataForReviewRequestCode: DataForReviewRequestCode) =>
      postDataForReviewRequestCodeApi(dataForReviewRequestCode),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [GROUP_QUERY_KEY.dataForReviewRequestCode] });
    },
    onError: (error) => {
      console.error(error.message);
    },
  });

  return {
    mutate,
    isSuccess,
    data,
  };
};

export default usePostDataForReviewRequestCode;
