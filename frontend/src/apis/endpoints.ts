export const VERSION2 = 'v2';

export const DETAILED_REVIEW_API_PARAMS = {
  resource: 'reviews',
  queryString: {
    memberId: 'memberId',
  },
};

export const DETAILED_REVIEW_API_URL = `${process.env.API_BASE_URL}/${VERSION2}/${DETAILED_REVIEW_API_PARAMS.resource}`;

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

export const REVIEW_WRITING_API_URL = `${process.env.API_BASE_URL}/${VERSION2}/${REVIEW_WRITING_API_PARAMS.resource}`;

export const REVIEW_GROUP_DATA_API_PARAMS = {
  resource: 'groups',
  queryString: {
    reviewRequestCode: 'reviewRequestCode',
  },
};

export const REVIEW_GROUP_DATA_API_URL = `${process.env.API_BASE_URL}/${VERSION2}/${REVIEW_GROUP_DATA_API_PARAMS.resource}`;

const endPoint = {
  postingReview: `${process.env.API_BASE_URL}/${VERSION2}/reviews`,
  gettingDetailedReview: (reviewId: number) => `${DETAILED_REVIEW_API_URL}/${reviewId}`,
  gettingDataToWriteReview: (reviewRequestCode: string) =>
    `${REVIEW_WRITING_API_URL}/${REVIEW_WRITING_API_PARAMS.queryString.write}?${REVIEW_WRITING_API_PARAMS.queryString.reviewRequestCode}=${reviewRequestCode}`,
  gettingReviewList: `${process.env.API_BASE_URL}/${VERSION2}/reviews`,
  postingDataForReviewRequestCode: `${process.env.API_BASE_URL}/${VERSION2}/groups`,
  passwordChecking: `${process.env.API_BASE_URL}/${VERSION2}/${REVIEW_PASSWORD_API_PARAMS.resource}/${REVIEW_PASSWORD_API_PARAMS.queryString.check}`,
  gettingReviewGroupData: (reviewRequestCode: string) =>
    `${REVIEW_GROUP_DATA_API_URL}?${REVIEW_GROUP_DATA_API_PARAMS.queryString.reviewRequestCode}=${reviewRequestCode}`,
};

export default endPoint;
