const endPoint = {
  postingReview: `${process.env.API_BASE_URL}/reviews`,
  gettingDetailedReview: (reviewId: number, memberId: number) =>
    `${process.env.API_BASE_URL}/reviews/${reviewId}?memberId=${memberId}`,
  gettingDataToWriteReview: (reviewerGroupId: number) =>
    `${process.env.API_BASE_URL}/reviews/write?reviewerGroupId=${reviewerGroupId}`,
  gettingReviewList: `${process.env.API_BASE_URL}/reviews`,
};

export default endPoint;
