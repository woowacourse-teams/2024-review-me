import { atom } from 'recoil';

import { ReviewWritingAnswer, ReviewWritingCardSection } from '@/types';

import { ATOM_KEY } from '../keys';
/**
 * 서버에서 내려준 리뷰 작성 폼 데이터 중, sections
 */
export const reviewWritingFormSectionListAtom = atom<ReviewWritingCardSection[]>({
  key: ATOM_KEY.reviewWritingForm.sectionList,
  default: [], // 초기 상태는 빈 배열로 설정합니다.
});
/**
 * 카테고리 질문에서 선택한 객관식 문항의 optionId
 */
export const selectedCategoryAtom = atom<number[]>({
  key: ATOM_KEY.reviewWritingForm.selectedCategoryAtom,
  default: [],
});

/**
 * 질문에 대한 답변들
 * number : questionId
 */

export const answerMapAtom = atom<Map<number, ReviewWritingAnswer> | null>({
  key: ATOM_KEY.reviewWritingForm.answerMapAtom,
  default: null,
});

/**
 * 질문의 단볍들의 유효성 여부
 * number: questionId
 */
export const answerValidationMapAtom = atom<Map<number, boolean> | null>({
  key: ATOM_KEY.reviewWritingForm.answerValidationMapAtom,
  default: null,
});

/**
 * 이미 방문한 카드의 section ID
 * number: sectionId
 */
export const visitedCardListAtom = atom<number[]>({
  key: ATOM_KEY.reviewWritingForm.visitedCardList,
  default: [1],
});
