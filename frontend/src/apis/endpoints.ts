const endPoint = {
  postingReview: `${process.env.API_BASE_URL}reviews`,
  gettingDetailedReview: (reviewId: number, memberId: number) =>
    `${process.env.API_BASE_URL}reviews/${reviewId}?memberId=${memberId}`,
  gettingDataToWriteReview: (reviewerGroupId: number) => `${process.env.API_BASE_URL}reviews/write/${reviewerGroupId}`,
  gettingKeyword: `${process.env.API_BASE_URL}keywords`,
};

export default endPoint;
