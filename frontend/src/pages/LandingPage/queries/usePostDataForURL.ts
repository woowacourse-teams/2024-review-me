import { useMutation, useQueryClient } from '@tanstack/react-query';

import { DataForURL, postDataForURLApi } from '@/apis/group';
import { GROUP_QUERY_KEY } from '@/constants';

const usePostDataForURL = () => {
  const queryClient = useQueryClient();

  const { mutate } = useMutation({
    mutationFn: (dataForURL: DataForURL) => postDataForURLApi(dataForURL),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [GROUP_QUERY_KEY.dataForURL] });
    },
    onError: (error) => {
      console.error(error.message);
    },
  });

  return {
    mutate,
  };
};

export default usePostDataForURL;
