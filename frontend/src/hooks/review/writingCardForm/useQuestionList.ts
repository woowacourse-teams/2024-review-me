import { useEffect, useState } from 'react';

import { ReviewWritingCardSection } from '@/types';

interface UseQuestionListProps {
  questionListSectionsData: ReviewWritingCardSection[];
}

const useQuestionList = ({ questionListSectionsData }: UseQuestionListProps) => {
  const [questionList, setQuestionList] = useState<ReviewWritingCardSection[] | null>(null);
  const [selectedCategory, setSelectedCategory] = useState<number[] | null>(null);

  const updatedSelectedCategory = (newSelectedCategory: number[]) => {
    setSelectedCategory(newSelectedCategory);
  };

  const updateQuestionList = () => {
    const newQuestionList = questionListSectionsData.filter((data) => {
      // 공통 질문 추출
      if (data.visible === 'ALWAYS') return true;
      // 선택된 카테고리 답변과 data.onSelectedOptionId를 비교
      if (!data.onSelectedOptionId) return false;
      return !!selectedCategory?.includes(data.onSelectedOptionId);
    });
    setQuestionList(newQuestionList);
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
