import { ThemeProvider } from '@emotion/react';
import { render, renderHook } from '@testing-library/react';
import { act } from 'react';
import { RecoilRoot, RecoilState, useRecoilState } from 'recoil';

import { REVIEW_QUESTION_DATA, STRENGTH_SECTION_LIST } from '@/mocks/mockData';
import { answerValidationMapAtom, reviewWritingFormSectionListAtom } from '@/recoil';
import theme from '@/styles/theme';
import { EssentialPropsWithChildren, ReviewWritingCardSection } from '@/types';

import CardSlider from '.';

interface InitializeStateParams {
  set: <T>(recoilState: RecoilState<T>, newValue: T) => void;
  reviewWritingFormSectionListData?: ReviewWritingCardSection[];
}

interface WrapperProps {
  reviewWritingFormSectionListData?: ReviewWritingCardSection[];
}

interface RenderCardSliderProps extends WrapperProps, Omit<InitializeStateParams, 'set'> {
  currentCardIndex?: number;
}

const renderCardSlider = ({
  reviewWritingFormSectionListData = REVIEW_QUESTION_DATA.sections,
  currentCardIndex = 0,
}: RenderCardSliderProps) => {
  const initializeState = ({
    set,
    reviewWritingFormSectionListData = REVIEW_QUESTION_DATA.sections,
  }: InitializeStateParams) => {
    set(reviewWritingFormSectionListAtom, reviewWritingFormSectionListData);
  };

  const wrapper = ({ children }: EssentialPropsWithChildren<WrapperProps>) => (
    <RecoilRoot initializeState={({ set }) => initializeState({ set, reviewWritingFormSectionListData })}>
      {children}
    </RecoilRoot>
  );

  const hookResult = renderHook(
    () => {
      const [answerValidationMap, setAnswerValidationMap] = useRecoilState(answerValidationMapAtom);

      return {
        answerValidationMap,
        setAnswerValidationMap,
      };
    },
    {
      wrapper,
    },
  );

  const renderResult = render(
    <RecoilRoot
      initializeState={({ set }: InitializeStateParams) => initializeState({ set, reviewWritingFormSectionListData })}
    >
      <ThemeProvider theme={theme}>
        <CardSlider currentCardIndex={currentCardIndex} handleCurrentCardIndex={() => {}} handleOpenModal={() => {}} />
      </ThemeProvider>
    </RecoilRoot>,
  );

  return { ...hookResult, ...renderResult };
};

describe('질문 순서별, 버튼 유형 테스트', () => {
  const CARD = REVIEW_QUESTION_DATA.sections[0];
  it('첫번째 질문이면 이전 버튼이 없고 다음 버튼이 있다.', () => {
    const renderResult = renderCardSlider({});

    expect(renderResult.queryByTestId(`${CARD.sectionId}-prevButton`)).not.toBeInTheDocument();
    expect(renderResult.queryByTestId(`${CARD.sectionId}-nextButton`)).toBeInTheDocument();
  });

  it('마지막 질문이면, 다음 버튼이 없고 제출 전 확인 버튼과 제출 버튼이 있다', () => {
    const renderResult = renderCardSlider({
      reviewWritingFormSectionListData: [REVIEW_QUESTION_DATA.sections[0]],
      currentCardIndex: 0,
    });

    expect(renderResult.queryByTestId(`${CARD.sectionId}-nextButton`)).not.toBeInTheDocument();
    expect(renderResult.queryByTestId(`${CARD.sectionId}-recheckButton`)).toBeInTheDocument();
    expect(renderResult.queryByTestId(`${CARD.sectionId}-submitButton`)).toBeInTheDocument();
  });
});

describe('답변 유효성 여부에 따른 다음 버튼 활성화 테스트', () => {
  it('현재 카드에 대한 모든 질문의 답변이 유효해야 다음 버튼이 활성화된다.', () => {
    const SECTION = STRENGTH_SECTION_LIST[0];

    const render = renderCardSlider({
      reviewWritingFormSectionListData: [SECTION, STRENGTH_SECTION_LIST[1]],
      currentCardIndex: 0,
    });

    const { result } = render;
    // 답변 유효성 업데이트
    act(() => {
      const { answerValidationMap, setAnswerValidationMap } = result.current;
      const newAnswerValidationMap = new Map(answerValidationMap);

      SECTION.questions.forEach((question) => {
        newAnswerValidationMap.set(question.questionId, true);
      });

      setAnswerValidationMap(newAnswerValidationMap);
    });

    // 업데이트한 답변 유효성 확인
    SECTION.questions.forEach((question) => {
      const validation = result.current.answerValidationMap?.get(question.questionId);
      expect(validation).toBeTruthy();
    });

    // 다음 버튼 활성화 확인
    const nextButton = render.queryByTestId(`${SECTION.sectionId}-nextButton`) as HTMLButtonElement | null;

    if (!nextButton) return;

    expect(nextButton.disabled).toBeFalsy();
  });

  it('현재 카드에 대한 질문들 중 하나라도 유효하지 않으면 다음 버튼이 비활성화된다.', () => {
    const SECTION = STRENGTH_SECTION_LIST[0];

    const render = renderCardSlider({
      reviewWritingFormSectionListData: [SECTION, STRENGTH_SECTION_LIST[1]],
      currentCardIndex: 0,
    });

    const { result } = render;
    // 답변 유효성 업데이트
    act(() => {
      const { answerValidationMap, setAnswerValidationMap } = result.current;
      const newAnswerValidationMap = new Map(answerValidationMap);

      SECTION.questions.forEach((question, index) => {
        newAnswerValidationMap.set(question.questionId, !!index);
      });

      setAnswerValidationMap(newAnswerValidationMap);
    });

    // 업데이트한 답변 유효성 확인
    SECTION.questions.forEach((question, index) => {
      const validation = result.current.answerValidationMap?.get(question.questionId);

      expect(validation).toBe(!!index);
    });

    // 다음 버튼 활성화 확인
    const nextButton = render.queryByTestId(`${SECTION.sectionId}-nextButton`) as HTMLButtonElement | null;

    if (!nextButton) return;

    expect(nextButton.disabled).toBeTruthy();
  });
});
