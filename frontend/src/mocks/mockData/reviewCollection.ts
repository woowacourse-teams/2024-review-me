import { GroupedReviews, GroupedSection } from '@/types';

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

export const GROUPED_REVIEWS_MOCK_DATA: GroupedReviews[] = [
  {
    reviews: [
      {
        question: {
          id: 1,
          name: '커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요',
          type: 'CHECKBOX',
        },
        answers: null,
        votes: [
          { content: '반대 의견을 내더라도 듣는 사람이 기분 나쁘지 않게 이야기해요', count: 13 },
          { content: '팀원들의 의견을 잘 모아서 회의가 매끄럽게 진행되도록 해요', count: 0 },
          { content: '팀의 분위기를 주도해요', count: 5 },
          { content: '주장을 이야기할 때에는 합당한 근거가 뒤따라요', count: 3 },
          { content: '팀에게 필요한 것과 그렇지 않은 것을 잘 구분해요', count: 0 },
          { content: '팀 내 주어진 요구사항에 우선순위를 잘 매겨요', count: 1 },
          { content: '서로 다른 분야간의 소통도 중요하게 생각해요', count: 1 },
        ],
      },
      {
        question: {
          id: 2,
          name: '위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요',
          type: 'TEXT',
        },
        answers: [
          {
            id: 1,
            content:
              '커뮤니은 짧고 직접적이며, 뒤따라 나올 복잡한 정보를 어떻게 해석해야 할 것인지 프레임을 짜주는 역할을 해야 한다. 그러면 아무리 긴 문장이라도 쉽게 읽힌다.\n\n프레임을 짜주는 역할을 해야 한다.\n    \n그러면 아무리 긴 문장이라도 쉽게 읽힌다.\n프레임을 짜주는 역할을 해야 한다. 그러면 아무리 긴 문장이라도 쉽게 읽힌다.',
            highlights: [{ lineIndex: 0, ranges: [{ startIndex: 0, endIndex: 0 }] }],
          },
          {
            id: 2,
            content:
              'http://localhost:3000/user/review-zone/5WkYQLqW1http://localhost:3000/user/review-zone/5WkYQLqW2http://localhost:3000/user/review-zone/5WkYQLqW3http://localhost:3000/user/review-zone/5WkYQLqW4http://localhost:3000/user/review-zone/5WkYQLqW5http://localhost:3000/user/review-zone/5WkYQLqW6http://localhost:3000/user/review-zone/5WkYQLqW7http://localhost:3000/user/review-zone/5WkYQLqW8http://localhost:3000/user/review-zone/5WkYQLqW9http://localhost:3000/user/review-zone/5WkYQLqW10',
            highlights: [
              {
                lineIndex: 0,
                ranges: [
                  { startIndex: 17, endIndex: 20 },
                  { startIndex: 64, endIndex: 67 },
                  { startIndex: 205, endIndex: 208 },
                  { startIndex: 252, endIndex: 255 },
                  { startIndex: 346, endIndex: 349 },
                ],
              },
            ],
          },
          {
            id: 3,
            content:
              '장의 시작부분은 짧고 직접적이며, 뒤따라 나올 복잡한 정보를 어떻게 해석해야 할 것인지 프레임을 짜주는 역할을 해야 한다. 그러면 아무리 긴 문장이라도 쉽게 읽힌다.',
            highlights: [],
          },
          {
            id: 4,
            content:
              '고액공제건강보험과 건강저축계좌를 만들어 노동자와 고용주가 세금공제를 받을 수 있도록 하면 결과적으로 노동자의 의료보험 부담이 커진다. 세금공제를 받을 수 있도록 하면------------------------------------------- 결과적으로 노동자의 의료보험 부담이 커진다.',
            highlights: [],
          },
        ],
        votes: null,
      },
    ],
  },
  {
    reviews: [
      {
        question: {
          id: 3,
          name: '문제 능력에서 어떤 부분이 인상 깊었는지 선택해주세요',
          type: 'CHECKBOX',
        },
        answers: null,
        votes: [
          { content: '반대 의견을 내더라도 듣는 사람이 기분 나쁘지 않게 이야기해요', count: 13 },
          { content: '팀원들의 의견을 잘 모아서 회의가 매끄럽게 진행되도록 해요', count: 0 },
          { content: '팀의 분위기를 주도해요', count: 5 },
          { content: '주장을 이야기할 때에는 합당한 근거가 뒤따라요', count: 3 },
          { content: '팀에게 필요한 것과 그렇지 않은 것을 잘 구분해요', count: 0 },
          { content: '팀 내 주어진 요구사항에 우선순위를 잘 매겨요', count: 1 },
          { content: '서로 다른 분야간의 소통도 중요하게 생각해요', count: 1 },
        ],
      },
      {
        question: {
          id: 4,
          name: '위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요',
          type: 'TEXT',
        },
        answers: [
          {
            id: 1,
            content:
              '문제 능력 장의 시작부분은 짧고 직접적이며, 뒤따라 나올 복잡한 정보를 어떻게 해석해야 할 것인지 프레임을 짜주는 역할을 해야 한다. 그러면 아무리 긴 문장이라도 쉽게 읽힌다.\n\n프레임을 짜주는 역할을 해야 한다.\n    \n그러면 아무리 긴 문장이라도 쉽게 읽힌다.\n프레임을 짜주는 역할을 해야 한다. 그러면 아무리 긴 문장이라도 쉽게 읽힌다.',
            highlights: [{ lineIndex: 0, ranges: [{ startIndex: 0, endIndex: 0 }] }],
          },
          {
            id: 2,
            content:
              ' 복잡한 문제를 체계적으로 분석하고, 창의적인 해결책을 제안하며 이를 실행하는 데 뛰어난 역량을 보여줍니다. 특히, 제한된 시간과 자원 속에서도 효과적으로 우선순위를 정하고 문제를 해결하는 모습을 통해 팀에 큰 신뢰를 주었습니다. 이러한 능력은 팀의 목표 달성과 성장에 큰 기여를 하며, 앞으로도 더 많은 성과를 낼 수 있을 것으로 기대됩니다.!!!!!',
            highlights: [
              {
                lineIndex: 0,
                ranges: [
                  { startIndex: 17, endIndex: 20 },
                  { startIndex: 64, endIndex: 67 },
                  { startIndex: 205, endIndex: 208 },
                  { startIndex: 252, endIndex: 255 },
                  { startIndex: 346, endIndex: 349 },
                ],
              },
            ],
          },
          {
            id: 3,
            content:
              '문제의 핵심 원인을 빠르게 파악하고, 이를 바탕으로 실행 가능한 솔루션을 제시하며 팀의 목표를 달성하는 데 큰 기여를 했습니다. 특히, 예상치 못한 상황에서도 냉철한 판단과 적극적인 태도로 해결책을 찾아가는 모습은 팀원들에게 좋은 자극이 되었습니다.',
            highlights: [],
          },
          {
            id: 4,
            content:
              '문제를 다양한 관점에서 바라보며 가장 적합한 해결책을 찾아내는 능력이 뛰어납니다. 특히, 여러 이해관계자 간의 의견을 조율하며 모두가 만족할 수 있는 방안을 제안한 점이 돋보였습니다. 이 과정에서 보여준 적극적인 소통과 논리적인 접근법은 팀의 신뢰를 더욱 높였고, 어려운 과제를 성공적으로 마무리할 수 있는 원동력이 되었습니다.',
            highlights: [],
          },
        ],
        votes: null,
      },
    ],
  },
  {
    reviews: [
      {
        question: {
          id: 5,
          name: '시간 능력에서 어떤 부분이 인상 깊었는지 선택해주세요',
          type: 'CHECKBOX',
        },
        answers: null,
        votes: [
          { content: '반대 의견을 내더라도 듣는 사람이 기분 나쁘지 않게 이야기해요', count: 13 },
          { content: '팀원들의 의견을 잘 모아서 회의가 매끄럽게 진행되도록 해요', count: 0 },
          { content: '팀의 분위기를 주도해요', count: 5 },
          { content: '주장을 이야기할 때에는 합당한 근거가 뒤따라요', count: 3 },
          { content: '팀에게 필요한 것과 그렇지 않은 것을 잘 구분해요', count: 0 },
          { content: '팀 내 주어진 요구사항에 우선순위를 잘 매겨요', count: 1 },
          { content: '서로 다른 분야간의 소통도 중요하게 생각해요', count: 1 },
        ],
      },
      {
        question: {
          id: 6,
          name: '위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요',
          type: 'TEXT',
        },
        answers: [
          {
            id: 1,
            content:
              '시간 능력 장의 시작부분은 짧고 직접적이며, 뒤따라 나올 복잡한 정보를 어떻게 해석해야 할 것인지 프레임을 짜주는 역할을 해야 한다. 그러면 아무리 긴 문장이라도 쉽게 읽힌다.\n\n프레임을 짜주는 역할을 해야 한다.\n    \n그러면 아무리 긴 문장이라도 쉽게 읽힌다.\n프레임을 짜주는 역할을 해야 한다. 그러면 아무리 긴 문장이라도 쉽게 읽힌다.',
            highlights: [{ lineIndex: 0, ranges: [{ startIndex: 0, endIndex: 0 }] }],
          },
          {
            id: 2,
            content:
              '효율적인 시간 관리 능력을 통해 중요한 작업을 기한 내에 완수하는 모습이 매우 인상적이었습니다. 특히, 작업의 우선순위를 명확히 구분하고 이를 기반으로 체계적으로 계획을 세워 진행하는 점이 돋보였습니다. 이러한 능력 덕분에 팀 전체의 생산성이 향상되었고, 예상치 못한 문제가 발생했을 때도 유연하게 대처하며 프로젝트를 성공적으로 이끌었습니다.',
            highlights: [
              {
                lineIndex: 0,
                ranges: [
                  { startIndex: 17, endIndex: 20 },
                  { startIndex: 64, endIndex: 67 },
                  { startIndex: 205, endIndex: 208 },
                  { startIndex: 252, endIndex: 255 },
                  { startIndex: 346, endIndex: 349 },
                ],
              },
            ],
          },
          {
            id: 3,
            content:
              '시간을 효율적으로 활용하는 뛰어난 능력을 보여주셨습니다. 작업 초기부터 명확한 계획을 수립하고 이를 끝까지 유지하는 모습이 인상적이었으며, 예상치 못한 변수에도 침착하게 대처하며 프로젝트의 일정과 품질을 모두 충족시켰습니다. 이러한 점은 팀에 큰 안정감을 주었고, 함께 일하는 사람들에게도 좋은 본보기가 되었습니다.',
            highlights: [],
          },
          {
            id: 4,
            content:
              '타이트한 일정 속에서도 주어진 목표를 체계적으로 달성하며, 동시에 세부적인 디테일까지 놓치지 않는 모습을 보여주셨습니다. 특히, 작업 과정에서 우선순위를 명확히 설정하고, 불필요한 시간 낭비를 줄이는 효율적인 접근 방식은 팀의 전반적인 속도와 성과에 크게 기여했습니다. 앞으로도 이런 시간 관리 능력을 통해 더 많은 성과를 이루시리라 믿습니다.',
            highlights: [],
          },
        ],
        votes: null,
      },
    ],
  },
];
