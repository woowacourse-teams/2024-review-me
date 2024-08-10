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

export interface ReviewList {
  revieweeName: string;
  projectName: string;
  reviews: ReviewInfo[];
}

export interface ReviewInfo {
  id: number;
  createdAt: string;
  contentPreview: string;
  keywords: Keyword[];
}

// 리뷰 작성 카드 관련 타입들
export interface ReviewWritingFrom {
  formId: string;
  revieweeName: string;
  projectName: string;
  sections: ReviewWritingCardSection[];
}
export interface ReviewWritingCardSection {
  sectionId: number;
  visible: 'ALWAYS' | 'CONDITIONAL';
  onSelectedOptionId: number | null;
  header: string;
  questions: ReviewWritingCardQuestion[];
}

export interface ReviewWritingCardQuestion {
  questionId: number;
  required: boolean;
  content: string; // 질문
  questionType: 'CHECKBOX' | 'TEXT';
  optionGroup: ReviewWritingQuestionOptionGroup | null; // 객관식이면 ReviewWritingQuestionOptionGroup, 아니면 null
  hasGuideline: boolean;
  guideline: string | null;
}

export interface ReviewWritingQuestionOptionGroup {
  optionGroupId: number;
  minCount: number; // 최소 몇개 체크해야함
  maxCount: number; // 최대 몇개 체크해야함 (제한 없으면 options 사이즈로 드립니다)
  options: ReviewWritingQuestionOption[];
}

export interface ReviewWritingQuestionOption {
  optionId: number;
  content: string;
}
