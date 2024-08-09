import { ReviewData } from '@/types';

export const REVIEW_DATA: ReviewData = {
  reviewRequestCode: 'ABCD1234',
  reviewContents: [
    { questionId: 0, answer: '첫 번째 질문에 대한 답변이에요. 20자 이상 입력했어요.' },
    { questionId: 1, answer: '두 번째 질문에 대한 답변이에요. 20자 이상 입력했어요.' },
    { questionId: 2, answer: '세 번째 질문에 대한 답변이에요. 20자 이상 입력했어요.' },
  ],
  keywords: [0, 1],
};
