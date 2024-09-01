import { renderHook, waitFor } from '@testing-library/react';
import { RecoilRoot, RecoilState, useRecoilValue, useSetRecoilState } from 'recoil';

import { REVIEW_QUESTION_DATA, STRENGTH_SECTION_LIST } from '@/mocks/mockData';
import { useUpdateDefaultAnswers } from '@/pages/ReviewWritingPage/form/hooks';
import {
  answerValidationMapAtom,
  cardSectionListSelector,
  reviewWritingFormSectionListAtom,
  selectedCategoryAtom,
} from '@/recoil';
import { EssentialPropsWithChildren } from '@/types';

import useStepList from '.';

interface InitializeStateParams {
  set: <T>(recoilState: RecoilState<T>, newValue: T) => void;
}

interface RenderUseStepListHookProps {
  currentCardIndex: number;
}

const renderUseStepListHook = ({ currentCardIndex }: RenderUseStepListHookProps) => {
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
      const hookResult = useStepList({ currentCardIndex });
      const sectionList = useRecoilValue(cardSectionListSelector);
      const setSelectedCategory = useSetRecoilState(selectedCategoryAtom);
      const setAnswerValidationMap = useSetRecoilState(answerValidationMapAtom);

      useUpdateDefaultAnswers();

      return {
        ...hookResult,
        sectionList,
        setSelectedCategory,
        setAnswerValidationMap,
      };
    },
    {
      wrapper,
    },
  );
};

describe('프로그레스 바 테스트', () => {
  describe('질문지에 따른 프로그레스 바 변경 테스트', () => {
    it('선택된 강점이 없다면, 프로그레스 바에 꼬리 질문 단계가 없다', () => {
      const STRENGTH_SECTION_ID_LIST = STRENGTH_SECTION_LIST.map((section) => section.sectionId);

      const { result } = renderUseStepListHook({ currentCardIndex: 0 });

      const { stepList } = result.current;

      // 강점에 대한 step 없음
      expect(stepList.every(({ sectionId }) => !STRENGTH_SECTION_ID_LIST.includes(sectionId))).toBeTruthy();
    });

    it('선택된 강점이 있다면, 프로그레스 바에 해당 꼬리 질문 단계가 있다.', async () => {
      const TARGET_STRENGTH_SECTION_ID = STRENGTH_SECTION_LIST[0].onSelectedOptionId as number;

      const { result } = renderUseStepListHook({ currentCardIndex: 0 });

      await waitFor(() => {
        result.current.setSelectedCategory([TARGET_STRENGTH_SECTION_ID]);
      });

      const { stepList } = result.current;

      // 강점에 대한 step 있음
      expect(stepList.some(({ sectionId }) => TARGET_STRENGTH_SECTION_ID === sectionId)).toBeTruthy();
    });
  });

  describe('currentCardIndex에 따른 프로그레스 바 변경 테스트', () => {
    it('currentCardIndex와 같은 단계의 경우 isCurrentStep 값이 참이다.', () => {
      const currentCardIndex = 1;
      const { result } = renderUseStepListHook({ currentCardIndex });

      const { stepList } = result.current;

      expect(stepList.findIndex((step) => step.isCurrentStep)).toBe(currentCardIndex);
    });
  });
  describe('카드 오픈 여부에 따른 프로그레스 바 변경 테스트', () => {
    it('오픈 했던 카드의 경우, 다시 방문할 수 있다.', () => {
      const currentCardIndex = 1;

      const { result } = renderUseStepListHook({ currentCardIndex });

      expect(result.current.stepList[0].isMovingAvailable).toBeTruthy();
    });
  });

  describe('답변 유효성에 다른 프로그레스 바 변경', () => {
    it('현재 카드의 모든 질문들의 답변이 유효하다면, isDone의 값은 참이다.', async () => {
      const TARGET_STRENGTH_SECTION_ID = STRENGTH_SECTION_LIST[0].onSelectedOptionId as number;
      const QUESTION_ID_LIST = STRENGTH_SECTION_LIST[0].questions.map((question) => question.questionId);

      const { result } = renderUseStepListHook({ currentCardIndex: 0 });

      await waitFor(() => {
        // cardSectionList 업데이트
        result.current.setSelectedCategory([TARGET_STRENGTH_SECTION_ID]);

        // 답변 유효성 업데이트
        result.current.setAnswerValidationMap((prev) => {
          const newMap = new Map(prev);
          QUESTION_ID_LIST.forEach((id) => newMap.set(id, true));

          return newMap;
        });
      });

      expect(result.current.stepList[1].isDone).toBeTruthy();
    });

    it('현재 카드의 질문 중 하나라도 답변이 유효하지 않다면, isDone의 값은 거짓이다.', async () => {
      // 첫번째 꼬리 질문 카드를 오픈한 상황
      const TARGET_STRENGTH_SECTION_ID = STRENGTH_SECTION_LIST[0].onSelectedOptionId as number;
      const QUESTION_ID_LIST = STRENGTH_SECTION_LIST[0].questions.map((question) => question.questionId);

      const { result } = renderUseStepListHook({ currentCardIndex: 1 });

      await waitFor(() => {
        // cardSectionList 업데이트
        result.current.setSelectedCategory([TARGET_STRENGTH_SECTION_ID]);

        // 답변 유효성 업데이트
        result.current.setAnswerValidationMap((prev) => {
          const newMap = new Map(prev);
          QUESTION_ID_LIST.forEach((id, index) => newMap.set(id, !!index));

          return newMap;
        });
      });

      expect(result.current.stepList[1].isDone).toBeFalsy();
    });

    it('이전에 오픈 했던 카드의 답변이 유효하지 않게되면, 이 뒤의 단계들로 이동할 수 없다', async () => {
      // 두번째 카드 오픈한 상황
      const TARGET_STRENGTH_SECTION_ID = STRENGTH_SECTION_LIST[0].onSelectedOptionId as number;

      const { result } = renderUseStepListHook({ currentCardIndex: 1 });

      await waitFor(() => {
        // cardSectionList 업데이트
        result.current.setSelectedCategory([TARGET_STRENGTH_SECTION_ID]);

        // 첫번째 카드 답변 유효성 업데이트(참)
        result.current.setAnswerValidationMap((prev) => {
          const newMap = new Map(prev);
          newMap.set(REVIEW_QUESTION_DATA.sections[0].sectionId, true);

          return newMap;
        });
      });

      //두번째 카드 이동 가능
      expect(result.current.stepList[1].isMovingAvailable).toBeTruthy();

      //첫번째 카드 답변을 유효하지 않게 변경
      await waitFor(() => {
        result.current.setAnswerValidationMap((prev) => {
          const newMap = new Map(prev);
          newMap.set(REVIEW_QUESTION_DATA.sections[0].sectionId, false);

          return newMap;
        });
      });

      //두번째 카드 이동 불가
      expect(result.current.stepList[1].isMovingAvailable).toBeFalsy();
    });
  });
});
