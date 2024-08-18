import { http, HttpResponse } from 'msw';

import endPoint, { REVIEW_GROUP_DATA_API_PARAMS, REVIEW_GROUP_DATA_API_URL } from '@/apis/endpoints';

import {
  CREATED_GROUP_DATA,
  INVALID_GROUP_ACCESS_CODE,
  REVIEW_GROUP_DATA,
  VALID_REVIEW_GROUP_REVIEW_REQUEST_CODE,
} from '../mockData/group';

// NOTE: URL 생성 정상 응답
const postDataForUrl = () => {
  return http.post(endPoint.postingDataForURL, async () => {
    return HttpResponse.json(CREATED_GROUP_DATA, { status: 200 });
  });
};

// NOTE: URL 생성 에러 응답
// const postDataForUrl = () => {
//   return http.post(endPoint.postingDataForURL, async () => {
//     return HttpResponse.json({ error: '서버 에러 테스트' }, { status: 500 });
//   });
// };

// NOTE: 확인 코드 정상 응답
const getIsValidGroupAccessCode = () => {
  return http.get(endPoint.gettingReviewList, async () => {
    return HttpResponse.json({ status: 200 });
  });
};

// NOTE: 확인 코드 에러 응답
// const getIsValidGroupAccessCode = () => {
//   return http.get(endPoint.gettingReviewList, async () => {
//     return HttpResponse.json(INVALID_GROUP_ACCESS_CODE, { status: 400 });
//   });
// };

// const getIsValidGroupAccessCode = () => {
//   return http.get(endPoint.gettingReviewList, async () => {
//     return HttpResponse.json({ error: '서버 에러 테스트' }, { status: 500 });
//   });
// };
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
const groupHandler = [postDataForUrl(), getIsValidGroupAccessCode(), getReviewGroupData()];

export default groupHandler;
