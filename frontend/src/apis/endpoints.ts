const endPoint = {
  postingReview: `${process.env.API_BASE_URL}/reviews`,
  gettingDetailedReview: (reviewId: number, memberId: number) =>
    `${process.env.API_BASE_URL}reviews/${reviewId}?memberId=${memberId}`,
  gettingDataToWriteReview: (reviewerGroupId: number) => `${process.env.API_BASE_URL}reviews/write/${reviewerGroupId}`,
  gettingReviewList: (revieweeId: number, lastReviewId: number, memberId: number) =>
    `${process.env.API_BASE_URL}/reviews?revieweeId=${revieweeId}&lastReviewId=${lastReviewId}&memberId=${memberId}`,
};

export default endPoint;
