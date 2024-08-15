import { useEffect } from 'react';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import { questionListSelector, reviewWritingFormSectionListAtom } from '@/recoil';
import { ReviewWritingCardSection } from '@/types';

interface UseQuestionListProps {
  questionListSectionsData: ReviewWritingCardSection[];
}
/**
 * 서버에서 받아온 데이터를 바탕으로 리뷰 작성 폼에서 사용할 질문지(상태)를 변경하는 훅
 * @param {ReviewWritingCardSection[]} questionListSectionsData  서버에서 받아온 질문 데이터
 * @returns
 */
const useQuestionList = ({ questionListSectionsData }: UseQuestionListProps) => {
  const setReviewWritingFormSectionList = useSetRecoilState(reviewWritingFormSectionListAtom);

  const questionList = useRecoilValue(questionListSelector);

  useEffect(() => {
    setReviewWritingFormSectionList(questionListSectionsData);
  }, [questionListSectionsData]);

  return {
    questionList,
  };
};
export default useQuestionList;
