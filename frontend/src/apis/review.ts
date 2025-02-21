import {
  DetailReviewData,
  ReviewList,
  ReviewWritingFormResult,
  ReviewWritingFormData,
  GroupedSection,
  GroupedReviews,
  ReviewInfoData,
  ReviewLinks,
  WrittenReviewList,
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
    credentials: 'include',
    body: JSON.stringify(formResult),
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }

  return;
};

// 받은 리뷰들에 대한 정보(프로젝트 이름, 리뷰이, 받은 리뷰 개수)
export const getReviewSummaryInfoDataApi = async (reviewRequestCode: string) => {
  const response = await fetch(endPoint.gettingReviewSummaryInfoData(reviewRequestCode), {
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

interface GetDetailedReviewApiParams {
  reviewId: number;
}
// 상세 리뷰
export const getDetailedReviewApi = async ({ reviewId }: GetDetailedReviewApiParams) => {
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

interface GetReceivedReviewListApiParams {
  reviewRequestCode: string;
  lastReviewId: number | null;
  size: number;
}

export const getReceivedReviewListApi = async (props: GetReceivedReviewListApiParams) => {
  const response = await fetch(endPoint.gettingReceivedReviewList(props), {
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
  reviewRequestCode: string;
  sectionId: number;
}

export const getGroupedReviews = async ({ reviewRequestCode, sectionId }: GetGroupedReviewsProps) => {
  const response = await fetch(endPoint.gettingGroupedReviews(reviewRequestCode, sectionId), {
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

export const getReviewLinksApi = async () => {
  const response = await fetch(endPoint.gettingReviewLinks, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  });

  if (!response.ok) throw new Error(createApiErrorMessage(response.status));

  const data = await response.json();
  return data as ReviewLinks;
};

export interface GetWrittenReviewListApiParams {
  lastReviewId: number | null;
  size: number;
}

export const getWrittenReviewListApi = async ({ lastReviewId, size }: GetWrittenReviewListApiParams) => {
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
