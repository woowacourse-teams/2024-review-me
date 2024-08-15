import { ReviewWritingFrom } from '@/types';

export const REVIEW_REQUEST_CODE = 'ABCD1234';

export const REVIEW_WRITING_FORM_CARD_DATA: ReviewWritingFrom = {
  formId: '1',
  revieweeName: '김리뷰',
  projectName: '리뷰미 프로젝트',
  sections: [
    // 항상 보이는 Section
    {
      sectionId: 1,
      visible: 'ALWAYS',
      onSelectedOptionId: null,
      header: '기억을 떠올려볼게요.', // 최종: `💡${REVIEWEE}와 함께 한 기억을 떠올려볼게요.`,
      questions: [
        {
          questionId: 1,
          required: true,
          questionType: 'CHECKBOX',
          content: '프로젝트 기간동안 강점이 드러난 순간들을 골라주세요',
          hasGuideline: false,
          guideline: null,
          optionGroup: {
            optionGroupId: 1,
            minCount: 1,
            maxCount: 2,
            options: [
              { optionId: 1, content: '🗣️ 커뮤니케이션, 협업 능력  (ex: 팀원간의 원활한 정보 공유, 명확한 의사소통)' },
              {
                optionId: 2,
                content: '💡 문제 해결 능력  (ex: 프로젝트 중 만난 버그/오류를 분석하고 이를 해결하는 능력)',
              },
              { optionId: 3, content: '⏰ 시간 관리 능력 (ex: 일정과 마감 기한 준수, 업무의 우선 순위 분배)' },
              { optionId: 4, content: '🤓 기술적 역량, 전문 지식 (ex: 요구 사항을 이해하고 이를 구현하는 능력)' },
              {
                optionId: 5,
                content:
                  '🌱 성장 마인드셋 (ex: 새로운 분야나 잘 모르는 분야에 도전하는 마음, 꾸준한 노력으로 프로젝트 이전보다 성장하는 모습)',
              },
            ],
          },
        },
      ],
    },
    {
      sectionId: 2, // 커뮤니케이션
      visible: 'CONDITIONAL',
      onSelectedOptionId: 1,
      header: '이제 선택한 순간을 바탕으로 리뷰를 작성해볼게요',
      questions: [
        {
          questionId: 2,
          required: true,
          questionType: 'CHECKBOX',
          content: '어떤 커뮤니케이션/협업 능력이 인상깊었나요?',
          hasGuideline: false,
          guideline: null,
          optionGroup: {
            optionGroupId: 2,
            minCount: 1,
            maxCount: 3,
            options: [
              { optionId: 6, content: '반대 의견을 내더라도 듣는 사람이 기분 나쁘지 않게 이야기해요.' },
              { optionId: 7, content: '팀원들의 의견을 잘 모아서 회의가 매끄럽게 진행되도록 해요.' },
              { optionId: 8, content: '팀의 분위기를 주도해요.' },
              { optionId: 9, content: '주장을 이야기할 때에는 합당한 근거가 뒤따라요.' },
              {
                optionId: 10,
                content: '팀에게 필요한 것과 그렇지 않은 것을 잘 구분해요.',
              },
              {
                optionId: 11,
                content: '팀 내 주어진 요구사항에 우선순위를 잘 매겨요.',
              },
              { optionId: 12, content: '서로 다른 분야간의 소통도 중요하게 생각해요.' },
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
          guideline: `상황을 자세하게 기록할수록 리뷰에 도움이 돼요. 팀이 원활한 소통을 이뤘거나, 함께 일하면서 배울 점이 있었는지 떠올려 보세요.`,
        },
      ],
    },
    {
      sectionId: 3, // 문제해결 능력
      visible: 'CONDITIONAL',
      onSelectedOptionId: 2,
      header: '이제 선택한 순간을 바탕으로 리뷰를 작성해볼게요',
      questions: [
        {
          questionId: 4,
          required: true,
          questionType: 'CHECKBOX',
          content: ' 어떤 문제 해결 능력이 인상 깊었는지 선택해주세요.',
          hasGuideline: false,
          guideline: null,
          optionGroup: {
            optionGroupId: 2,
            minCount: 1,
            maxCount: 3,
            options: [
              { optionId: 13, content: '큰 문제를 작은 단위로 쪼개서 단계별로 해결해나가요.' },
              { optionId: 14, content: '낯선 문제를 만나도 당황하지 않고 차분하게 풀어나가요.' },
              { optionId: 15, content: '문제 해결을 위해 GPT등의 자원을 적극적으로 활용해요.' },
              {
                optionId: 16,
                content: '문제를 해결한 뒤에도 재발 방지를 위한 노력을 기울여요. (예: 문서화, 테스트 케이스 추가 등)',
              },
              {
                optionId: 17,
                content: '문제의 원인을 적극적으로 탐구하고 해결해요. (예: 디버깅 툴의 적극적 활용 등)',
              },
              {
                optionId: 18,
                content: '어려운 문제를 만나도 피하지 않고 도전해요.',
              },
              {
                optionId: 19,
                content:
                  '문제를 해결하기 위해 타인과 의사소통을 할 수 있어요. (예: 팀원과 이슈 공유, 문제 상황 설명 등)',
              },
              { optionId: 20, content: '문제 원인과 해결책에 대한 가설을 세우고 직접 실험해봐요.' },
            ],
          },
        },
        {
          questionId: 5,
          required: true,
          questionType: 'TEXT',
          content: '인상깊은 상황을 이야기해주세요',
          optionGroup: null,
          hasGuideline: true,
          guideline: `상황을 자세하게 기록할수록 리뷰에 도움이 돼요. 팀이 원활한 소통을 이뤘거나, 함께 일하면서 배울 점이 있었는지 떠올려 보세요.`,
        },
      ],
    },
    {
      sectionId: 4, // 시간 관리 능력
      visible: 'CONDITIONAL',
      onSelectedOptionId: 3,
      header: '이제 선택한 순간을 바탕으로 리뷰를 작성해볼게요',
      questions: [
        {
          questionId: 6,
          required: true,
          questionType: 'CHECKBOX',
          content: '어떤 시간 관리 능력이 인상 깊었는지 선택해주세요.',
          hasGuideline: false,
          guideline: null,
          optionGroup: {
            optionGroupId: 2,
            minCount: 1,
            maxCount: 3,
            options: [
              { optionId: 21, content: '큰 문제를 작은 단위로 쪼개서 단계별로 해결해나가요.' },
              { optionId: 22, content: '낯선 문제를 만나도 당황하지 않고 차분하게 풀어나가요.' },
              { optionId: 8, content: '문제 해결을 위해 GPT등의 자원을 적극적으로 활용해요.' },
              {
                optionId: 23,
                content: '문제를 해결한 뒤에도 재발 방지를 위한 노력을 기울여요. (예: 문서화, 테스트 케이스 추가 등)',
              },
              {
                optionId: 24,
                content: '문제의 원인을 적극적으로 탐구하고 해결해요. (예: 디버깅 툴의 적극적 활용 등)',
              },
              {
                optionId: 25,
                content: '어려운 문제를 만나도 피하지 않고 도전해요.',
              },
              {
                optionId: 26,
                content:
                  '문제를 해결하기 위해 타인과 의사소통을 할 수 있어요. (예: 팀원과 이슈 공유, 문제 상황 설명 등)',
              },
              { optionId: 27, content: '문제 원인과 해결책에 대한 가설을 세우고 직접 실험해봐요.' },
            ],
          },
        },
        {
          questionId: 7,
          required: true,
          questionType: 'TEXT',
          content: '인상깊은 상황을 이야기해주세요',
          optionGroup: null,
          hasGuideline: true,
          guideline: `상황을 자세하게 기록할수록 리뷰에 도움이 돼요. 팀이 원활한 소통을 이뤘거나, 함께 일하면서 배울 점이 있었는지 떠올려 보세요.`,
        },
      ],
    },
    {
      sectionId: 5, // 기술적 역량, 전문 지식 능력
      visible: 'CONDITIONAL',
      onSelectedOptionId: 4,
      header: '이제 선택한 순간을 바탕으로 리뷰를 작성해볼게요',
      questions: [
        {
          questionId: 8,
          required: true,
          questionType: 'CHECKBOX',
          content: '어떤 기술적 능력이 인상 깊었는지 선택해주세요.',
          hasGuideline: false,
          guideline: null,
          optionGroup: {
            optionGroupId: 2,
            minCount: 1,
            maxCount: 3,
            options: [
              { optionId: 28, content: '큰 문제를 작은 단위로 쪼개서 단계별로 해결해나가요.' },
              { optionId: 29, content: '낯선 문제를 만나도 당황하지 않고 차분하게 풀어나가요.' },
              { optionId: 30, content: '문제 해결을 위해 GPT등의 자원을 적극적으로 활용해요.' },
              {
                optionId: 31,
                content: '문제를 해결한 뒤에도 재발 방지를 위한 노력을 기울여요. (예: 문서화, 테스트 케이스 추가 등)',
              },
              {
                optionId: 32,
                content: '문제의 원인을 적극적으로 탐구하고 해결해요. (예: 디버깅 툴의 적극적 활용 등)',
              },
              {
                optionId: 33,
                content: '어려운 문제를 만나도 피하지 않고 도전해요.',
              },
              {
                optionId: 34,
                content:
                  '문제를 해결하기 위해 타인과 의사소통을 할 수 있어요. (예: 팀원과 이슈 공유, 문제 상황 설명 등)',
              },
              { optionId: 35, content: '문제 원인과 해결책에 대한 가설을 세우고 직접 실험해봐요.' },
            ],
          },
        },
        {
          questionId: 9,
          required: true,
          questionType: 'TEXT',
          content: '인상깊은 상황을 이야기해주세요',
          optionGroup: null,
          hasGuideline: true,
          guideline: `상황을 자세하게 기록할수록 리뷰에 도움이 돼요. 팀이 원활한 소통을 이뤘거나, 함께 일하면서 배울 점이 있었는지 떠올려 보세요.`,
        },
      ],
    },
    {
      sectionId: 6, // 성장 마인드 셋
      visible: 'CONDITIONAL',
      onSelectedOptionId: 5,
      header: '이제 선택한 순간을 바탕으로 리뷰를 작성해볼게요',
      questions: [
        {
          questionId: 10,
          required: true,
          questionType: 'CHECKBOX',
          content: '어떤 성장 마인드셋이 인상 깊었는지 선택해주세요.',
          hasGuideline: false,
          guideline: null,
          optionGroup: {
            optionGroupId: 2,
            minCount: 1,
            maxCount: 3,
            options: [
              { optionId: 36, content: '큰 문제를 작은 단위로 쪼개서 단계별로 해결해나가요.' },
              { optionId: 37, content: '낯선 문제를 만나도 당황하지 않고 차분하게 풀어나가요.' },
              { optionId: 38, content: '문제 해결을 위해 GPT등의 자원을 적극적으로 활용해요.' },
              {
                optionId: 39,
                content: '문제를 해결한 뒤에도 재발 방지를 위한 노력을 기울여요. (예: 문서화, 테스트 케이스 추가 등)',
              },
              {
                optionId: 40,
                content: '문제의 원인을 적극적으로 탐구하고 해결해요. (예: 디버깅 툴의 적극적 활용 등)',
              },
              {
                optionId: 41,
                content: '어려운 문제를 만나도 피하지 않고 도전해요.',
              },
              {
                optionId: 42,
                content:
                  '문제를 해결하기 위해 타인과 의사소통을 할 수 있어요. (예: 팀원과 이슈 공유, 문제 상황 설명 등)',
              },
              { optionId: 43, content: '문제 원인과 해결책에 대한 가설을 세우고 직접 실험해봐요.' },
            ],
          },
        },
        {
          questionId: 11,
          required: true,
          questionType: 'TEXT',
          content: '인상깊은 상황을 이야기해주세요',
          optionGroup: null,
          hasGuideline: true,
          guideline: `상황을 자세하게 기록할수록 리뷰에 도움이 돼요. 팀이 원활한 소통을 이뤘거나, 함께 일하면서 배울 점이 있었는지 떠올려 보세요.`,
        },
      ],
    },
    {
      sectionId: 7,
      visible: 'ALWAYS',
      onSelectedOptionId: 6,
      header: `성장을 위한 다음 목표를 설정해볼게요.`,
      questions: [
        {
          questionId: 12,
          required: true,
          questionType: 'TEXT',
          content: '앞으로의 성장을 위해서 어떤 목표를 설정하면 좋을까요?',
          optionGroup: null,
          hasGuideline: false,
          guideline: null,
        },
      ],
    },
    {
      sectionId: 8,
      visible: 'ALWAYS',
      onSelectedOptionId: null,
      header: `아직 전하지 못한 리뷰/응원을 적어보세요.`,
      questions: [
        {
          questionId: 13,
          required: false,
          questionType: 'TEXT',
          content: '전하고 싶은 다른 리뷰가 있거나 응원의 말이 있다면 적어주세요.',
          optionGroup: null,
          hasGuideline: false,
          guideline: null,
        },
      ],
    },
  ],
};
