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
