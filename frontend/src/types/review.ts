export interface Keyword {
  id: number;
  content: string;
}

export interface ReviewItem {
  question: string;
  answer: string;
}

export interface Options {
  optionId: number;
  content: string;
  isChecked: boolean;
}

export interface OptionGroup {
  optionGroupId: number;
  minCount: number;
  maxCount: number;
  options: Options[];
}

export type QuestionType = 'CHECKBOX' | 'TEXT';

export interface QuestionData {
  questionId: number;
  required: boolean;
  questionType: QuestionType;
  content: string;
  optionGroup: OptionGroup | null;
  hasGuideline?: boolean;
  guideline?: string | null;
  answer?: string;
}

export interface DetailReviewSection {
  sectionId: number;
  header: string;
  questions: QuestionData[];
}

export interface DetailReviewData {
  formId: number;
  revieweeName: string;
  projectName: string;
  createdAt: string;
  sections: DetailReviewSection[];
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
  reviewId: number;
  createdAt: string;
  contentPreview: string;
  categories: Category[];
}

export interface Category {
  optionId: number;
  content: string;
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
export interface ReviewWritingAnswer {
  questionId: number;
  selectedOptionIds: number[] | null;
  text: string | null;
}

export interface ReviewWritingFormResult {
  reviewRequestCode: string;
  answers: ReviewWritingAnswer[];
}
