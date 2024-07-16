export interface ReviewItem {
  question: string;
  answer: string;
}

export interface DetailReviewData {
  id: number;
  createdAt: Date;
  reviewer: {
    memberId: number;
    name: string;
  };
  reviewGroup: {
    groupId: number;
    name: string;
  };
  contents: ReviewItem[];
  keywords: { id: number; detail: string }[];
}

// api
export interface ReviewData {
  reviewerId: number; // 추후 제거한 뒤 토큰으로 대체
  reviewerGroupId: number; // 임의 설정
  contents: {
    order: number;
    question: string;
    answer: string;
  }[];
  selectedKeywordIds: number[];
}

export interface WritingReviewInfoData {
  id: number;
  name: string;
  deadline: Date;
  reviewee: {
    id: number;
    name: string;
  };
}
