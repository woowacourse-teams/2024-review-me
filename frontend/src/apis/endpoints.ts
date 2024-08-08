export const DETAILED_REVIEW_API_PARAMS = {
  resource: 'reviews',
  queryString: {
    memberId: 'memberId',
  },
};

export const DETAILED_REVIEW_API_URL = `${process.env.API_BASE_URL}/${DETAILED_REVIEW_API_PARAMS.resource}`;

export const REVIEW_WRITING_API_PARAMS = {
  queryString: {
    reviewRequestCode: 'reviewRequestCode',
  },
};

const endPoint = {
  postingReview: `${process.env.API_BASE_URL}/reviews`,
  gettingDetailedReview: (reviewId: number, memberId: number) =>
    `${DETAILED_REVIEW_API_URL}/${reviewId}?${DETAILED_REVIEW_API_PARAMS.queryString.memberId}=${memberId}`,
  gettingDataToWriteReview: (reviewRequestCode: string) =>
    `${process.env.API_BASE_URL}/reviews/write?${REVIEW_WRITING_API_PARAMS.queryString.reviewRequestCode}=${reviewRequestCode}`,
  gettingReviewList: `${process.env.API_BASE_URL}/reviews`,
  postingDataForURL: `${process.env.API_BASE_URL}/groups`,
};

export default endPoint;
