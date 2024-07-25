const endPoint = {
  postingReview: `${process.env.API_BASE_URL}/reviews`,
  gettingDetailedReview: (reviewId: number, memberId: number) =>
    `${process.env.API_BASE_URL}/reviews/${reviewId}?memberId=${memberId}`,
  gettingReviewList: (revieweeId: number, lastReviewId: number, memberId: number) =>
    `${process.env.API_BASE_URL}/reviews?revieweeId=${revieweeId}&lastReviewId=${lastReviewId}&memberId=${memberId}`,
  gettingInfoToWriteReview: (reviewerGroupId: number) => `/reviewer-groups/${reviewerGroupId}`,
  gettingKeyword: `${process.env.API_BASE_URL}/keywords`,
};

export default endPoint;
