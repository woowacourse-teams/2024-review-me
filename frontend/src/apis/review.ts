import {
  DetailReviewData,
  ReviewList,
  ReviewWritingFormResult,
  ReviewWritingFormData,
  GroupedSection,
  GroupedReviews,
  ReviewInfoData,
  WrittenReviewList,
} from '@/types';

import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

export interface GetInfiniteReviewListApiParams {
  lastReviewId: number | null;
  size: number;
}

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

// 받은 리뷰들에 대한 정보(프로젝트 이름, 리뷰이, 받은 리뷰 개수)
export const getReviewInfoDataApi = async () => {
  const response = await fetch(endPoint.gettingReviewInfoData, {
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
  return data as ReviewInfoData;
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

export const getReviewListApi = async ({ lastReviewId, size }: GetInfiniteReviewListApi) => {
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

export const getWrittenReviewList = async ({ lastReviewId, size }: GetInfiniteReviewListApi) => {
  const response = await fetch(endPoint.gettingWrittenReviewList(lastReviewId, size), {
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
  return data as WrittenReviewList;
};
