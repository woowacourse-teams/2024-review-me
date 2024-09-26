import { INVALID_REVIEW_PASSWORD_MESSAGE } from '@/constants';
import { PasswordResponse, ReviewGroupData } from '@/types';

import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

export interface DataForReviewRequestCode {
  revieweeName: string;
  projectName: string;
  groupAccessCode: string;
}

export const postDataForReviewRequestCodeApi = async (dataForReviewRequestCode: DataForReviewRequestCode) => {
  const response = await fetch(endPoint.postingDataForReviewRequestCode, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(dataForReviewRequestCode),
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
export const postPasswordValidationApi = async ({
  groupAccessCode,
  reviewRequestCode,
}: GetPasswordValidationApiParams): Promise<PasswordResponse> => {
  const response = await fetch(endPoint.checkingPassword, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ reviewRequestCode, groupAccessCode }),
    credentials: 'include',
  });

  // 비밀번호 인증 성공 (204: 별도 응답 데이터 없음)
  if (response.status === 204) {
    return { status: 'valid' };
  }

  // 유효하지 않은 비밀번호 (401)
  if (response.status === 401) {
    return { status: 'invalid', error: new Error(INVALID_REVIEW_PASSWORD_MESSAGE) };
  }

  // 유효하지 않은 비밀번호 외의 다른 에러 상황
  return { status: 'error', error: new Error(createApiErrorMessage(response.status)) };
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
