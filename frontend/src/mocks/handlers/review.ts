import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

import {
  DETAILED_REVIEW_MOCK_DATA,
  DETAILED_PAGE_MOCK_API_SETTING_VALUES,
  DETAILED_PAGE_ERROR_API_VALUES,
} from '../mockData/detailedReviewMockData';
import { REVIEW_PREVIEW_LIST } from '../mockData/reviewPreviewList';
import { REVIEW_WRITING_DATA } from '../mockData/reviewWritingData';

export const PAGE = {
  firstPageNumber: 1,
  firstPageStartIndex: 0,
  defaultPageSize: 5,
  additionalPageSize: 2,
};

const getDetailedReview = () =>
  http.get(
    endPoint.gettingDetailedReview(
      DETAILED_PAGE_MOCK_API_SETTING_VALUES.reviewId,
      DETAILED_PAGE_MOCK_API_SETTING_VALUES.memberId,
    ),
    async () => {
      return HttpResponse.json(DETAILED_REVIEW_MOCK_DATA);
    },
  );

const getWrongDetailReview = () =>
  http.get(
    endPoint.gettingDetailedReview(DETAILED_PAGE_ERROR_API_VALUES.reviewId, DETAILED_PAGE_ERROR_API_VALUES.memberId),
    async () => {
      return HttpResponse.json({ error: '잘못된 상세리뷰 요청' }, { status: 404 });
    },
  );

const getDataToWriteReview = () =>
  http.get(endPoint.gettingDataToWriteReview(10), async ({ request }) => {
    return HttpResponse.json(REVIEW_WRITING_DATA);
  });

const getReviewPreviewList = () => {
  return http.get(endPoint.gettingReviewList, async ({ request }) => {
    // const url = new URL(request.url);

    // const lastReviewId = Number(url.searchParams.get('lastReviewId'));

    // const isFirstPage = lastReviewId === 0;
    // const limit = isFirstPage ? PAGE.defaultPageSize : PAGE.additionalPageSize;
    // const startIndex = isFirstPage
    //   ? PAGE.firstPageStartIndex
    //   : REVIEW_PREVIEW_LIST.reviews.findIndex((review) => review.id === lastReviewId) + 1;

    // const endIndex = startIndex + limit;

    // const paginatedReviews = REVIEW_PREVIEW_LIST.reviews.slice(startIndex, endIndex);

    // const isLastPage = endIndex >= REVIEW_PREVIEW_LIST.reviews.length;

    // return HttpResponse.json({
    //   size: paginatedReviews.length,
    //   lastReviewId: isLastPage ? null : lastReviewId + limit,
    //   reviews: paginatedReviews,
    // });
    return HttpResponse.json(REVIEW_PREVIEW_LIST);
  });
};

const reviewHandler = [getDetailedReview(), getWrongDetailReview(), getReviewPreviewList(), getDataToWriteReview()];

export default reviewHandler;
