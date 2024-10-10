import { GroupedReviews, GroupedSection, ReviewSummary } from '@/types';

export const REVIEW_SUMMARY_MOCK_DATA: ReviewSummary = {
  projectName: '리뷰미',
  revieweeName: '에프이',
  reviewCount: 5,
};

export const GROUPED_SECTION_MOCK_DATA: GroupedSection = {
  sections: [
    { id: 0, name: '강점 카테고리' },
    { id: 1, name: '커뮤니케이션, 협업 능력' },
    { id: 2, name: '문제 해결 능력' },
    { id: 3, name: '시간 관리 능력' },
    { id: 4, name: '기술 역량, 전문 지식' },
    { id: 5, name: '성장 마인드셋' },
    { id: 6, name: '단점 피드백' },
    { id: 7, name: '추가 리뷰 및 응원' },
  ],
};

export const GROUPED_REVIEWS_MOCK_DATA: GroupedReviews = {
  reviews: [
    {
      question: {
        name: '커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요',
        type: 'CHECKBOX',
      },
      answers: null,
      votes: [
        { content: '반대 의견을 내더라도 듣는 사람이 기분 나쁘지 않게 이야기해요', count: 5 },
        { content: '팀원들의 의견을 잘 모아서 회의가 매끄럽게 진행되도록 해요', count: 4 },
        { content: '팀의 분위기를 주도해요', count: 3 },
        { content: '주장을 이야기할 때에는 합당한 근거가 뒤따라요', count: 2 },
        { content: '팀에게 필요한 것과 그렇지 않은 것을 잘 구분해요', count: 2 },
        { content: '팀 내 주어진 요구사항에 우선순위를 잘 매겨요 (커뮤니케이션 능력을 특화하자)', count: 1 },
        { content: '서로 다른 분야간의 소통도 중요하게 생각해요', count: 1 },
      ],
    },
    {
      question: {
        name: '위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요',
        type: 'TEXT',
      },
      answers: [
        {
          content:
            '장의 시작부분은 짧고 직접적이며, 뒤따라 나올 복잡한 정보를 어떻게 해석해야 할 것인지 프레임을 짜주는 역할을 해야 한다. 그러면 아무리 긴 문장이라도 쉽게 읽힌다.',
        },
        {
          content:
            '고액공제건강보험과 건강저축계좌를 만들어 노동자와 고용주가 세금공제를 받을 수 있도록 하면 결과적으로 노동자의 의료보험 부담이 커진다.',
        },
        {
          content:
            '장의 시작부분은 짧고 직접적이며, 뒤따라 나올 복잡한 정보를 어떻게 해석해야 할 것인지 프레임을 짜주는 역할을 해야 한다. 그러면 아무리 긴 문장이라도 쉽게 읽힌다.',
        },
        {
          content:
            '고액공제건강보험과 건강저축계좌를 만들어 노동자와 고용주가 세금공제를 받을 수 있도록 하면 결과적으로 노동자의 의료보험 부담이 커진다.',
        },
      ],
      votes: null,
    },
  ],
};
