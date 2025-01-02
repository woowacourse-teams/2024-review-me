import { DetailReviewData } from '@/types';

export const DETAILED_PAGE_MOCK_API_SETTING_VALUES = {
  reviewId: 5,
};

const REVIEWEENAME = 'badahertz52';

export const DETAILED_REVIEW_MOCK_DATA: DetailReviewData = {
  formId: 1,
  revieweeName: REVIEWEENAME,
  projectName: 'review-me',
  createdAt: '2024-05-05',
  sections: [
    {
      sectionId: 1,
      header: `💡 ${REVIEWEENAME}와 함께 한 기억을 떠올려볼게요`,
      questions: [
        {
          questionId: 1,
          required: true,
          questionType: 'CHECKBOX',
          content: `프로젝트 기간 동안, ${REVIEWEENAME}의 강점이 드러났던 순간을 선택해주세요`,
          optionGroup: {
            optionGroupId: 1,
            minCount: 1,
            maxCount: 2,
            options: [
              {
                optionId: 1,
                content: '🗣️커뮤니케이션, 협업 능력 (예: 팀원간의 원활한 정보 공유, 명확한 의사소통)',
                isChecked: true,
              },
              {
                optionId: 2,
                content: '💡문제 해결 능력 (예: 프로젝트 중 만난 버그/오류를 분석하고 이를 해결하는 능력)',
                isChecked: false,
              },
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
          content: `${REVIEWEENAME}에서 어떤 부분이 인상 깊었는지 선택해주세요`,
          optionGroup: {
            optionGroupId: 1,
            minCount: 1,
            maxCount: 3,
            options: [
              {
                optionId: 4,
                content: '반대 의견을 내더라도 듣는 사람이 기분 나쁘지 않게 이야기해요',
                isChecked: true,
              },
              { optionId: 5, content: '팀원들의 의견을 잘 모아서 회의가 매끄럽게 진행되도록 해요', isChecked: true },
            ],
          },
        },
        {
          questionId: 3,
          required: true,
          questionType: 'TEXT',
          content: '위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요',
          optionGroup: null,
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
          required: true,
          questionType: 'TEXT',
          content: `앞으로의 성장을 위해서 ${REVIEWEENAME}이 어떤 목표를 설정하면 좋을까요?`,
          optionGroup: null,
          answer: '어디까지 성장할려구~?',
        },
      ],
    },
    {
      sectionId: 4,
      header: '응원의 한마디를 남겨주세요',
      questions: [
        {
          questionId: 5,
          required: false,
          questionType: 'TEXT',
          content: `${REVIEWEENAME}에게 전하고 싶은 다른 리뷰가 있거나 응원의 말이 있다면 적어주세요`,
          optionGroup: null,
          answer: '응원합니다 화이팅!!',
        },
      ],
    },
  ],
};
