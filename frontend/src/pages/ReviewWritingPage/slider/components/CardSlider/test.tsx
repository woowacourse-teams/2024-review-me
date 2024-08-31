import { ThemeProvider } from '@emotion/react';
import { fireEvent, render, renderHook, waitFor } from '@testing-library/react';
import { act } from 'react';
import { RecoilRoot, RecoilState } from 'recoil';

import { REVIEW_QUESTION_DATA } from '@/mocks/mockData';
import useCombinedAnswerState from '@/queryTestSetup/useCombinedAnswerState';
import { reviewWritingFormSectionListAtom } from '@/recoil';
import theme from '@/styles/theme';
import { ReviewWritingCardQuestion, ReviewWritingCardSection, ReviewWritingQuestionOptionGroup } from '@/types';

import CardSlider from '.';

const QUESTION: ReviewWritingCardQuestion = REVIEW_QUESTION_DATA.sections[0].questions[0];

interface InitializeStateParams {
  set: <T>(recoilState: RecoilState<T>, newValue: T) => void;
  reviewWritingFormSectionListData?: ReviewWritingCardSection[];
}

const initializeState = ({
  set,
  reviewWritingFormSectionListData = REVIEW_QUESTION_DATA.sections,
}: InitializeStateParams) => {
  set(reviewWritingFormSectionListAtom, reviewWritingFormSectionListData);
};

interface RenderWidthProvidersProps {
  reviewWritingFormSectionListData?: ReviewWritingCardSection[];
  currentCardIndex?: number;
}
const renderWithProviders = ({
  reviewWritingFormSectionListData = REVIEW_QUESTION_DATA.sections,
  currentCardIndex = 0,
}: RenderWidthProvidersProps) => {
  return render(
    <RecoilRoot
      initializeState={({ set }: InitializeStateParams) => initializeState({ set, reviewWritingFormSectionListData })}
    >
      <ThemeProvider theme={theme}>
        <CardSlider currentCardIndex={currentCardIndex} handleCurrentCardIndex={() => {}} handleOpenModal={() => {}} />
      </ThemeProvider>
    </RecoilRoot>,
  );
};

describe('질문 순서별, 버튼 유형 테스트', () => {
  it('첫번째 질문이면 이전 버튼이 없다', () => {});
});

describe('필수 질문의 질문 유형(객관식/주관식)과 답변에 따른 다음 버튼 활성화 테스트', () => {
  describe('객관식', () => {
    it('필수 질문인 객관식의 경우, 답변이 유효하지 않으면(=최소 선택과 최대 선택 조건을 충족하지 않는다) 다음 단계로 이동할 수 없다.', async () => {
      const CARD = REVIEW_QUESTION_DATA.sections[0];
      const QUESTION = CARD.questions[0];

      const { result } = renderHook(() => useCombinedAnswerState(), {
        wrapper: RecoilRoot,
      });
      // recoil 초기값 설정
      act(() => {
        result.current.setReviewWritingFormSectionList(REVIEW_QUESTION_DATA.sections);
      });

      await waitFor(() => {
        expect(result.current.reviewWritingFormSectionList).toEqual(REVIEW_QUESTION_DATA.sections);

        expect(result.current.answerValidationMap?.get(QUESTION.questionId)).toBeFalsy();
      });

      //컴포넌트 렌더링
      const renderResult = renderWithProviders({});

      //다음 버튼
      const nextButton = renderResult.queryByTestId(`${CARD.sectionId}-nextButton`) as HTMLButtonElement | null;

      if (!nextButton) return;

      expect(nextButton.disabled).toBeTruthy();
    });

    describe('필수 질문인 객관식의 경우, 답변이 유효하면(=최소 선택과 최대 선택 조건을 충족한다)  다음 단계로 이동할 수 있다.', () => {
      const CARD = REVIEW_QUESTION_DATA.sections[0];
      const CARD_QUESTION = CARD.questions[0];
      const { minCount, maxCount } = CARD_QUESTION.optionGroup as ReviewWritingQuestionOptionGroup;
      const testCase = [minCount, maxCount];

      testCase.forEach((count) => {
        it('최소 개수 이상 최대 개수 이하로 선택하면 다음 단계로 이동할 수 있다.', async () => {
          const { result } = renderHook(() => useCombinedAnswerState(), {
            wrapper: RecoilRoot,
          });
          // recoil 초기값 설정
          act(() => {
            result.current.setReviewWritingFormSectionList(REVIEW_QUESTION_DATA.sections);
          });

          await waitFor(() => {
            expect(result.current.reviewWritingFormSectionList).toEqual(REVIEW_QUESTION_DATA.sections);

            expect(result.current.answerValidationMap?.get(CARD_QUESTION.questionId)).toBeFalsy();
          });

          //컴포넌트 렌더링
          const renderResult = renderWithProviders({});

          //문항 선택
          const checkboxList = renderResult.getAllByRole('checkbox') as HTMLInputElement[];
          const checkedTargetList = checkboxList.slice(0, count);

          checkedTargetList.forEach((checkbox) => {
            fireEvent.click(checkbox);
            expect(checkbox.checked).toBeTruthy();
          });

          //다음 버튼
          const nextButton = renderResult.queryByTestId(`${CARD.sectionId}-nextButton`) as HTMLButtonElement | null;

          if (!nextButton) return;

          expect(nextButton.getAttribute('disabled')).toBeFalsy();
        });
      });
    });
  });
});

describe('선택 질문의 질문 유형(객관식/주관식)과 답변에 따른 다음 버튼 활성화 테스트', () => {
  describe('객관식', () => {
    const NOT_REQUIRED_QUESTION: ReviewWritingCardQuestion = {
      ...QUESTION,
      required: false,
      questionType: 'CHECKBOX',
      optionGroup: {
        ...(QUESTION.optionGroup as ReviewWritingQuestionOptionGroup),
        minCount: 2,
        maxCount: 3,
      },
    };

    const CARD_SECTION: ReviewWritingCardSection = {
      ...REVIEW_QUESTION_DATA.sections[0],
      questions: [NOT_REQUIRED_QUESTION],
    };

    const REVIEW_WRITING_CARD_SECTION_LIST_DATA: ReviewWritingCardSection[] = [
      CARD_SECTION,
      { ...CARD_SECTION, sectionId: 2 },
    ];

    it('선택 질문인 객관식의 경우, 선택된 문항이 없어도 유효하며 다음 단계로 이동할 수 있다.', async () => {
      const { result } = renderHook(() => useCombinedAnswerState(), {
        wrapper: RecoilRoot,
      });
      // recoil 초기값 설정
      act(() => {
        result.current.setReviewWritingFormSectionList(REVIEW_WRITING_CARD_SECTION_LIST_DATA);
      });

      await waitFor(() => {
        expect(result.current.reviewWritingFormSectionList).toEqual(REVIEW_WRITING_CARD_SECTION_LIST_DATA);

        expect(result.current.answerValidationMap?.get(CARD_SECTION.questions[0].questionId)).toBeTruthy();
      });

      //컴포넌트 렌더링
      const renderResult = renderWithProviders({
        reviewWritingFormSectionListData: REVIEW_WRITING_CARD_SECTION_LIST_DATA,
      });

      //다음 버튼
      const nextButton = renderResult.queryByTestId(`${CARD_SECTION.sectionId}-nextButton`) as HTMLButtonElement | null;

      if (!nextButton) return;

      expect(nextButton.getAttribute('disabled')).toBeFalsy();
    });

    it('선택 질문인 객관식이더라도 선택한 문항이 있다면 유효성 검사를 통과하지 못하면(=최소 선택 개수 이상 최대 선택 개수 이하 선택 조건을 충족하지 못함) 다음 단계로 이동할 수 없다', async () => {
      const { result } = renderHook(() => useCombinedAnswerState(), {
        wrapper: RecoilRoot,
      });

      // recoil 초기값 설정
      act(() => {
        result.current.setReviewWritingFormSectionList(REVIEW_WRITING_CARD_SECTION_LIST_DATA);
      });

      await waitFor(() => {
        expect(result.current.reviewWritingFormSectionList).toEqual(REVIEW_WRITING_CARD_SECTION_LIST_DATA);

        expect(result.current.answerValidationMap?.get(CARD_SECTION.questions[0].questionId)).toBeTruthy();
      });

      //컴포넌트 렌더링
      const renderResult = renderWithProviders({
        reviewWritingFormSectionListData: REVIEW_WRITING_CARD_SECTION_LIST_DATA,
      });

      //문항 선택
      const { minCount } = NOT_REQUIRED_QUESTION.optionGroup as ReviewWritingQuestionOptionGroup;

      const checkboxList = renderResult.getAllByRole('checkbox') as HTMLInputElement[];
      const minCheckboxList = checkboxList.slice(0, minCount - 1);

      minCheckboxList.forEach((checkbox) => {
        fireEvent.click(checkbox);
        expect(checkbox.checked).toBeTruthy();
      });
      const list = (renderResult.getAllByRole('checkbox') as HTMLInputElement[]).filter(
        (checkbox) => checkbox.checked,
      ).length;
      expect(list).toBe(1);

      //다음 버튼
      const nextButton = renderResult.queryByTestId(`${CARD_SECTION.sectionId}-nextButton`) as HTMLButtonElement | null;

      if (!nextButton) return;

      expect(nextButton.disabled).toBeTruthy();
    });
  });
});
