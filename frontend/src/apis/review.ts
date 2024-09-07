import { DetailReviewData, ReviewList, ReviewWritingFormResult, ReviewWritingFormData } from '@/types';

import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

export const getDataToWriteReviewApi = async (reviewRequestCode: string) => {
  const response = await fetch(endPoint.gettingDataToWriteReview(reviewRequestCode), {
    method: 'GET',
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  const data = await response.json();
  return data as ReviewWritingFormData;
};

export const postReviewApi = async (formResult: ReviewWritingFormResult) => {
  const response = await fetch(endPoint.postingReview, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(formResult),
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  return;
};

interface GetDetailedReviewApi {
  reviewId: number;
  groupAccessCode: string;
  reviewRequestCode: string;
}
// 상세 리뷰
export const getDetailedReviewApi = async ({ reviewId, groupAccessCode, reviewRequestCode }: GetDetailedReviewApi) => {
  const response = await fetch(endPoint.gettingDetailedReview(reviewId, reviewRequestCode), {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      GroupAccessCode: groupAccessCode,
    },
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  const data = await response.json();
  return data as DetailReviewData;
};

export const getReviewListApi = async (groupAccessCode: string, reviewRequestCode: string) => {
  const response = await fetch(endPoint.gettingReviewList(reviewRequestCode), {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      GroupAccessCode: groupAccessCode,
    },
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  const data = await response.json();
  return data as ReviewList;
};
