import { renderHook, waitFor } from '@testing-library/react';
import { act } from 'react';
import { RecoilRoot, RecoilState, useRecoilValue, useSetRecoilState } from 'recoil';

import { REVIEW_QUESTION_DATA, STRENGTH_SECTION_LIST } from '@/mocks/mockData';
import { cardSectionListSelector, reviewWritingFormSectionListAtom, selectedCategoryAtom } from '@/recoil';
import { EssentialPropsWithChildren } from '@/types';

import useCardSectionList from '.';

interface InitializeStateParams {
  set: <T>(recoilState: RecoilState<T>, newValue: T) => void;
}

const renderUseCardSectionListHook = () => {
  const wrapper = ({ children }: EssentialPropsWithChildren) => (
    <RecoilRoot
      initializeState={({ set }: InitializeStateParams) => {
        set(reviewWritingFormSectionListAtom, REVIEW_QUESTION_DATA.sections);
      }}
    >
      {children}
    </RecoilRoot>
  );

  return renderHook(
    () => {
      const hookResult = useCardSectionList({ cardSectionListData: REVIEW_QUESTION_DATA.sections });
      const setSelectedCategory = useSetRecoilState(selectedCategoryAtom);
      const cardSectionList = useRecoilValue(cardSectionListSelector);

      return {
        ...hookResult,
        cardSectionList,
        setSelectedCategory,
      };
    },
    {
      wrapper,
    },
  );
};

describe('질문지 테스트', () => {
  it('강점을 선택하기 전, 질문지는 공통 질문만 들어있다.', async () => {
    const { result } = renderUseCardSectionListHook();

    await waitFor(() => {
      expect(result.current.cardSectionList.length).not.toBe(0);
    });

    expect(result.current.cardSectionList.every((section) => section.visible === 'ALWAYS')).toBeTruthy();
  });

  it('강점을 선택하면, 해당 강점에 대한 꼬리 질문이 추가된다.', async () => {
    const categoryId = STRENGTH_SECTION_LIST[0].onSelectedOptionId as number;
    const { result } = renderUseCardSectionListHook();
    //초기 환경
    await waitFor(() => {
      expect(result.current.cardSectionList.length).not.toBe(0);
    });

    expect(result.current.cardSectionList.every((section) => section.visible === 'ALWAYS')).toBeTruthy();

    //강점 선택
    act(() => {
      result.current.setSelectedCategory([categoryId]);
    });

    expect(result.current.cardSectionList.find((section) => section.onSelectedOptionId === categoryId)).toBeDefined();
  });
});
