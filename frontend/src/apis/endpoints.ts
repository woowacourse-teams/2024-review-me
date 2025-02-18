export const VERSION2 = 'v2';

const getDevServerUrl = () => {
  const serverUrlList = process.env.API_BASE_URL?.split('//');
  if (!serverUrlList) return '';
  return serverUrlList[0] + '//' + 'dev-' + serverUrlList[1];
};

const getServerUrl = () => {
  const isProduction = window?.location.hostname === 'review-me.page';
  const devServerUrl = getDevServerUrl();

  return isProduction ? process.env.API_BASE_URL : devServerUrl;
};

const serverUrl = getServerUrl();

export const DETAILED_REVIEW_API_PARAMS = {
  resource: 'reviews',
  queryString: {
    memberId: 'memberId',
  },
};

export const REVIEW_RECEIVED_LIST_API_PARAMS = {
  resource: 'reviews/received',
};

export const REVIEW_WRITING_API_PARAMS = {
  resource: 'reviews',
  queryString: {
    write: 'write',
    reviewRequestCode: 'reviewRequestCode',
  },
};

export const REVIEW_GROUP_DATA_API_PARAMS = {
  queryString: {
    reviewRequestCode: 'reviewRequestCode',
  },
};

export const REVIEW_GROUP_API_PARAMS = {
  queryString: {
    sectionId: 'sectionId',
  },
};

export const WRITTEN_REVIEW_PARAMS = {
  resource: 'reviews/authored',
  queryString: {
    lastReviewId: 'lastReviewId',
    size: 'size',
  },
};

export const REVIEW_WRITING_API_URL = `${serverUrl}/${VERSION2}/${REVIEW_WRITING_API_PARAMS.resource}`;
export const DETAILED_REVIEW_API_URL = `${serverUrl}/${VERSION2}/${DETAILED_REVIEW_API_PARAMS.resource}`;
export const REVIEW_GROUPS_BASIC_API_URL = `${serverUrl}/${VERSION2}/groups`;
export const WRITTEN_REVIEW_LIST_API_URL = `${serverUrl}/${VERSION2}/${WRITTEN_REVIEW_PARAMS.resource}`;

interface GetReviewListEndpointParams {
  lastReviewId: number | null;
  size: number;
  reviewRequestCode: string;
}

/*
NOTE: reviewGroupId로 변경될 endpoint
섹션 별 받은 리뷰 모아보기 GET /groups/{id}/reviews/gather
받은 리뷰 목록 조회 GET /groups/{id}/reviews/received
받은 리뷰 요약 조회 GET  /groups/{id}/reviews/summary
하이라이트 수정 POST /highlight :: request 에 reviewGroupId 포함되어야 함
*/
const endPoint = {
  postingReview: `${serverUrl}/${VERSION2}/reviews`,
  gettingReviewSummaryInfoData: (reviewRequestCode: string) =>
    `${REVIEW_GROUPS_BASIC_API_URL}/${reviewRequestCode}/reviews/summary`,
  gettingDetailedReview: (reviewId: number) => `${DETAILED_REVIEW_API_URL}/${reviewId}`,
  gettingDataToWriteReview: (reviewRequestCode: string) =>
    `${REVIEW_WRITING_API_URL}/${REVIEW_WRITING_API_PARAMS.queryString.write}?${REVIEW_WRITING_API_PARAMS.queryString.reviewRequestCode}=${reviewRequestCode}`,
  gettingReceivedReviewList: ({ lastReviewId, size, reviewRequestCode }: GetReviewListEndpointParams) => {
    const basicUrl = `${REVIEW_GROUPS_BASIC_API_URL}/${reviewRequestCode}/${REVIEW_RECEIVED_LIST_API_PARAMS.resource}`;
    if (lastReviewId) {
      return `${basicUrl}?lastReviewId=${lastReviewId}&size=${size}`;
    }
    return `${basicUrl}?size=${size}`;
  },
  postingDataForReviewRequestCode: REVIEW_GROUPS_BASIC_API_URL,
  checkingReviewRequestPassword: `${serverUrl}/${VERSION2}/auth/review-group`,
  gettingReviewGroupData: (reviewRequestCode: string) =>
    `${REVIEW_GROUPS_BASIC_API_URL}/summary?${REVIEW_GROUP_DATA_API_PARAMS.queryString.reviewRequestCode}=${reviewRequestCode}`,
  gettingSectionList: `${serverUrl}/${VERSION2}/sections`,
  gettingWrittenReviewList: (lastReviewId: number | null, size: number) => {
    const basicUrl = `${WRITTEN_REVIEW_LIST_API_URL}?${WRITTEN_REVIEW_PARAMS.queryString.size}=${size}`;

    if (!lastReviewId) return basicUrl;
    return basicUrl + '&' + `${WRITTEN_REVIEW_PARAMS.queryString.lastReviewId}=${lastReviewId}`;
  },
  gettingGroupedReviews: (reviewRequestCode: string, sectionId: number) =>
    `${REVIEW_GROUPS_BASIC_API_URL}/${reviewRequestCode}/reviews/gather?${REVIEW_GROUP_API_PARAMS.queryString.sectionId}=${sectionId}`,
  postingHighlight: (reviewRequestCode: string) => `${REVIEW_GROUPS_BASIC_API_URL}/${reviewRequestCode}/highlights`,
  gettingReviewLinks: REVIEW_GROUPS_BASIC_API_URL,
};

export default endPoint;
