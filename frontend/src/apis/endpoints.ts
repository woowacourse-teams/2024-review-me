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

export const REVIEW_LIST_API_PARAMS = {
  resource: 'reviews',
};

export const REVIEW_WRITING_API_PARAMS = {
  resource: 'reviews',
  queryString: {
    write: 'write',
    reviewRequestCode: 'reviewRequestCode',
  },
};

export const REVIEW_PASSWORD_API_PARAMS = {
  resource: 'groups',
  queryString: {
    check: 'check',
  },
};

export const REVIEW_GROUP_DATA_API_PARAMS = {
  resource: 'groups',
  queryString: {
    reviewRequestCode: 'reviewRequestCode',
  },
};

export const REVIEW_WRITING_API_URL = `${serverUrl}/${VERSION2}/${REVIEW_WRITING_API_PARAMS.resource}`;
export const REVIEW_LIST_API_URL = `${serverUrl}/${VERSION2}/${REVIEW_LIST_API_PARAMS.resource}`;
export const DETAILED_REVIEW_API_URL = `${serverUrl}/${VERSION2}/${DETAILED_REVIEW_API_PARAMS.resource}`;
export const REVIEW_GROUP_DATA_API_URL = `${serverUrl}/${VERSION2}/${REVIEW_GROUP_DATA_API_PARAMS.resource}`;

const endPoint = {
  postingReview: `${serverUrl}/${VERSION2}/reviews`,
  gettingDetailedReview: (reviewId: number) => `${DETAILED_REVIEW_API_URL}/${reviewId}`,
  gettingDataToWriteReview: (reviewRequestCode: string) =>
    `${REVIEW_WRITING_API_URL}/${REVIEW_WRITING_API_PARAMS.queryString.write}?${REVIEW_WRITING_API_PARAMS.queryString.reviewRequestCode}=${reviewRequestCode}`,
  gettingReviewList: (lastReviewId: number | null, size: number) => {
    if (lastReviewId) {
      return `${REVIEW_LIST_API_URL}?lastReviewId=${lastReviewId}&size=${size}`;
    }
    return `${REVIEW_LIST_API_URL}?size=${size}`;
  },
  postingDataForReviewRequestCode: `${serverUrl}/${VERSION2}/groups`,
  checkingPassword: `${serverUrl}/${VERSION2}/${REVIEW_PASSWORD_API_PARAMS.resource}/${REVIEW_PASSWORD_API_PARAMS.queryString.check}`,
  gettingReviewGroupData: (reviewRequestCode: string) =>
    `${REVIEW_GROUP_DATA_API_URL}?${REVIEW_GROUP_DATA_API_PARAMS.queryString.reviewRequestCode}=${reviewRequestCode}`,
  gettingSectionList: `${serverUrl}/${VERSION2}/sections`,
  gettingGroupedReviews: (sectionId: number) => `${serverUrl}/${VERSION2}/reviews/gather?sectionId=${sectionId}`,
};

export default endPoint;
