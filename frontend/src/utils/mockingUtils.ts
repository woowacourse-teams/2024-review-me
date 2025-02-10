import { DefaultBodyType, StrictRequest } from 'msw';

import { API_ERROR_MESSAGE } from '@/constants';

/**
 * api 요청 body를 탐색해 결과를 반환하는 함수
 * @param request mock handler의 request
 * @returns request body가 있다면 경우 body 객체 반환, 그렇지 않을 경우 Error 반환
 */
export const getRequestBody = async (request: StrictRequest<DefaultBodyType>) => {
  // request body의 존재 검증
  if (!request.body) return new Error(API_ERROR_MESSAGE[400]);

  const rawBody = await request.body.getReader().read();
  const textDecoder = new TextDecoder();
  const bodyText = textDecoder.decode(rawBody.value);

  return JSON.parse(bodyText);
};
