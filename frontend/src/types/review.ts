export interface Keyword {
  id: number;
  content: string;
}

export interface ReviewItem {
  question: string;
  answer: string;
}

export interface DetailReviewContent {
  question: string;
  answer: string;
}
export interface DetailReviewData {
  id: number;
  createdAt: Date;
  isPublic: boolean;
  reviewerGroup: {
    id: number;
    name: string;
    description: string;
    thumnailUrl: string;
  };
  reviews: DetailReviewContent[];
  keywords: string[];
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

export interface ReviewPreview {
  id: number;
  isPublic: boolean;
  reviewerGroup: {
    id: number;
    name: string;
    thumbnailUrl: string;
  };
  createdAt: string;
  contentPreview: string;
  keywords: Array<{
    id: number;
    content: string;
  }>;
}
