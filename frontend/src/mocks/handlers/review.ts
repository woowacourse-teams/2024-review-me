import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

import { DETAILED_REVIEW_MOCK_DATA, DETAILED_PAGE_MOCK_API_SETTING_VALUES } from '../mockData/detailedReviewMockData';
import { REVIEW_PREVIEW_LIST } from '../mockData/reviewPreviewList';
import { REVIEW_WRITING_DATA } from '../mockData/reviewWritingData';

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

const getDataToWriteReview = () =>
  http.get(endPoint.gettingDataToWriteReview(10), async ({ request }) => {
    return HttpResponse.json(REVIEW_WRITING_DATA);
  });

const getReviewPreviewList = () =>
  http.get(endPoint.gettingReviewList(1, 3, 1), async ({ request }) => {
    return HttpResponse.json(REVIEW_PREVIEW_LIST);
  });

const reviewHandler = [getDetailedReview(), getReviewPreviewList(), getDataToWriteReview()];

export default reviewHandler;
