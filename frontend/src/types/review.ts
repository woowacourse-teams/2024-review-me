export interface ReviewItem {
  question: string;
  answer: string;
}
export interface Keyword {
  id: number;
  detail: string;
}

export interface DetailReviewContent {
  id: number;
  question: string;
  answer: string;
}
export interface DetailReviewData {
  id: number;
  createdAt: Date;
  reviewerGroup: {
    id: number;
    name: string;
    deadline: Date;
    reviewee: {
      id: number;
      name: string;
    };
  };
  contents: DetailReviewContent[];
  keywords: Keyword[];
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
