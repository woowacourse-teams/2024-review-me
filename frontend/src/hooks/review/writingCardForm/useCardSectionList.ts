import { useEffect } from 'react';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import { cardSectionListSelector, reviewWritingFormSectionListAtom } from '@/recoil';
import { ReviewWritingCardSection } from '@/types';

interface UseCardSectionListProps {
  cardSectionListData: ReviewWritingCardSection[];
}
/**
 * 서버에서 받아온 데이터를 바탕으로 리뷰 작성 폼에서 사용할 질문지(상태)를 변경하는 훅
 * @param {ReviewWritingCardSection[]} cardSectionListData  서버에서 받아온 질문 데이터
 * @returns
 */
const useCardSectionList = ({ cardSectionListData }: UseCardSectionListProps) => {
  const setReviewWritingFormSectionList = useSetRecoilState(reviewWritingFormSectionListAtom);

  const cardSectionList = useRecoilValue(cardSectionListSelector);

  useEffect(() => {
    setReviewWritingFormSectionList(cardSectionListData);
  }, [cardSectionListData]);

  return {
    cardSectionList,
  };
};

export default useCardSectionList;
