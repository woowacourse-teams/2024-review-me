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
