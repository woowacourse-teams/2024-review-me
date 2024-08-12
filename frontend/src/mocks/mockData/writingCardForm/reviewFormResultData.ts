import { ReviewWritingFormResult } from '@/types';

import { REVIEW_REQUEST_CODE } from './reviewWritingCardFormData';

export const REVIEW_FORM_RESULT_DATA: ReviewWritingFormResult = {
  reviewRequestCode: REVIEW_REQUEST_CODE,
  answers: [
    { questionId: 1, selectedOptionIds: [1], text: null },
    { questionId: 2, selectedOptionIds: [6], text: null },
    { questionId: 3, selectedOptionIds: null, text: '커!!!!!!!!!!!!!!!!!뮤!!!!!!!!!!!!!!!' },
    { questionId: 12, selectedOptionIds: null, text: '성장!!!!!!!!!!!!!!!!!!!!!!!!!!!!!' },
    { questionId: 13, selectedOptionIds: null, text: '추가 리뷰!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!' },
  ],
};
