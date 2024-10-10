import {
  DetailReviewData,
  ReviewList,
  ReviewWritingFormResult,
  ReviewWritingFormData,
  GroupedSection,
  GroupedReviews,
} from '@/types';

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

interface GetReviewListApi {
  lastReviewId: number | null;
  size: number;
}

export const getReviewListApi = async ({ lastReviewId, size }: GetReviewListApi) => {
  const response = await fetch(endPoint.gettingReviewList(lastReviewId, size), {
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

export const getSectionList = async () => {
  const response = await fetch(endPoint.gettingSectionList, {
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
  return data as GroupedSection;
};

interface GetGroupedReviewsProps {
  sectionId: number;
}

export const getGroupedReviews = async ({ sectionId }: GetGroupedReviewsProps) => {
  const response = await fetch(endPoint.gettingGroupedReviews(sectionId), {
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
  return data as GroupedReviews;
};
