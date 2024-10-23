import { API_ERROR_MESSAGE } from '@/constants';

const retryQuery = (failureCount: number, error: Error): boolean => {
  const { message } = error;
  const isServerError = message === API_ERROR_MESSAGE.serverError;

  // Fetch API로 인해 발생한 오류인지 확인
  // 500번대 에러이면 한 번 더 재시도
  if (isServerError) return failureCount < 1;

  return false; // 그 외의 경우 재시도하지 않음
};

export default retryQuery;
