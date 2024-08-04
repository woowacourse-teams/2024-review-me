import { useMutation, useQueryClient } from '@tanstack/react-query';

import { DataForURL, postDataForURL } from '@/apis/group';
import { GROUP_QUERY_KEY } from '@/constants';

const usePostDataForURL = () => {
  const queryClient = useQueryClient();

  const data = useMutation({
    mutationFn: (dataForURL: DataForURL) => postDataForURL(dataForURL),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [GROUP_QUERY_KEY.dataForURL] });
    },
    onError: (error) => {
      console.error(error.message);
    },
  });

  return data;
};

export default usePostDataForURL;
