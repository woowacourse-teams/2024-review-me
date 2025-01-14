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

  const mutation = useMutation({
    mutationFn: (dataForReviewRequestCode: DataForReviewRequestCode) =>
      postDataForReviewRequestCodeApi(dataForReviewRequestCode),

    onMutate: () => {
      if (mutation.isPending) return;
    },
    onSuccess: (data) => {
      queryClient.invalidateQueries({ queryKey: [GROUP_QUERY_KEY.dataForReviewRequestCode] });
      handleAPISuccess(data);
    },
    onError: handleAPIError,
  });

  return mutation;
};

export default usePostDataForReviewRequestCode;
