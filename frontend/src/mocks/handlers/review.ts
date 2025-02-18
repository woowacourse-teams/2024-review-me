import { DefaultBodyType, http, HttpResponse, StrictRequest } from 'msw';

import endPoint, {
  DETAILED_REVIEW_API_PARAMS,
  DETAILED_REVIEW_API_URL,
  REVIEW_GROUP_API_PARAMS,
  REVIEW_WRITING_API_PARAMS,
  REVIEW_WRITING_API_URL,
  VERSION2,
} from '@/apis/endpoints';
import { DEFAULT_SIZE_PER_PAGE } from '@/constants';
import {
  VALID_REVIEW_REQUEST_CODE,
  DETAILED_REVIEW_MOCK_DATA,
  DETAILED_PAGE_MOCK_API_SETTING_VALUES,
  REVIEW_QUESTION_DATA,
  REVIEW_LIST,
  MOCK_REVIEW_INFO_DATA,
  bothCookie,
  nonMemberOnlyCookie,
  reviewLinks,
  memberOnlyCookie,
} from '@/mocks/mockData';

import { GROUPED_REVIEWS_MOCK_DATA, GROUPED_SECTION_MOCK_DATA } from '../mockData/reviewCollection';
import { WRITTEN_REVIEW_LIST } from '../mockData/writtenReviewList';

import { authorizeWithCookie } from './cookies';
import { paginateDataList } from './pagination';

export const PAGE = {
  firstPageNumber: 1,
  firstPageStartIndex: 0,
};

interface GetInfiniteReviewListApiParams {
  lastReviewId: number | null;
  size: number;
}

const getReviewSummaryInfoData = () => {
  const nonMemberUrl = endPoint.gettingReviewSummaryInfoData(VALID_REVIEW_REQUEST_CODE.nonMember);
  const memberUrl = endPoint.gettingReviewSummaryInfoData(VALID_REVIEW_REQUEST_CODE.member);
  const targetUrl = new RegExp(`^(${nonMemberUrl}|${memberUrl})`);

  return http.get(targetUrl, ({ cookies }) => {
    return authorizeWithCookie({
      cookies,
      validateCookieNames: bothCookie,
      callback: () => HttpResponse.json(MOCK_REVIEW_INFO_DATA),
    });
  });
};

const getDetailedReview = () =>
  http.get(new RegExp(`^${DETAILED_REVIEW_API_URL}/\\d+$`), ({ request, cookies }) => {
    const handleAPI = () => {
      //요청 url에서 reviewId, memberId 추출
      const url = new URL(request.url);
      const urlReviewId = url.pathname.replace(`/${VERSION2}/${DETAILED_REVIEW_API_PARAMS.resource}/`, '');

      const { reviewId } = DETAILED_PAGE_MOCK_API_SETTING_VALUES;
      // 유효한 reviewId, memberId일 경우에만 데이터 반환
      if (Number(urlReviewId) == reviewId) {
        return HttpResponse.json(DETAILED_REVIEW_MOCK_DATA);
      }

      return HttpResponse.json({ error: '잘못된 상세리뷰 요청' }, { status: 404 });
    };

    return authorizeWithCookie({
      cookies,
      validateCookieNames: bothCookie,
      callback: handleAPI,
    });
  });

const getDataToWriteReview = () =>
  http.get(new RegExp(`^${REVIEW_WRITING_API_URL}/${REVIEW_WRITING_API_PARAMS.queryString.write}`), ({ request }) => {
    //요청 url에서 reviewId, memberId 추출
    const url = new URL(request.url);
    const urlRequestCode = url.searchParams.get(REVIEW_WRITING_API_PARAMS.queryString.reviewRequestCode);

    if (VALID_REVIEW_REQUEST_CODE.nonMember === urlRequestCode) {
      return HttpResponse.json(REVIEW_QUESTION_DATA);
    }
    return HttpResponse.json({ error: '잘못된 리뷰 작성 데이터 요청' }, { status: 404 });
  });

// TODO: 추후 getReviewList API에서 리뷰 정보(이름, 개수...)를 내려주지 않는 경우 핸들러도 수정 필요
const getMemberReceivedReviewList = ({ lastReviewId, size }: GetInfiniteReviewListApiParams) => {
  const memberUrl = endPoint.gettingReceivedReviewList({
    lastReviewId,
    size,
    reviewRequestCode: VALID_REVIEW_REQUEST_CODE.member,
  });

  return http.get(memberUrl, ({ request, cookies }) => {
    return authorizeWithCookie({
      cookies,
      validateCookieNames: memberOnlyCookie,
      callback: () => handleReviewListAPI(request, size),
    });
  });
};

const getNonMemberReceivedReviewList = ({ lastReviewId, size }: GetInfiniteReviewListApiParams) => {
  const nonMemberUrl = endPoint.gettingReceivedReviewList({
    lastReviewId,
    size,
    reviewRequestCode: VALID_REVIEW_REQUEST_CODE.nonMember,
  });

  return http.get(nonMemberUrl, ({ request, cookies }) => {
    return authorizeWithCookie({
      cookies,
      validateCookieNames: nonMemberOnlyCookie,
      callback: () => handleReviewListAPI(request, size),
    });
  });
};

// 공통 API 처리 함수
const handleReviewListAPI = (request: StrictRequest<DefaultBodyType>, size: number) => {
  const url = new URL(request.url);

  const lastReviewIdParam = url.searchParams.get('lastReviewId');
  const lastReviewId = lastReviewIdParam === 'null' ? 0 : Number(lastReviewIdParam);

  const { isLastPage, paginatedDataList, lastDataId } = paginateDataList({
    dataList: WRITTEN_REVIEW_LIST.reviews,
    dataId: 'reviewId',
    lastDataId: lastReviewId,
    size: size,
  });

  return HttpResponse.json({
    revieweeName: REVIEW_LIST.revieweeName,
    projectName: REVIEW_LIST.projectName,
    lastReviewId: lastDataId,
    isLastPage: isLastPage,
    reviews: paginatedDataList,
  });
};

const postReview = () =>
  http.post(endPoint.postingReview, () => {
    return HttpResponse.json({ message: 'post 성공' }, { status: 201 });
  });

const getSectionList = () =>
  http.get(endPoint.gettingSectionList, ({ cookies }) => {
    return authorizeWithCookie({
      cookies,
      validateCookieNames: bothCookie,
      callback: () => HttpResponse.json(GROUPED_SECTION_MOCK_DATA),
    });
  });

interface HandleGroupedReviewAPIParams {
  request: StrictRequest<DefaultBodyType>;
  cookies: Record<string, string>;
}

const handleGroupedReviewsAPI = ({ request, cookies }: HandleGroupedReviewAPIParams) => {
  const url = new URL(request.url);
  const sectionId = url.searchParams.get(REVIEW_GROUP_API_PARAMS.queryString.sectionId);
  const { length } = GROUPED_REVIEWS_MOCK_DATA;
  const index = (Number(sectionId) + length) % length;

  return authorizeWithCookie({
    cookies,
    validateCookieNames: bothCookie,
    callback: () => HttpResponse.json(GROUPED_REVIEWS_MOCK_DATA[index]),
  });
};

const getGroupedReviews = (reviewRequestCode: string) => {
  const SECTION_ID = 1;
  const reviewUrl = endPoint.gettingGroupedReviews(reviewRequestCode, SECTION_ID);

  return http.get(reviewUrl, ({ request, cookies }) => {
    return handleGroupedReviewsAPI({ request, cookies });
  });
};

const getWrittenReviewList = ({ lastReviewId, size }: GetInfiniteReviewListApiParams) => {
  return http.get(endPoint.gettingWrittenReviewList(lastReviewId, size), ({ request, cookies }) => {
    const handleAPI = () => {
      const url = new URL(request.url);
      const lastReviewIdParam = url.searchParams.get('lastReviewId');
      const lastReviewId = lastReviewIdParam === 'null' ? 0 : Number(lastReviewIdParam);

      const { isLastPage, paginatedDataList, lastDataId } = paginateDataList({
        dataList: WRITTEN_REVIEW_LIST.reviews,
        dataId: 'reviewId',
        lastDataId: lastReviewId,
      });

      return HttpResponse.json({
        revieweeName: REVIEW_LIST.revieweeName,
        projectName: REVIEW_LIST.projectName,
        lastReviewId: lastDataId,
        isLastPage: isLastPage,
        reviews: paginatedDataList,
      });
    };

    return authorizeWithCookie({
      cookies,
      validateCookieNames: memberOnlyCookie,
      callback: handleAPI,
    });
  });
};

const getReviewLinks = () =>
  http.get(endPoint.gettingReviewLinks, ({ cookies }) => {
    return authorizeWithCookie({
      cookies,
      validateCookieNames: memberOnlyCookie,
      callback: () => HttpResponse.json(reviewLinks),
    });
  });

const reviewHandler = [
  getDetailedReview(),
  getNonMemberReceivedReviewList({ lastReviewId: null, size: DEFAULT_SIZE_PER_PAGE }),
  getMemberReceivedReviewList({ lastReviewId: null, size: DEFAULT_SIZE_PER_PAGE }),
  getDataToWriteReview(),
  getSectionList(),
  getGroupedReviews(VALID_REVIEW_REQUEST_CODE.member),
  getGroupedReviews(VALID_REVIEW_REQUEST_CODE.nonMember),
  getReviewSummaryInfoData(),
  postReview(),
  getReviewLinks(),
  getWrittenReviewList({ lastReviewId: null, size: DEFAULT_SIZE_PER_PAGE }),
];

export default reviewHandler;
