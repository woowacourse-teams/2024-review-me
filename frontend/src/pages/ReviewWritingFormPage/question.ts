export const REVIEWEE = '쑤쑤';
export const TAIL_QUESTION_TITLE = `✏️선택한 순간들을 바탕으로 ${REVIEWEE}에 대한 리뷰를 작성해볼게요.`;

export interface QuestionType {
  name: string;
  title: string;
  question: string;
  answerType: 'choice' | 'essay';
  options?: string[];
  isExtraEssay?: boolean;
  choiceMinLength?: number;
  choiceMaxLength?: number;
}

export interface AnswerType {
  questionName: string;
  choiceAnswer?: string[];
  essayAnswer?: string;
}
const CHOICE_MIN_LENGTH = 1;
const OPENING_CHOICE_MAX_LENGTH = 2;

export const TAIL_QUESTIONS: QuestionType[] = [
  {
    name: 'communication',
    title: TAIL_QUESTION_TITLE,
    question: '커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요. (1개 이상)',
    answerType: 'choice',
    options: [
      '반대 의견을 내더라도 듣는 사람이 기분 나쁘지 않게 이야기해요.',
      '팀원들의 의견을 잘 모아서 회의가 매끄럽게 진행되도록 해요.',
      '팀의 분위기를 주도해요.',
      '주장을 이야기할 때에는 합당한 근거가 뒤따라요.',
      '팀에게 필요한 것과 그렇지 않은 것을 잘 구분해요.',
      '팀 내 주어진 요구사항에 우선순위를 잘 매겨요.',
      '서로 다른 분야간의 소통도 중요하게 생각해요.',
    ],
    isExtraEssay: true,
    choiceMinLength: CHOICE_MIN_LENGTH,
  },
  {
    name: 'solution',
    title: TAIL_QUESTION_TITLE,
    question: '문제해결 능력에서 어느 부분이 인상 깊었는지 선택해주세요. (1개 이상)',
    answerType: 'choice',
    options: [
      '큰 문제를 작은 단위로 쪼개서 단계별로 해결해나가요.',
      '낯선 문제를 만나도 당황하지 않고 차분하게 풀어나가요.',
      '문제 해결을 위해 GPT등의 자원을 적극적으로 활용해요.',
      '문제를 해결한 뒤에도 재발 방지를 위한 노력을 기울여요. (예: 문서화, 테스트 케이스 추가 등)',
      '문제의 원인을 적극적으로 탐구하고 해결해요. (예: 디버깅 툴의 적극적 활용 등)',
      '어려운 문제를 만나도 피하지 않고 도전해요.',
      '문제를 해결하기 위해 타인과 의사소통을 할 수 있어요. (예: 팀원과 이슈 공유, 문제 상황 설명 등)',
      '문제 원인과 해결책에 대한 가설을 세우고 직접 실험해봐요.',
    ],
    isExtraEssay: true,
    choiceMinLength: CHOICE_MIN_LENGTH,
  },
  {
    name: 'time',
    title: TAIL_QUESTION_TITLE,
    question: '시간 관리 능력을 선택하셨다면 어떤 부분에서 인상 깊었는지 선택해주세요.',
    answerType: 'choice',
    options: [
      '프로젝트의 일정과 주요 마일스톤을 설정하여 체계적으로 일정을 관리해요.',
      '일정에 따라 마감 기한을 잘 지켜요.',
      '업무의 중요도와 긴급성을 고려하여 우선 순위를 정하고, 그에 따라 작업을 분배해요.',
      '예기치 않은 일정 변경에도 유연하게 대처해요.',
      '회의 시간과 같은 약속된 시간을 잘 지켜요.',
    ],
    isExtraEssay: true,
    choiceMinLength: CHOICE_MIN_LENGTH,
  },
  {
    name: 'techSkill',
    title: TAIL_QUESTION_TITLE,
    question: '기술 역량, 전문 지식에서 어떤 부분에서 인상 깊었는지 선택해주세요.',
    answerType: 'choice',
    options: [
      '관련 언어 / 라이브러리 / 프레임워크 지식이 풍부해요.',
      '인프라 지식이 풍부해요.',
      'CS 지식이 풍부해요.',
      '코드 리뷰에서 중요한 개선점을 제안했어요.',
      '리팩토링을 통해 전체 코드의 품질을 향상시켰어요.',
      '복잡한 버그를 신속하게 찾고 해결했어요.',
      '꼼꼼하게 테스트를 작성했어요.',
      '처음 보는 기술을 빠르게 습득하여 팀 프로젝트에 적용했어요.',
      '명확하고 자세한 기술 문서를 작성하여 팀의 이해를 도왔어요.',
      '컨벤션을 잘 지키면서 클린 코드를 작성하려고 노력했어요.',
      '성능 최적화에 기여했어요.',
      '지속적인 학습과 공유를 통해 팀의 기술 수준을 높였어요.',
    ],
    isExtraEssay: true,
    choiceMinLength: CHOICE_MIN_LENGTH,
  },
  {
    name: 'growthMind',
    title: TAIL_QUESTION_TITLE,
    question: '성장 마인드셋을 선택하셨다면 어떤 부분이 인상 깊었는지 선택해주세요.',
    answerType: 'choice',
    options: [
      '어떤 상황에도 긍정적인 태도로 임해요.',
      '주변 사람들한테 질문하는 것을 부끄러워하지 않아요.',
      '어려움이 있어도 끝까지 해내요.',
      '함께 성장하기 위해, 배운 내용을 다른 사람과 공유해요.',
      '새로운 것을 두려워하지 않고 적극적으로 배워나가요.',
      '이론적 학습에서 그치지 않고 직접 적용하려 노력해요.',
      '다른 사람들과 비교하지 않고 본인만의 속도로 성장하는 법을 알고 있어요.',
      '받은 피드백을 빠르게 수용해요.',
      '회고를 통해 성장할 수 있는 방법을 스스로 탐색해요.',
      '새로운 아이디어를 시도하고, 기존의 틀을 깨는 것을 두려워하지 않아요.',
    ],
    isExtraEssay: true,
    choiceMinLength: CHOICE_MIN_LENGTH,
  },
];
// NOTE: Map 객체로 변환 생각해보자
export const COMMON_QUESTIONS: QuestionType[] = [
  {
    name: 'opening',
    title: `💡${REVIEWEE}와 함께 한 기억을 떠올려볼게요.`,
    question: `프로젝트 기간 동안, ${REVIEWEE}의 강점이 드러났던 순간을 선택해주세요. (1~2개)`,
    answerType: 'choice',
    options: [
      '🗣️ 커뮤니케이션, 협업 능력  (ex: 팀원간의 원활한 정보 공유, 명확한 의사소통)',
      '💡 문제 해결 능력  (ex: 프로젝트 중 만난 버그/오류를 분석하고 이를 해결하는 능력)',
      '⏰ 시간 관리 능력 (ex: 일정과 마감 기한 준수, 업무의 우선 순위 분배)',
      '🤓 기술적 역량, 전문 지식 (ex: 요구 사항을 이해하고 이를 구현하는 능력)',
      '🌱 성장 마인드셋 (ex: 새로운 분야나 잘 모르는 분야에 도전하는 마음, 꾸준한 노력으로 프로젝트 이전보다 성장하는 모습)',
    ],
    choiceMinLength: CHOICE_MIN_LENGTH,
    choiceMaxLength: OPENING_CHOICE_MAX_LENGTH,
  },

  {
    name: 'goal',
    title: `🌱${REVIEWEE}의 다음 목표를 설정해볼게요.`,
    question: `앞으로의 성장을 위해서 ${REVIEWEE}가 어떤 목표를 설정하면 좋을까요?`,
    answerType: 'essay',
  },
  {
    name: 'plusReview',
    title: '🤗리뷰를 더 하고 싶은 리뷰어를 위한 추가 리뷰!',
    question: `${REVIEWEE}}에게 전하고 싶은 다른 리뷰가 있거나 응원의 말이 있다면 적어주세요.`,
    answerType: 'essay',
  },
];

interface EssayType {
  name: string;
  question: string;
  guideLine: string;
}
export const ESSAY_QUESTION = '위에서 선택한 사항에 대해 조금 더 자세히 설명해주세요.';

export const ESSAY: EssayType[] = [
  {
    name: 'communication',
    question: ESSAY_QUESTION,
    guideLine:
      '상황을 자세하게 기록할수록 OO에게 도움이 돼요. OO 덕분에 팀이 원활한 소통을 이뤘거나, 함께 일하면서 배울 점이 있었는지 떠올려 보세요.',
  },
  {
    name: 'solution',
    question: ESSAY_QUESTION,
    guideLine:
      '상황을 자세하게 기록할수록 OO에게 도움이 돼요. 어떤 문제 상황이 발생했고, OO이 어떻게 해결했는지 그 과정을 떠올려 보세요.',
  },
  {
    name: 'time',
    question: ESSAY_QUESTION,
    guideLine:
      '상황을 자세하게 기록할수록 OO에게 도움이 돼요. OO 덕분에 팀이 효율적으로 시간관리를 할 수 있었는지 떠올려 보세요.',
  },
  {
    name: 'techSkill',
    question: ESSAY_QUESTION,
    guideLine:
      '상황을 자세하게 기록할수록 OO에게 도움이 돼요. OO 덕분에 기술적 역량, 전문 지식적으로 도움을 받은 경험을 떠올려 보세요.',
  },
  {
    name: 'growthMind',
    question: ESSAY_QUESTION,
    guideLine: '상황을 자세하게 기록할수록 OO에게 도움이 돼요. 인상깊었던 OO의 성장 마인드셋을 떠올려 보세요.',
  },
];
