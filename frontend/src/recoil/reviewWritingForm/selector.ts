import { selector } from 'recoil';

import { ReviewWritingCardSection } from '@/types';

import { SELECTOR_KEY } from '../keys';

import { reviewWritingFormSectionListAtom, selectedCategoryAtom } from './atom';
/**
 * 서버에서 내려준 질문지 데이터에서,  공통 질문과 카테고리 선택된 질문을 뽑아서 만든 질문지
 * */
export const questionListSelector = selector<ReviewWritingCardSection[]>({
  key: SELECTOR_KEY.reviewWritingForm.questionListSelector,
  get: ({ get }) => {
    const sectionList = get(reviewWritingFormSectionListAtom);
    const selectedCategory = get(selectedCategoryAtom);

    return sectionList.filter((data) => {
      // 공통 질문 차출
      if (data.visible === 'ALWAYS') return true;
      // 카테고리에서 선택된 질문 차출
      if (!data.onSelectedOptionId) return false;

      return !!selectedCategory?.includes(data.onSelectedOptionId);
    });
  },
});
