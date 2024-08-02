import { DetailReviewData, ReviewData, WritingReviewInfoData } from '@/types';

import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

export const getDataToWriteReviewApi = async (reviewerGroupId: number) => {
  const response = await fetch(endPoint.gettingDataToWriteReview(reviewerGroupId), {
    method: 'GET',
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  const data = await response.json();
  return data as WritingReviewInfoData;
};

export const postReviewApi = async ({ reviewData }: { reviewData: ReviewData }) => {
  const response = await fetch(endPoint.postingReview, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(reviewData),
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  const data = await response.json();
  return data;
};

// 상세 리뷰
export const getDetailedReviewApi = async ({ reviewId, memberId }: { reviewId: number; memberId: number }) => {
  const response = await fetch(endPoint.gettingDetailedReview(reviewId, memberId), {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  const data = await response.json();
  return data as DetailReviewData;
};

export const getReviewListApi = async (groupAccessCode: string) => {
  const response = await fetch(endPoint.gettingReviewList, {
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
  return data;
};

export const checkGroupAccessCodeApi = async (groupAccessCode: string) => {
  const response = await fetch(endPoint.gettingReviewList, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      GroupAccessCode: groupAccessCode,
    },
  });

  return response.ok;
};
