const API_BASE_URL = 'http://localhost:8080';

const endPoint = {
  postingReview: `${API_BASE_URL}/reviews`,
  gettingDetailedReview: (reviewId: number) => `${API_BASE_URL}/reviews/${reviewId}`,
  gettingInfoToWriteReview: (reviewerGroupId: number) => `/reviewer-groups/${reviewerGroupId}`,
  gettingKeyword: `${API_BASE_URL}/keywords`,
};

export default endPoint;
