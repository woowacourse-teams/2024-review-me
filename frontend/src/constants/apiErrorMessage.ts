interface ApiErrorMessages {
  [key: number]: string;
  serverError: string;
}

export const API_ERROR_MESSAGE: ApiErrorMessages = {
  400: '유효하지 않은 요청 형식이에요.',
  401: '인증을 실패했어요.',
  403: '요청권한이 없어요.',
  404: '요청한 페이지를 찾을 수 없어요.',
  422: '요청이 잘못되었거나 비즈니스 로직을 위반했어요.',
  serverError: '서버 오류가 발생했어요.',
};

export const SERVER_ERROR_REGEX = /^5\d{2}$/;
