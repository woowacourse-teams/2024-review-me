import { API_ERROR_MESSAGE, SERVER_ERROR_REGEX } from '@/constants';

const createApiErrorMessage = (statusCode: number) => {
  const isServerError = SERVER_ERROR_REGEX.test(statusCode.toString());

  if (isServerError) return API_ERROR_MESSAGE.serverError;

  if (statusCode in API_ERROR_MESSAGE) return API_ERROR_MESSAGE[statusCode];
};

export default createApiErrorMessage;
