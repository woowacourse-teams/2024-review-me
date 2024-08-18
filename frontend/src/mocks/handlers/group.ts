import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

import { CREATED_REVIEW_REQUEST_CODE } from '../mockData/group';

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

const groupHandler = [postDataForReviewRequestCode()];

export default groupHandler;
