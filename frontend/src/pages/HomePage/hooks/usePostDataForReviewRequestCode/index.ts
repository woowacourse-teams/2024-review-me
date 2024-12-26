import { useMutation, useQueryClient } from '@tanstack/react-query';

import { DataForReviewRequestCode, postDataForReviewRequestCodeApi } from '@/apis/group';
import { GROUP_QUERY_KEY } from '@/constants';
export interface UsePostDataForReviewRequestCodeProps {
  handleAPISuccess: (data: any) => void;
  handleAPIError: (error: Error) => void;
}

const usePostDataForReviewRequestCode = ({
  handleAPIError,
  handleAPISuccess,
}: UsePostDataForReviewRequestCodeProps) => {
  const queryClient = useQueryClient();
  const mutationFn = async (dataForReviewRequestCode: DataForReviewRequestCode) => {
    try {
      return await postDataForReviewRequestCodeApi(dataForReviewRequestCode);
    } catch (error) {
      console.error('Mutation function error:', error);
      return Promise.reject(error); // Promise로 거부를 반환하여 onError로 전달
    }
  };

  const { mutate, isPending, isError } = useMutation({
    mutationFn,

    onMutate: () => {
      if (isPending) return;
    },
    onSuccess: (data) => {
      queryClient.invalidateQueries({ queryKey: [GROUP_QUERY_KEY.dataForReviewRequestCode] });
      handleAPISuccess(data);
    },
    onError: handleAPIError,
  });

  return {
    mutate,
    isError,
    isPending,
  };
};

export default usePostDataForReviewRequestCode;
