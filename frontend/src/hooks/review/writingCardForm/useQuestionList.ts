import { useEffect, useState } from 'react';

import { REVIEW_WRITING_FORM_CARD_DATA } from '@/mocks/mockData';
import { ReviewWritingCardSection } from '@/types';

const useQuestionList = () => {
  const questionListSectionsData = REVIEW_WRITING_FORM_CARD_DATA.sections;
  const [questionList, setQuestionList] = useState<ReviewWritingCardSection[] | null>(null);
  const [selectedCategory, setSelectedCategory] = useState<number[] | null>(null);
  // TODO: react-query로 서버에서 질문 목록 다 받아오기

  const updatedSelectedCategory = (newSelectedCategory: number[]) => {
    setSelectedCategory(newSelectedCategory);
  };

  const updateQuestionList = () => {
    setQuestionList(
      questionListSectionsData.filter((data) => {
        // 공통 질문 추출
        if (data.visible === 'ALWAYS') return true;
        // 선택된 카테고리 답변과 data.onSelectedOptionId를 비교
        if (!data.onSelectedOptionId) return false;
        return !!selectedCategory?.includes(data.onSelectedOptionId);
      }),
    );
  };
  useEffect(() => {
    updateQuestionList();
  }, [selectedCategory]);

  return {
    questionList,
    updatedSelectedCategory,
  };
};
export default useQuestionList;
