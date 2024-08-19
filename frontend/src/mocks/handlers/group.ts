import { http, HttpResponse } from 'msw';

import endPoint, { REVIEW_GROUP_DATA_API_PARAMS, REVIEW_GROUP_DATA_API_URL } from '@/apis/endpoints';
import { API_ERROR_MESSAGE } from '@/constants';
import { PasswordResponse } from '@/types';

import {
  CREATED_REVIEW_REQUEST_CODE,
  REVIEW_GROUP_DATA,
  VALID_REVIEW_GROUP_REVIEW_REQUEST_CODE,
  VALIDATED_PASSWORD,
} from '../mockData/group';

// NOTE: reviewRequestCode 생성 정상 응답
const postDataForReviewRequestCode = () => {
  return http.post(endPoint.postingDataForReviewRequestCode, async () => {
    return HttpResponse.json(CREATED_REVIEW_REQUEST_CODE, { status: 200 });
  });
};

// NOTE: reviewRequestCode 생성 에러 응답
// const postDataForReviewRequestCode = () => {
//   return http.post(endPoint.postingDataForReviewRequestCode, async () => {
//     return HttpResponse.json({ error: '서버 에러 테스트' }, { status: 500 });
//   });
// };

const postPassWordValidation = () => {
  return http.post(endPoint.passwordChecking, async ({ request }) => {
    // password가 있는 경우
    if (!request.body) return HttpResponse.json({ error: API_ERROR_MESSAGE[400] }, { status: 400 });

    const rawBody = await request.body.getReader().read();
    const textDecoder = new TextDecoder();
    const bodyText = textDecoder.decode(rawBody.value);

    // password가 유효한 패스워드인지 확인
    const { reviewRequestCode, groupAccessCode: password } = JSON.parse(bodyText);

    // reviewRequestCode가 없는 경우
    //password가 없을 경우
    if (!password || !reviewRequestCode) return HttpResponse.json({ error: API_ERROR_MESSAGE[400] }, { status: 400 });
    // password가 있는 경우

    const response: PasswordResponse = {
      hasAccess: password === VALIDATED_PASSWORD,
    };

    return HttpResponse.json(response, { status: 200 });
  });
};

/**
 * 리뷰 연결 페이지에서 리뷰이 이름, 프로젝트 이름을 가져오는 목 핸들러
 */
// 예시 출력
const getReviewGroupData = () => {
  return http.get(new RegExp(`^${REVIEW_GROUP_DATA_API_URL}?`), async ({ request }) => {
    const url = new URL(request.url);
    const params = new URLSearchParams(url.search);
    const { queryString } = REVIEW_GROUP_DATA_API_PARAMS;
    // 리뷰 그룹 정보에 대한 요청인지 확인
    if (!params.has(queryString.reviewRequestCode)) return;

    //요청 url에서 reviewRequestCode 추출
    const reviewRequestCode = params.get(queryString.reviewRequestCode);

    if (reviewRequestCode === VALID_REVIEW_GROUP_REVIEW_REQUEST_CODE) {
      return HttpResponse.json(REVIEW_GROUP_DATA);
    }

    return HttpResponse.json({ error: '잘못된 리뷰 그룹 데이터 요청' }, { status: 404 });
  });
};
const groupHandler = [postDataForReviewRequestCode(), getReviewGroupData(), postPassWordValidation()];

export default groupHandler;
