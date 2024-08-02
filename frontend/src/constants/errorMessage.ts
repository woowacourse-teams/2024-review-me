interface ApiErrorMessages {
  [key: number]: string;
  serverError: string;
}

export const API_ERROR_MESSAGE: ApiErrorMessages = {
  400: '잘못된 요청이에요.',
  401: '인증을 실패했어요.',
  403: '요청권한이 없어요.',
  404: '요청하신 내용을 찾을 수 없어요.',
  422: '올바르지 않은 데이터 형식이에요.',
  serverError: '서버 오류가 발생했어요.',
};

export const SERVER_ERROR_REGEX = /^5\d{2}$/;

export const ROUTE_ERROR_MESSAGE = '찾으시는 페이지가 없어요.';
