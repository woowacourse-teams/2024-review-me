export interface Keyword {
  id: number;
  content: string;
}

export interface ReviewItem {
  question: string;
  answer: string;
}

export interface DetailReviewContent {
  id: number;
  question: string;
  answer: string;
}

export interface DetailReviewData {
  id: number;
  createdAt: Date;
  projectName: string;
  revieweeName: string;
  contents: DetailReviewContent[];
  keywords: Keyword[];
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

export interface ReviewPreviewList {
  revieweeName: string;
  projectName: string;
  reviews: ReviewPreview[];
}

export interface ReviewPreview {
  id: number;
  createdAt: string;
  contentPreview: string;
  keywords: Keyword[];
}
