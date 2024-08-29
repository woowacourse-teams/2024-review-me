import { useResetRecoilState } from 'recoil';

import { answerMapAtom, answerValidationMapAtom, selectedCategoryAtom } from '@/recoil';
/**
 * 리뷰 작성 페이지에서 사용하는 recoil 상태들을 초기화하는 훅
 */
const useResetFormRecoil = () => {
  const resetSelectedCategoryAtom = useResetRecoilState(selectedCategoryAtom);
  const resetAnswerMapAtom = useResetRecoilState(answerMapAtom);
  const resetAnswerValidationMapAtom = useResetRecoilState(answerValidationMapAtom);

  const resetFormRecoil = () => {
    resetSelectedCategoryAtom();
    resetAnswerMapAtom();
    resetAnswerValidationMapAtom();
  };

  return { resetFormRecoil };
};

export default useResetFormRecoil;
