import { useQuery } from '@tanstack/react-query';
import { useEffect } from 'react';

import { postPasswordValidationApi, GetPasswordValidationApiParams } from '@/apis/group';
import { GROUP_QUERY_KEY, INVALID_REVIEW_PASSWORD_MESSAGE } from '@/constants';
import { PasswordResponse } from '@/types';

interface UseCheckPasswordValidationProps extends GetPasswordValidationApiParams {
  onSuccess: () => void;
  onError: (error: Error) => void;
}

/**
 * 비밀번호 조회 http 요청을 통해 비밀번호의 유효성을 판단한 후, 비밀번호의 유효성 여부에 따른 액션을 실행함
 * @param onSuccess: 비밀번호가 유효할 경우 하는 다음 액션
 * @param onError: 비밀번호가 유효하지 않을 경우 하는 다음 액션
 */
const useCheckPasswordValidation = ({
  groupAccessCode,
  reviewRequestCode,
  onSuccess,
  onError,
}: UseCheckPasswordValidationProps) => {
  const fetchPasswordValidation = async (params: GetPasswordValidationApiParams) => {
    const result = await postPasswordValidationApi(params);
    return result;
  };

  const result = useQuery<PasswordResponse>({
    queryKey: [GROUP_QUERY_KEY.password, groupAccessCode, reviewRequestCode],
    queryFn: () => fetchPasswordValidation({ groupAccessCode, reviewRequestCode }),
    enabled: !!groupAccessCode && !!reviewRequestCode,
    staleTime: 1000 * 60 * 5,
  });

  /**
   * 비밀번호 조회 결과에 따른 액션 핸들러
   */
  const handleResult = () => {
    const { data, isLoading } = result;

    if (isLoading) return;

    // 요청 성공
    if (data?.status === 'valid') {
      onSuccess();
    }
    // 유효하지 않은 비밀번호
    if (data?.status === 'invalid') {
      return onError(new Error(INVALID_REVIEW_PASSWORD_MESSAGE));
    }
    // 에러 처리
    if (data?.status === 'error' && data?.error) {
      return onError(data.error);
    }
  };

  useEffect(() => {
    handleResult();
  }, [result.status]);

  return result;
};

export default useCheckPasswordValidation;
