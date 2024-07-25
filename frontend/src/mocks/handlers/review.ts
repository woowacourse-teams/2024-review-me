import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

import { DETAILED_REVIEW_MOCK_DATA } from '../mockData/detailedReviewMockData';
import { REVIEW_WRITING_DATA } from '../mockData/reviewWritingData';

const getDetailedReview = () =>
  http.get(endPoint.gettingDetailedReview(0), async ({ request }) => {
    return HttpResponse.json(DETAILED_REVIEW_MOCK_DATA);
  });

const getDataToWriteReview = () =>
  http.get(endPoint.gettingDataToWriteReview(10), async ({ request }) => {
    return HttpResponse.json(REVIEW_WRITING_DATA);
  });

const reviewHandler = [getDetailedReview(), getDataToWriteReview()];

export default reviewHandler;
