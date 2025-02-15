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
  resource: 'groups',
  queryString: {
    reviewRequestCode: 'reviewRequestCode',
  },
};

export const REVIEW_GROUP_API_PARAMS = {
  queryString: {
    sectionId: 'sectionId',
  },
};

export const REVIEW_WRITING_API_URL = `${serverUrl}/${VERSION2}/${REVIEW_WRITING_API_PARAMS.resource}`;

export const DETAILED_REVIEW_API_URL = `${serverUrl}/${VERSION2}/${DETAILED_REVIEW_API_PARAMS.resource}`;
export const REVIEW_GROUP_DATA_API_URL = `${serverUrl}/${VERSION2}/${REVIEW_GROUP_DATA_API_PARAMS.resource}`;
const REVIEW_GROUPS_BASIC_API_URL = `${serverUrl}/${VERSION2}/groups`;

// NOTE: 추후에 reviewRequestCode가 아닌 reviewGroupId로 요청하는 방식으로 변경될 예정
export const makeReviewGroupBasicApiUrl = (reviewRequestCode: string) =>
  `${REVIEW_GROUPS_BASIC_API_URL}/${reviewRequestCode}/reviews/gather`;
export const makeReceivedReviewListBasicUrl = (reviewRequestCode: string) =>
  `${REVIEW_GROUPS_BASIC_API_URL}/${reviewRequestCode}/${REVIEW_RECEIVED_LIST_API_PARAMS.resource}`;
export const makeReviewSummaryInfoBasicUrl = (reviewRequestCode: string) =>
  `${REVIEW_GROUPS_BASIC_API_URL}/${reviewRequestCode}/reviews/summary`;

interface GetReviewListEndPointParams {
  lastReviewId: number | null;
  size: number;
  reviewRequestCode: string;
}
const endPoint = {
  postingReview: `${serverUrl}/${VERSION2}/reviews`,
  gettingReviewSummaryInfoData: (reviewRequestCode: string) => makeReviewSummaryInfoBasicUrl(reviewRequestCode),
  gettingDetailedReview: (reviewId: number) => `${DETAILED_REVIEW_API_URL}/${reviewId}`,
  gettingDataToWriteReview: (reviewRequestCode: string) =>
    `${REVIEW_WRITING_API_URL}/${REVIEW_WRITING_API_PARAMS.queryString.write}?${REVIEW_WRITING_API_PARAMS.queryString.reviewRequestCode}=${reviewRequestCode}`,
  gettingReceivedReviewList: ({ lastReviewId, size, reviewRequestCode }: GetReviewListEndPointParams) => {
    const basicUrl = makeReceivedReviewListBasicUrl(reviewRequestCode);
    if (lastReviewId) {
      return `${basicUrl}?lastReviewId=${lastReviewId}&size=${size}`;
    }
    return `${basicUrl}?size=${size}`;
  },
  postingDataForReviewRequestCode: `${serverUrl}/${VERSION2}/groups`,
  checkingReviewRequestPassword: `${serverUrl}/${VERSION2}/auth/review-group`,
  gettingReviewGroupData: (reviewRequestCode: string) =>
    `${REVIEW_GROUP_DATA_API_URL}?${REVIEW_GROUP_DATA_API_PARAMS.queryString.reviewRequestCode}=${reviewRequestCode}`,
  gettingSectionList: `${serverUrl}/${VERSION2}/sections`,
  gettingGroupedReviews: (reviewRequestCode: string, sectionId: number) =>
    `${makeReviewGroupBasicApiUrl(reviewRequestCode)}?${REVIEW_GROUP_API_PARAMS.queryString.sectionId}=${sectionId}`,
  postingHighlight: (reviewRequestCode: string) => `${serverUrl}/${VERSION2}/${reviewRequestCode}/highlight`,
};

export default endPoint;
