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
  reviewerGroup: {
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
  reviewContents: ReviewContent[];
  keywords: number[];
}

export interface ReviewContent {
  questionId: number;
  answer: string;
}

export interface Question {
  id: number;
  content: string;
}

export interface Keyword {
  id: number;
  content: string;
}

export interface WritingReviewInfoData {
  reviewerGroup: {
    id: number;
    name: string;
    description: string;
    deadline: Date;
    thumbnailUrl: string;
    reviewee: {
      id: number;
      name: string;
    };
  };
  questions: Question[];
  keywords: Keyword[];
}
