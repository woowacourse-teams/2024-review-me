import { http, HttpResponse } from 'msw';

import endPoint, {
  DETAILED_REVIEW_API_PARAMS,
  DETAILED_REVIEW_API_URL,
  REVIEW_WRITING_API_PARAMS,
} from '@/apis/endpoints';

import { DETAILED_REVIEW_MOCK_DATA, DETAILED_PAGE_MOCK_API_SETTING_VALUES } from '../mockData/detailedReviewMockData';
import { REVIEW_LIST } from '../mockData/reviewListMockData';
import { REVIEW_REQUEST_CODE, REVIEW_WRITING_FORM_CARD_DATA } from '../mockData/reviewWritingCardFormData';

export const PAGE = {
  firstPageNumber: 1,
  firstPageStartIndex: 0,
  defaultPageSize: 5,
  additionalPageSize: 2,
};

const getDetailedReview = () =>
  http.get(new RegExp(`^${DETAILED_REVIEW_API_URL}/\\d+$`), async ({ request }) => {
    //요청 url에서 reviewId, memberId 추출
    const url = new URL(request.url);
    const urlReviewId = url.pathname.replace(`/${DETAILED_REVIEW_API_PARAMS.resource}/`, '');
    const urlMemberId = url.searchParams.get(DETAILED_REVIEW_API_PARAMS.queryString.memberId);

    const { reviewId, memberId } = DETAILED_PAGE_MOCK_API_SETTING_VALUES;
    // 유효한 reviewId, memberId일 경우에만 데이터 반환
    if (Number(urlReviewId) == reviewId && Number(urlMemberId) === memberId) {
      return HttpResponse.json(DETAILED_REVIEW_MOCK_DATA);
    }

    return HttpResponse.json({ error: '잘못된 상세리뷰 요청' }, { status: 404 });
  });

const getDataToWriteReview = () =>
  http.get(endPoint.gettingDataToWriteReview(REVIEW_REQUEST_CODE), async ({ request }) => {
    //요청 url에서 reviewId, memberId 추출
    const url = new URL(request.url);
    const urlRequestCode = url.searchParams.get(REVIEW_WRITING_API_PARAMS.queryString.reviewRequestCode);

    if (REVIEW_REQUEST_CODE === urlRequestCode) {
      return HttpResponse.json(REVIEW_WRITING_FORM_CARD_DATA);
    }
    return HttpResponse.json({ error: '잘못된 리뷰 작성 데이터 요청' }, { status: 404 });
  });

const getReviewList = () => {
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
    return HttpResponse.json(REVIEW_LIST);
  });
};

const postReview = () =>
  http.post(endPoint.postingReview, async () => {
    return HttpResponse.json({ message: 'post 성공' }, { status: 201 });
  });

const reviewHandler = [getDetailedReview(), getReviewList(), getDataToWriteReview(), postReview()];

export default reviewHandler;
