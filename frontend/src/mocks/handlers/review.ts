import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

import { DETAILED_REVIEW_MOCK_DATA } from '../mockData/detailedReviewMockData';

const getDetailedReview = () =>
  http.get(endPoint.gettingDetailedReview(0), async ({ request }) => {
    return HttpResponse.json(DETAILED_REVIEW_MOCK_DATA);
  });

const reviewHandler = [getDetailedReview()];

export default reviewHandler;
