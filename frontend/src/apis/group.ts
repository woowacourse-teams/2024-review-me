import { INVALID_REVIEW_PASSWORD_MESSAGE } from '@/constants';
import { PasswordResponse, ReviewGroupData } from '@/types';

import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

//리뷰 그룹 생성
export interface DataForURL {
  revieweeName: string;
  projectName: string;
}

export const postDataForURLApi = async (dataForURL: DataForURL) => {
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

//리뷰 비밀번호
export interface GetPasswordValidationApiParams {
  groupAccessCode: string;
  reviewRequestCode: string;
}
/**
 * @param groupAccessCode L 비밀번호
 */
export const getPasswordValidationApi = async ({
  groupAccessCode,
  reviewRequestCode,
}: GetPasswordValidationApiParams) => {
  const response = await fetch(endPoint.gettingPasswordValidation(reviewRequestCode), {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      GroupAccessCode: groupAccessCode,
    },
  });
  //요청 실패
  if (response.status === 401) return new Error(INVALID_REVIEW_PASSWORD_MESSAGE);

  if (!response.ok) return new Error(createApiErrorMessage(response.status));

  //요청성공
  const data = await response.json();

  return data as PasswordResponse;
};

//리뷰 그룹 정보
export const getReviewGroupDataApi = async (reviewRequestCode: string) => {
  const response = await fetch(endPoint.gettingReviewGroupData(reviewRequestCode), {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });

  if (!response.ok) throw new Error(createApiErrorMessage(response.status));

  const data = await response.json();

  return data as ReviewGroupData;
};
