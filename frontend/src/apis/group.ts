import { INVALID_GROUP_ACCESS_CODE_MESSAGE } from '@/constants';

import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

export interface DataForURL {
  revieweeName: string;
  projectName: string;
}

export const postDataForURL = async (dataForURL: DataForURL) => {
  const response = await fetch(endPoint.postingDataForURL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(dataForURL),
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  const data = await response.json();
  return data;
};

// NOTE: 리뷰 목록 엔드포인트(gettingReviewList)에 요청을 보내고 있지만,
// 요청 성격이 목록을 얻어오는 것이 아닌 유효한 groupAccessCode인지 확인하는 것이므로 group 파일에 작성함
// 단, 해당 엔드포인트에 대한 정상 요청 핸들러가 동작한다면 아래 에러 핸들러는 동작하지 않음
export const getIsValidGroupAccessCodeApi = async (groupAccessCode: string) => {
  const response = await fetch(endPoint.gettingReviewList, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      GroupAccessCode: groupAccessCode,
    },
  });

  if (response.status === 400) throw new Error(INVALID_GROUP_ACCESS_CODE_MESSAGE);
  if (!response.ok) throw new Error(createApiErrorMessage(response.status));

  return response.ok;
};
