import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

import { CREATED_GROUP_DATA } from '../mockData/createdGroupData';

const postDataForUrl = () => {
  return http.post(endPoint.postingDataForURL, async () => {
    return HttpResponse.json(CREATED_GROUP_DATA, { status: 200 });
  });
};

// NOTE: 에러 테스트용 핸들러
// const postDataForUrl = () => {
//   return http.post(endPoint.postingDataForURL, async () => {
//     return HttpResponse.json({ error: '서버 에러 테스트' }, { status: 500 });
//   });
// };

const groupHandler = [postDataForUrl()];

export default groupHandler;
