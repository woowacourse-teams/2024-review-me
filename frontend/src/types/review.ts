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
