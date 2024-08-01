export const DETAILED_REVIEW_API_PARAMS = {
  resource: 'reviews',
  queryString: {
    memberId: 'memberId',
  },
};

const endPoint = {
  postingReview: `${process.env.API_BASE_URL}/reviews`,
  gettingDetailedReview: (reviewId: number, memberId: number) =>
    `${process.env.API_BASE_URL}/${DETAILED_REVIEW_API_PARAMS.resource}/${reviewId}?${DETAILED_REVIEW_API_PARAMS.queryString.memberId}=${memberId}`,
  gettingDataToWriteReview: (reviewerGroupId: number) =>
    `${process.env.API_BASE_URL}/reviews/write?reviewerGroupId=${reviewerGroupId}`,
  gettingReviewList: `${process.env.API_BASE_URL}/reviews`,
};

export default endPoint;
