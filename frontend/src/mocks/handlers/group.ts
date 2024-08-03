import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

import { CREATED_GROUP_DATA } from '../mockData/createdGroupData';

const postCreatedGroupData = () => {
  return http.post(
    endPoint.postingCreatedGroupData,
    async () => {
      return HttpResponse.json(CREATED_GROUP_DATA, { status: 200 });
    },
  );
};

// const postCreatedGroupData = () =>
//   http.post(endPoint.postingCreatedGroupData, async (req, res, ctx) => {
//     return HttpResponse.json(CREATED_GROUP_DATA, { status: 200 });
//   });

const groupHandler = [postCreatedGroupData()];

export default groupHandler;
