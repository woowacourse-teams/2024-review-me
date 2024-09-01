import { renderHook, act, waitFor } from '@testing-library/react';
import { RecoilRoot, RecoilState, useRecoilValue } from 'recoil';

import { REVIEW_QUESTION_DATA } from '@/mocks/mockData';
import {
  answerMapAtom,
  reviewWritingFormSectionListAtom,
  answerValidationMapAtom,
  cardSectionListSelector,
} from '@/recoil';
import {
  EssentialPropsWithChildren,
  ReviewWritingCardQuestion,
  ReviewWritingCardSection,
  ReviewWritingQuestionOption,
} from '@/types';

import useUpdateDefaultAnswers from '../../useUpdateDefaultAnswers';
import useUpdateMultipleChoiceAnswer from '../useUpdateMultipleChoiceAnswer';

const MOCK_OPTIONS_LIST = REVIEW_QUESTION_DATA.sections[0].questions[0].optionGroup
  ?.options as ReviewWritingQuestionOption[];

const MOCK_QUESTION: ReviewWritingCardQuestion = {
  ...REVIEW_QUESTION_DATA.sections[0].questions[0],
  optionGroup: {
    optionGroupId: 2,
    minCount: 1,
    maxCount: 2,
    options: MOCK_OPTIONS_LIST,
  },
};

const MOCK_SECTION_LIST: ReviewWritingCardSection[] = [
  { ...REVIEW_QUESTION_DATA.sections[0], questions: [MOCK_QUESTION] },
];

interface WrapperProps {
  reviewWritingFormSectionListData?: ReviewWritingCardSection[];
}

interface InitializeStateParams {
  set: <T>(recoilState: RecoilState<T>, newValue: T) => void;
  reviewWritingFormSectionListData?: ReviewWritingCardSection[];
}

interface RenderUseMultipleChoiceHookProps extends WrapperProps, Omit<InitializeStateParams, 'set'> {}

const renderUseMultipleChoiceHook = ({
  reviewWritingFormSectionListData = MOCK_SECTION_LIST,
}: RenderUseMultipleChoiceHookProps) => {
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

  return renderHook(
    () => {
      const hookResult = useUpdateMultipleChoiceAnswer({ question: reviewWritingFormSectionListData[0].questions[0] });
      const answerMap = useRecoilValue(answerMapAtom);
      const answerValidationMap = useRecoilValue(answerValidationMapAtom);
      const cardSectionList = useRecoilValue(cardSectionListSelector);

      // 여기에 추가적인 hook을 사용할 수 있음
      useUpdateDefaultAnswers();

      return {
        ...hookResult,
        answerMap,
        answerValidationMap,
        cardSectionList,
      };
    },
    {
      wrapper,
    },
  );
};

describe('객관식 답변 테스트', () => {
  describe('필수 질문의 선택 개수 유효성 검사', () => {
    const VALIDATED_OPTION_LIST = ['1', '1,2'];
    const INVALIDATED_OPTION_LIST = ['', '1,2,3'];

    it.each(VALIDATED_OPTION_LIST)(
      '필수 질문인 객관식은 최소 선택 개수 이상 최대 선택 개수 이하로 선택해야 답변이 유효하다.(선택된 문항:%s)',
      async (optionList) => {
        const selectedOptionList = optionList.split(',').map((value) => Number(value));

        const { result } = renderUseMultipleChoiceHook({});

        await waitFor(() => {
          expect(result.current.cardSectionList.length).not.toBe(0);
        });

        act(() => {
          result.current.updateAnswerState(selectedOptionList);
        });

        const { answerMap, answerValidationMap } = result.current;

        //해당 질문에 대한 answerMap 답변 확인
        const updatedAnswer = answerMap?.get(MOCK_QUESTION.questionId);
        expect(updatedAnswer).toEqual({
          questionId: MOCK_QUESTION.questionId,
          selectedOptionIds: selectedOptionList,
          text: null,
        });

        //해당 질문에 대한 answerValidationMap의 값 확인
        const isValidated = answerValidationMap?.get(MOCK_QUESTION.questionId);
        expect(isValidated).toBe(true);
      },
    );

    it.each(INVALIDATED_OPTION_LIST)(
      '필수 질문인 객관식에서 선택된 문항의 개수가 최소 선택 개수 미만이거나 최대 선택 개수를 넘으면 답변이 유효하지 않다.',
      async (optionList) => {
        const selectedOptionList = optionList === '' ? [] : optionList.split(',').map((value) => Number(value));

        const { result } = renderUseMultipleChoiceHook({});

        await waitFor(() => {
          expect(result.current.cardSectionList.length).not.toBe(0);
        });

        act(() => {
          result.current.updateAnswerState(selectedOptionList);
        });

        const { answerMap, answerValidationMap } = result.current;

        //해당 질문에 대한 answerMap 답변 확인
        // 유효하지 않으면 빈배열
        const updatedAnswer = answerMap?.get(MOCK_QUESTION.questionId);
        expect(updatedAnswer).toEqual({
          questionId: MOCK_QUESTION.questionId,
          selectedOptionIds: [],
          text: null,
        });

        //해당 질문에 대한 answerValidationMap 답변 확인
        const isValidated = answerValidationMap?.get(MOCK_QUESTION.questionId);
        expect(isValidated).toBe(false);
      },
    );
  });

  describe('선택 질문의 선택 개수 유효성 검사', () => {
    // 선택 질문
    const MOCK_NOT_REQUIRED_QUESTION: ReviewWritingCardQuestion = {
      ...REVIEW_QUESTION_DATA.sections[0].questions[0],
      required: false,
      optionGroup: {
        optionGroupId: 2,
        minCount: 1,
        maxCount: 2,
        options: MOCK_OPTIONS_LIST,
      },
    };

    const MOCK__NOT_REQUIRED_SECTION_LIST: ReviewWritingCardSection[] = [
      { ...REVIEW_QUESTION_DATA.sections[0], questions: [MOCK_NOT_REQUIRED_QUESTION] },
    ];

    // 선택된 문항 옵션
    const VALIDATED_OPTION_LIST = ['1', '1,2'];
    const INVALIDATED_OPTION_LIST = ['', '1,2,3'];

    it('선택 질문인 객관식에서 선택된 문항이 없다면, 답변이 유효하다.', async () => {
      const SELECTED_OPTION_LIST: number[] = [];

      const { result } = renderUseMultipleChoiceHook({
        reviewWritingFormSectionListData: MOCK__NOT_REQUIRED_SECTION_LIST,
      });

      await waitFor(() => {
        expect(result.current.cardSectionList.length).not.toBe(0);
      });

      act(() => {
        result.current.updateAnswerState(SELECTED_OPTION_LIST);
      });

      const { answerMap, answerValidationMap } = result.current;

      //해당 질문에 대한 answerMap 답변 확인
      const updatedAnswer = answerMap?.get(MOCK_QUESTION.questionId);
      expect(updatedAnswer).toEqual({
        questionId: MOCK_QUESTION.questionId,
        selectedOptionIds: SELECTED_OPTION_LIST,
        text: null,
      });

      //해당 질문에 대한 answerValidationMap의 값 확인
      const isValidated = answerValidationMap?.get(MOCK_QUESTION.questionId);
      expect(isValidated).toBe(true);
    });

    it.each(VALIDATED_OPTION_LIST)(
      '선택 질문인 객관식이라도, 선택 된 문항이 있다면 최소 선택 개수 이상 최대 선택 개수 이하로 선택해야 답변이 유효하다.(선택된 문항:%s)',
      async (optionList) => {
        const selectedOptionList = optionList.split(',').map((value) => Number(value));

        const { result } = renderUseMultipleChoiceHook({});

        await waitFor(() => {
          expect(result.current.cardSectionList.length).not.toBe(0);
        });

        act(() => {
          result.current.updateAnswerState(selectedOptionList);
        });

        const { answerMap, answerValidationMap } = result.current;

        //해당 질문에 대한 answerMap 답변 확인
        const updatedAnswer = answerMap?.get(MOCK_QUESTION.questionId);
        expect(updatedAnswer).toEqual({
          questionId: MOCK_QUESTION.questionId,
          selectedOptionIds: selectedOptionList,
          text: null,
        });

        //해당 질문에 대한 answerValidationMap의 값 확인
        const isValidated = answerValidationMap?.get(MOCK_QUESTION.questionId);
        expect(isValidated).toBe(true);
      },
    );

    it.each(INVALIDATED_OPTION_LIST)(
      '선택 질문인 객관식에서 선택된 문항이 있다면, 최소 선택 개수 미만이거나 최대 선택 개수의 요건을 충족하지 않다면 답변이 유효하지 않다.',
      async (optionList) => {
        const selectedOptionList = optionList === '' ? [] : optionList.split(',').map((value) => Number(value));

        const { result } = renderUseMultipleChoiceHook({});

        await waitFor(() => {
          expect(result.current.cardSectionList.length).not.toBe(0);
        });

        act(() => {
          result.current.updateAnswerState(selectedOptionList);
        });

        const { answerMap, answerValidationMap } = result.current;

        //해당 질문에 대한 answerMap 답변 확인
        // 유효하지 않으면 빈배열
        const updatedAnswer = answerMap?.get(MOCK_QUESTION.questionId);
        expect(updatedAnswer).toEqual({
          questionId: MOCK_QUESTION.questionId,
          selectedOptionIds: [],
          text: null,
        });

        //해당 질문에 대한 answerValidationMap 답변 확인
        const isValidated = answerValidationMap?.get(MOCK_QUESTION.questionId);
        expect(isValidated).toBe(false);
      },
    );
  });
});
