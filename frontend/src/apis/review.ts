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
}
// 상세 리뷰
export const getDetailedReviewApi = async ({ reviewId }: GetDetailedReviewApi) => {
  const response = await fetch(endPoint.gettingDetailedReview(reviewId), {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  const data = await response.json();
  return data as DetailReviewData;
};

export const getReviewListApi = async () => {
  const response = await fetch(endPoint.gettingReviewList, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  const data = await response.json();
  return data as ReviewList;
};
