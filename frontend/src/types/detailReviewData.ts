export interface ReviewItem {
  question: string;
  answer: string;
}

export interface DetailReviewData {
  reviewer: {
    memberId: number;
    name: string;
  };
  reviewGroup: {
    groupId: number;
    name: string;
  };
  contents: ReviewItem[];
}
