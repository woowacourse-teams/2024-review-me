import { useRecoilState, useRecoilValue } from 'recoil';

import { useUpdateDefaultAnswers } from '@/pages/ReviewWritingPage/form/hooks';
import {
  answerMapAtom,
  answerValidationMapAtom,
  cardSectionListSelector,
  reviewWritingFormSectionListAtom,
  selectedCategoryAtom,
} from '@/recoil';

const useCombinedReviewWritingState = () => {
  const answerMap = useRecoilValue(answerMapAtom);
  const answerValidationMap = useRecoilValue(answerValidationMapAtom);
  const [reviewWritingFormSectionList, setReviewWritingFormSectionList] = useRecoilState(
    reviewWritingFormSectionListAtom,
  );
  const [selectedCategory, setSelectedCategory] = useRecoilState(selectedCategoryAtom);
  const cardSectionList = useRecoilValue(cardSectionListSelector);

  useUpdateDefaultAnswers();
  return {
    answerMap,
    answerValidationMap,
    selectedCategory,
    setSelectedCategory,
    cardSectionList,
    reviewWritingFormSectionList,
    setReviewWritingFormSectionList,
  };
};

export default useCombinedReviewWritingState;
