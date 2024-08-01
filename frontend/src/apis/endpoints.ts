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
  gettingReviewList: (revieweeId: number, lastReviewId: number, memberId: number) =>
    `${process.env.API_BASE_URL}/reviews?revieweeId=${revieweeId}&lastReviewId=${lastReviewId}&memberId=${memberId}`,
};

export default endPoint;
