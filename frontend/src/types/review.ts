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
// NOTE: 3차-1주차 시현 시 리뷰 상세페이지 API 데이터의 형식이 변경되었는데, keyword부분이 다를 데이터에서 어떻게 변경될 지 몰라서 리뷰 상세페이지의 키워드만을 위한 타입을 따로 만들었어요.
export interface DetailReviewKeyword {
  id: number;
  detail: string;
}
export interface DetailReviewData {
  id: number;
  createdAt: Date;
  projectName: string;
  revieweeName: string;
  contents: DetailReviewContent[];
  keywords: DetailReviewKeyword[];
}

// api
export interface ReviewData {
  reviewRequestCode: string;
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
  revieweeName: string;
  projectName: string;
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
