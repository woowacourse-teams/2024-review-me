const endPoint = {
  postingReview: `${process.env.API_BASE_URL}/reviews`,
  gettingDetailedReview: (reviewId: number) => `${process.env.API_BASE_URL}/reviews/${reviewId}`,
  gettingDataToWriteReview: (reviewerGroupId: number) => `/reviewer-groups/${reviewerGroupId}`,
  gettingKeyword: `${process.env.API_BASE_URL}/keywords`,
};

export default endPoint;
