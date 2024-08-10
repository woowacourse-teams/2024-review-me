interface Option {
  optionId: number;
  content: string;
  isChecked: boolean;
}

interface OptionGroup {
  optionGroupId: number;
  minCount: number;
  maxCount: number;
  options: Option[];
}

interface BaseQuestion {
  questionId: number;
  required: boolean;
  questionType: 'CHECKBOX' | 'TEXT';
  content: string;
}

interface CheckboxQuestion extends BaseQuestion {
  questionType: 'CHECKBOX';
  optionGroup: OptionGroup;
}

interface TextQuestion extends BaseQuestion {
  questionType: 'TEXT';
  optionGroup: null;
  hasGuideline: boolean;
  guideline: string | null;
  answer: string | null;
}

type Question = CheckboxQuestion | TextQuestion;

export interface Section {
  sectionId: number;
  header: string;
  questions: Question[];
}

// NOTE: 리뷰 상세 조회의 Section 속성 이하를 가져옴
export const ANSWER_LIST: Section[] = [
  {
    sectionId: 1,
    header: '기억을 떠올려볼게요.',
    questions: [
      {
        questionId: 1,
        required: true,
        questionType: 'CHECKBOX',
        content: '프로젝트 기간동안 강점이 드러난 ~ 골라주세요',
        optionGroup: {
          optionGroupId: 1,
          minCount: 1,
          maxCount: 2,
          options: [
            { optionId: 1, content: '코드리뷰', isChecked: true },
            { optionId: 2, content: '프로젝트 관리', isChecked: false },
            { optionId: 3, content: '커뮤니케이션 능력', isChecked: true },
          ],
        },
      },
    ],
  },

  {
    sectionId: 2,
    header: '이제 선택한 순간을 바탕으로 리뷰를 작성해볼게요',
    questions: [
      {
        questionId: 2,
        required: true,
        questionType: 'CHECKBOX',
        content: '어떤 부분이 인상깊었나요 ?',
        optionGroup: {
          optionGroupId: 1,
          minCount: 1,
          maxCount: 3,
          options: [
            { optionId: 4, content: '코드리뷰', isChecked: true },
            { optionId: 5, content: '프로젝트 관리', isChecked: true },
            { optionId: 6, content: '커뮤니케이션 능력', isChecked: false },
          ],
        },
      },
      {
        questionId: 3,
        required: true,
        questionType: 'TEXT',
        content: '인상깊은 상황을 이야기해주세요',
        optionGroup: null,
        hasGuideline: true,
        guideline: '가이드라인',
        answer: '쑤쑤 쑤퍼노바 인상깊어요',
      },
    ],
  },
  {
    sectionId: 3,
    header: '응원의 한마디를 남겨주세요',
    questions: [
      {
        questionId: 4,
        required: false,
        questionType: 'TEXT',
        content: '응원의 한마디',
        optionGroup: null,
        hasGuideline: false,
        guideline: null,
        answer: '응원합니다 화이팅!!',
      },
    ],
  },
];
