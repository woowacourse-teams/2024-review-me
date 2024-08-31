import { ReviewWritingCardSection, ReviewWritingFormData } from '@/types';

export const REVIEW_REQUEST_CODE = 'ABCD1234';

export const FEEDBACK_SECTION: ReviewWritingCardSection = {
  sectionId: 7,
  sectionName: '단점 피드백',
  visible: 'ALWAYS',
  onSelectedOptionId: null,
  header: 'bada의 성장을 도와주세요!',
  questions: [
    {
      questionId: 12,
      required: true,
      content: '앞으로의 성장을 위해서 bada이/가 어떤 목표를 설정하면 좋을까요?',
      questionType: 'TEXT',
      optionGroup: null,
      hasGuideline: true,
      guideline: "어떤 점을 보완하면 좋을지와 함께 '이렇게 해보면 어떨까?'하는 간단한 솔루션을 제안해봐요",
    },
  ],
};

export const EXTRA_REVIEW_SECTION: ReviewWritingCardSection = {
  sectionId: 8,
  sectionName: '추가 리뷰 및 응원',
  visible: 'ALWAYS',
  onSelectedOptionId: null,
  header: '리뷰를 더 하고 싶은 리뷰어를 위한 추가 리뷰!',
  questions: [
    {
      questionId: 13,
      required: false,
      content: 'bada에게 전하고 싶은 다른 리뷰가 있거나 응원의 말이 있다면 적어주세요',
      questionType: 'TEXT',
      optionGroup: null,
      hasGuideline: false,
      guideline: null,
    },
  ],
};

export const REVIEW_QUESTION_DATA: ReviewWritingFormData = {
  formId: 1,
  revieweeName: 'bada',
  projectName: 'bada',
  sections: [
    {
      sectionId: 1,
      sectionName: '카테고리 선택',
      visible: 'ALWAYS',
      onSelectedOptionId: null,
      header: 'bada와 함께 한 기억을 떠올려볼게요',
      questions: [
        {
          questionId: 1,
          required: true,
          content: '프로젝트 기간 동안, bada의 강점이 드러났던 순간을 선택해주세요',
          questionType: 'CHECKBOX',
          optionGroup: {
            optionGroupId: 1,
            minCount: 1,
            maxCount: 2,
            options: [
              { optionId: 1, content: '🗣️커뮤니케이션, 협업 능력  (ex: 팀원간의 원활한 정보 공유, 명확한 의사소통)' },
              {
                optionId: 2,
                content: '💡문제 해결 능력  (ex: 프로젝트 중 만난 버그/오류를 분석하고 이를 해결하는 능력)',
              },
              { optionId: 3, content: '⏰시간 관리 능력 (ex: 일정과 마감 기한 준수, 업무의 우선 순위 분배)' },
              { optionId: 4, content: '💻기술적 역량, 전문 지식 (ex: 요구 사항을 이해하고 이를 구현하는 능력)' },
              {
                optionId: 5,
                content:
                  '🌱성장 마인드셋 (ex: 새로운 분야나 잘 모르는 분야에 도전하는 마음, 꾸준한 노력으로 프로젝트 이전보다 성장하는 모습)',
              },
            ],
          },
          hasGuideline: false,
          guideline: null,
        },
      ],
    },
    {
      sectionId: 2,
      sectionName: '커뮤니케이션, 협업 능력',
      visible: 'CONDITIONAL',
      onSelectedOptionId: 1,
      header: '이제, 선택한 순간들을 바탕으로 bada에 대한 리뷰를 작성해볼게요',
      questions: [
        {
          questionId: 2,
          required: true,
          content: '커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요',
          questionType: 'CHECKBOX',
          optionGroup: {
            optionGroupId: 2,
            minCount: 1,
            maxCount: 7,
            options: [
              { optionId: 6, content: '반대 의견을 내더라도 듣는 사람이 기분 나쁘지 않게 이야기해요' },
              { optionId: 7, content: '팀원들의 의견을 잘 모아서 회의가 매끄럽게 진행되도록 해요' },
              { optionId: 8, content: '팀의 분위기를 주도해요' },
              { optionId: 9, content: '주장을 이야기할 때에는 합당한 근거가 뒤따라요' },
              { optionId: 10, content: '팀에게 필요한 것과 그렇지 않은 것을 잘 구분해요' },
              { optionId: 11, content: '팀 내 주어진 요구사항에 우선순위를 잘 매겨요 (커뮤니케이션 능력을 특화하자)' },
              { optionId: 12, content: '서로 다른 분야간의 소통도 중요하게 생각해요' },
            ],
          },
          hasGuideline: false,
          guideline: null,
        },
        {
          questionId: 3,
          required: true,
          content: '위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요',
          questionType: 'TEXT',
          optionGroup: null,
          hasGuideline: true,
          guideline:
            '상황을 자세하게 기록할수록 bada에게 도움이 돼요 bada 덕분에 팀이 원활한 소통을 이뤘거나, 함께 일하면서 배울 점이 있었는지 떠올려 보세요',
        },
      ],
    },
    {
      sectionId: 3,
      sectionName: '문제해결 능력',
      visible: 'CONDITIONAL',
      onSelectedOptionId: 2,
      header: '이제, 선택한 순간들을 바탕으로 bada에 대한 리뷰를 작성해볼게요',
      questions: [
        {
          questionId: 4,
          required: true,
          content: '문제해결 능력에서 어느 부분이 인상 깊었는지 선택해주세요',
          questionType: 'CHECKBOX',
          optionGroup: {
            optionGroupId: 3,
            minCount: 1,
            maxCount: 8,
            options: [
              { optionId: 13, content: '큰 문제를 작은 단위로 쪼개서 단계별로 해결해나가요' },
              { optionId: 14, content: '낯선 문제를 만나도 당황하지 않고 차분하게 풀어나가요' },
              { optionId: 15, content: '문제 해결을 위해 GPT등의 자원을 적극적으로 활용해요' },
              {
                optionId: 16,
                content: '문제를 해결한 뒤에도 재발 방지를 위한 노력을 기울여요 (예: 문서화, 테스트 케이스 추가 등)',
              },
              { optionId: 17, content: '문제의 원인을 적극적으로 탐구하고 해결해요 (예: 디버깅 툴의 적극적 활용 등)' },
              { optionId: 18, content: '어려운 문제를 만나도 피하지 않고 도전해요' },
              {
                optionId: 19,
                content:
                  '문제를 해결하기 위해 타인과 의사소통을 할 수 있어요 (예: 팀원과 이슈 공유, 문제 상황 설명 등)',
              },
              { optionId: 20, content: '문제 원인과 해결책에 대한 가설을 세우고 직접 실험해봐요' },
            ],
          },
          hasGuideline: false,
          guideline: null,
        },
        {
          questionId: 5,
          required: true,
          content: '위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요',
          questionType: 'TEXT',
          optionGroup: null,
          hasGuideline: true,
          guideline:
            '상황을 자세하게 기록할수록 bada에게 도움이 돼요  어떤 문제 상황이 발생했고, bada이/가 어떻게 해결했는지 그 과정을 떠올려 보세요',
        },
      ],
    },
    {
      sectionId: 4,
      sectionName: '시간 관리 능력',
      visible: 'CONDITIONAL',
      onSelectedOptionId: 3,
      header: '이제, 선택한 순간들을 바탕으로 bada에 대한 리뷰를 작성해볼게요',
      questions: [
        {
          questionId: 6,
          required: true,
          content: '시간 관리 능력에서 어느 부분이 인상 깊었는지 선택해주세요',
          questionType: 'CHECKBOX',
          optionGroup: {
            optionGroupId: 4,
            minCount: 1,
            maxCount: 5,
            options: [
              { optionId: 21, content: '프로젝트의 일정과 주요 마일스톤을 설정하여 체계적으로 일정을 관리해요' },
              { optionId: 22, content: '일정에 따라 마감 기한을 잘 지켜요' },
              {
                optionId: 23,
                content: '업무의 중요도와 긴급성을 고려하여 우선 순위를 정하고, 그에 따라 작업을 분배해요',
              },
              { optionId: 24, content: '예기치 않은 일정 변경에도 유연하게 대처해요' },
              { optionId: 25, content: '회의 시간과 같은 약속된 시간을 잘 지켜요' },
            ],
          },
          hasGuideline: false,
          guideline: null,
        },
        {
          questionId: 7,
          required: true,
          content: '위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요',
          questionType: 'TEXT',
          optionGroup: null,
          hasGuideline: true,
          guideline:
            '상황을 자세하게 기록할수록 bada에게 도움이 돼요 bada 덕분에 팀이 효율적으로 시간관리를 할 수 있었는지 떠올려 보세요',
        },
      ],
    },
    {
      sectionId: 5,
      sectionName: '기술 역량, 전문 지식',
      visible: 'CONDITIONAL',
      onSelectedOptionId: 4,
      header: '이제, 선택한 순간들을 바탕으로 bada에 대한 리뷰를 작성해볼게요',
      questions: [
        {
          questionId: 8,
          required: true,
          content: '기술 역량, 전문 지식에서 어떤 부분이 인상 깊었는지 선택해주세요',
          questionType: 'CHECKBOX',
          optionGroup: {
            optionGroupId: 5,
            minCount: 1,
            maxCount: 12,
            options: [
              { optionId: 26, content: '관련 언어 / 라이브러리 / 프레임워크 지식이 풍부해요' },
              { optionId: 27, content: '인프라 지식이 풍부해요' },
              { optionId: 28, content: 'CS 지식이 풍부해요' },
              { optionId: 29, content: '코드 리뷰에서 중요한 개선점을 제안했어요' },
              { optionId: 30, content: '리팩토링을 통해 전체 코드의 품질을 향상시켰어요' },
              { optionId: 31, content: '복잡한 버그를 신속하게 찾고 해결했어요' },
              { optionId: 32, content: '꼼꼼하게 테스트를 작성했어요' },
              { optionId: 33, content: '처음 보는 기술을 빠르게 습득하여 팀 프로젝트에 적용했어요' },
              { optionId: 34, content: '명확하고 자세한 기술 문서를 작성하여 팀의 이해를 도왔어요' },
              { optionId: 35, content: '컨벤션을 잘 지키면서 클린 코드를 작성하려고 노력했어요' },
              { optionId: 36, content: '성능 최적화에 기여했어요' },
              { optionId: 37, content: '지속적인 학습과 공유를 통해 팀의 기술 수준을 높였어요' },
            ],
          },
          hasGuideline: false,
          guideline: null,
        },
        {
          questionId: 9,
          required: true,
          content: '위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요',
          questionType: 'TEXT',
          optionGroup: null,
          hasGuideline: true,
          guideline:
            '상황을 자세하게 기록할수록 bada에게 도움이 돼요 bada 덕분에 기술적 역량, 전문 지식적으로 도움을 받은 경험을 떠올려 보세요',
        },
      ],
    },
    {
      sectionId: 6,
      sectionName: '성장 마인드셋',
      visible: 'CONDITIONAL',
      onSelectedOptionId: 5,
      header: '이제, 선택한 순간들을 바탕으로 bada에 대한 리뷰를 작성해볼게요',
      questions: [
        {
          questionId: 10,
          required: true,
          content: '성장 마인드셋에서 어떤 부분이 인상 깊었는지 선택해주세요',
          questionType: 'CHECKBOX',
          optionGroup: {
            optionGroupId: 6,
            minCount: 1,
            maxCount: 10,
            options: [
              { optionId: 38, content: '어떤 상황에도 긍정적인 태도로 임해요' },
              { optionId: 39, content: '주변 사람들한테 질문하는 것을 부끄러워하지 않아요' },
              { optionId: 40, content: '어려움이 있어도 끝까지 해내요' },
              { optionId: 41, content: '함께 성장하기 위해, 배운 내용을 다른 사람과 공유해요' },
              { optionId: 42, content: '새로운 것을 두려워하지 않고 적극적으로 배워나가요' },
              { optionId: 43, content: '이론적 학습에서 그치지 않고 직접 적용하려 노력해요' },
              { optionId: 44, content: '다른 사람들과 비교하지 않고 본인만의 속도로 성장하는 법을 알고 있어요' },
              { optionId: 45, content: '받은 피드백을 빠르게 수용해요' },
              { optionId: 46, content: '회고를 통해 성장할 수 있는 방법을 스스로 탐색해요' },
              { optionId: 47, content: '새로운 아이디어를 시도하고, 기존의 틀을 깨는 것을 두려워하지 않아요' },
            ],
          },
          hasGuideline: false,
          guideline: null,
        },
        {
          questionId: 11,
          required: true,
          content: '위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요',
          questionType: 'TEXT',
          optionGroup: null,
          hasGuideline: true,
          guideline: '상황을 자세하게 기록할수록 bada에게 도움이 돼요 인상깊었던 bada의 성장 마인드셋을 떠올려 보세요',
        },
      ],
    },
    FEEDBACK_SECTION,
    EXTRA_REVIEW_SECTION,
  ],
};
