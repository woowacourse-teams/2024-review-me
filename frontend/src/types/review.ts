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
  totalSize: number;
  lastReviewId: number | null;
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
export interface ReviewWritingFormData {
  formId: number;
  revieweeName: string;
  projectName: string;
  sections: ReviewWritingCardSection[];
}
/**
 * 서버에서 보내주는 질문지 데이터 형식
 */
export interface ReviewWritingCardSection {
  sectionId: number;
  sectionName: string;
  visible: 'ALWAYS' | 'CONDITIONAL';
  /**
   * 강점 테고리별 questionId , 그렇지 않으면 null
   */
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
  /**
   * 해당 답변을 작성한 질문(ReviewWritingCardQuestion)의 questionId
   */
  questionId: number;
  /**
   * number[] (빈배열이 아닌 배열) : 객관식 질문에서 선택된 답변이 있는 경우
   * [] : 객관식 질문에서 선택된 답변이 없는 경우
   * null : 서술형 질문
   */
  selectedOptionIds: number[] | null;
  /**
   * string (빈문자열이 아닌 문자열): 서술형 질문에서 작성한 답변이 있는 경우
   * ""(빈문자열) : 서술형 질문에서 작성한 답변이 없는 경우
   * null: 객관식 질문
   */
  text: string | null;
}

export interface ReviewWritingFormResult {
  reviewRequestCode: string;
  answers: ReviewWritingAnswer[];
}
