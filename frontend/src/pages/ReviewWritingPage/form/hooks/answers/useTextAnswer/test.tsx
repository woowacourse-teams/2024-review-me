import { act, renderHook, waitFor } from '@testing-library/react';
import { RecoilRoot, RecoilState, useRecoilValue } from 'recoil';

import { FEEDBACK_SECTION } from '@/mocks/mockData';
import {
  answerMapAtom,
  answerValidationMapAtom,
  cardSectionListSelector,
  reviewWritingFormSectionListAtom,
} from '@/recoil';
import { EssentialPropsWithChildren, ReviewWritingCardSection } from '@/types';

import useUpdateDefaultAnswers from '../useUpdateDefaultAnswers';

import useTextAnswer, { TEXT_ANSWER_ERROR_MESSAGE, TEXT_ANSWER_LENGTH } from '.';

const MOCK_SECTION_LIST = [FEEDBACK_SECTION];
const NOT_REQUIRED_ANSWER_MOCK_SECTION_LIST: ReviewWritingCardSection[] = [
  { ...FEEDBACK_SECTION, questions: [{ ...FEEDBACK_SECTION.questions[0], required: false }] },
];

interface WrapperProps {
  reviewWritingFormSectionListData?: ReviewWritingCardSection[];
}

interface InitializeStateParams {
  set: <T>(recoilState: RecoilState<T>, newValue: T) => void;
  reviewWritingFormSectionListData?: ReviewWritingCardSection[];
}

interface RenderUseTextAnswerHookProps extends WrapperProps, Omit<InitializeStateParams, 'set'> {}

const renderUseTextAnswerHook = ({
  reviewWritingFormSectionListData = MOCK_SECTION_LIST,
}: RenderUseTextAnswerHookProps) => {
  const initializeState = ({ set, reviewWritingFormSectionListData = MOCK_SECTION_LIST }: InitializeStateParams) => {
    set(reviewWritingFormSectionListAtom, reviewWritingFormSectionListData);
  };

  const wrapper = ({ children }: EssentialPropsWithChildren<WrapperProps>) => (
    <RecoilRoot initializeState={({ set }) => initializeState({ set, reviewWritingFormSectionListData })}>
      {children}
    </RecoilRoot>
  );

  return renderHook(
    () => {
      const hookResult = useTextAnswer({ question: reviewWritingFormSectionListData[0].questions[0] });
      const answerMap = useRecoilValue(answerMapAtom);
      const answerValidationMap = useRecoilValue(answerValidationMapAtom);
      const cardSectionList = useRecoilValue(cardSectionListSelector);

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

describe('서술형 답변 테스트', () => {
  const { min, max } = TEXT_ANSWER_LENGTH;

  const MOCK_TEXT = 'A'.repeat(max + 10);
  const COMMON_VALIDATE_CASE_LIST = [MOCK_TEXT.slice(0, min), MOCK_TEXT.slice(0, max)];
  const NOT_REQUIRED_ANSWER_VALIDATE_CASE_LIST = ['', ...COMMON_VALIDATE_CASE_LIST];
  const COMMON_INVALIDATE_CASE = MOCK_TEXT.slice(0, max + 1);
  const REQUIRED_ANSWER_INVALIDATED_CASE_LIST = ['', MOCK_TEXT.slice(0, min - 1), COMMON_INVALIDATE_CASE];

  describe('필수 질문에 대한 서술형 답변 유효성 검사', () => {
    it.each(COMMON_VALIDATE_CASE_LIST)(
      '필수 질문에 대한 서술형의 경우, 답변 글자 수가 최소 글자 수 이상 최대 글자 수 이하 조건을 만족해야한다.(글자수 : %i.length)',
      async (text) => {
        const { result } = renderUseTextAnswerHook({});
        //초기 셋팅 확인
        await waitFor(() => {
          expect(result.current.cardSectionList[0].questions[0].required).toBeTruthy();
        });

        // text값 넣기
        act(() => {
          const event = { target: { value: text } } as React.ChangeEvent<HTMLTextAreaElement>;

          result.current.handleTextAnswerChange(event);
        });

        //답변 유효성 확인
        expect(result.current.answerValidationMap?.get(MOCK_SECTION_LIST[0].questions[0].questionId)).toBeTruthy();
      },
    );

    it.each(REQUIRED_ANSWER_INVALIDATED_CASE_LIST)(
      '필수 질문에 대한 서술형의 경우, 답변 글자 수가 최소 글자 수 미만이거나 최대 글자 수를 초과하면 답변이 유효하지 않디.(글자수 : %i.length)',
      async (text) => {
        const { result } = renderUseTextAnswerHook({});
        //초기 셋팅 확인
        await waitFor(() => {
          expect(result.current.cardSectionList[0].questions[0].required).toBeTruthy();
        });

        // text값 넣기
        act(() => {
          const event = { target: { value: text } } as React.ChangeEvent<HTMLTextAreaElement>;

          result.current.handleTextAnswerChange(event);
        });

        //답변 유효성 확인
        expect(result.current.answerValidationMap?.get(MOCK_SECTION_LIST[0].questions[0].questionId)).toBeFalsy();
      },
    );
  });

  describe('선택 질문에 대한 서술형 답변 유효성 검사', () => {
    it('선택 질문에 대한 서술형 답볌의 경우, 작성한 답변이 없으면 유효한 답변이다.', async () => {
      const { result } = renderUseTextAnswerHook({
        reviewWritingFormSectionListData: NOT_REQUIRED_ANSWER_MOCK_SECTION_LIST,
      });

      //초기 셋팅 확인
      await waitFor(() => {
        expect(result.current.cardSectionList[0].questions[0].required).toBeFalsy();
      });

      expect(
        result.current.answerValidationMap?.get(NOT_REQUIRED_ANSWER_MOCK_SECTION_LIST[0].questions[0].questionId),
      ).toBeTruthy();
    });

    describe('선택 질문에 대한 서술형 답변에 작성한 답변이 있는 경우의 유효성 검사', () => {
      it.each(NOT_REQUIRED_ANSWER_VALIDATE_CASE_LIST)(
        '선택 질문이어도 작성한 답변이 있다면 답변 글자 수가 최대 글자 수 이하면 답변이 유효하디.(글자수 : %i.length)',
        async (text) => {
          const { result } = renderUseTextAnswerHook({
            reviewWritingFormSectionListData: NOT_REQUIRED_ANSWER_MOCK_SECTION_LIST,
          });

          //초기 셋팅 확인
          await waitFor(() => {
            expect(result.current.cardSectionList[0].questions[0].required).toBeFalsy();
          });

          expect(result.current.errorMessage).toEqual('');

          //change 이벤트 실행
          act(() => {
            const event = { target: { value: text } } as React.ChangeEvent<HTMLTextAreaElement>;

            result.current.handleTextAnswerChange(event);
          });

          expect(
            result.current.answerValidationMap?.get(NOT_REQUIRED_ANSWER_MOCK_SECTION_LIST[0].questions[0].questionId),
          ).toBeTruthy();
        },
      );

      it('선택 질문이어도 작성한 답변의 글자 수가 최대 글자 수를 초과하면 답변이 유효하지 않디.(글자수 : %i.length)', async () => {
        const { result } = renderUseTextAnswerHook({
          reviewWritingFormSectionListData: NOT_REQUIRED_ANSWER_MOCK_SECTION_LIST,
        });

        //초기 셋팅 확인
        await waitFor(() => {
          expect(result.current.cardSectionList[0].questions[0].required).toBeFalsy();
        });

        expect(result.current.errorMessage).toEqual('');

        //change 이벤트 실행
        act(() => {
          const event = { target: { value: COMMON_INVALIDATE_CASE } } as React.ChangeEvent<HTMLTextAreaElement>;

          result.current.handleTextAnswerChange(event);
        });

        expect(
          result.current.answerValidationMap?.get(NOT_REQUIRED_ANSWER_MOCK_SECTION_LIST[0].questions[0].questionId),
        ).toBeFalsy();
      });
    });
  });

  describe('오류 메세지 테스트', () => {
    it('답변 입력 시, 최대 글자 수를 초과하면 글자 수 초과 오류 메세지를 띄운다', async () => {
      const { result } = renderUseTextAnswerHook({});
      //초기 셋팅 확인
      await waitFor(() => {
        expect(result.current.cardSectionList[0].questions[0].required).toBeTruthy();
      });
      //오류 메세지 없음
      expect(result.current.errorMessage).toEqual('');

      // text값 넣기
      act(() => {
        const event = { target: { value: COMMON_INVALIDATE_CASE } } as React.ChangeEvent<HTMLTextAreaElement>;

        result.current.handleTextAnswerChange(event);
      });

      //답변 유효성 확인
      expect(result.current.errorMessage).toEqual(TEXT_ANSWER_ERROR_MESSAGE.max);
    });

    describe('포커스 해제 시(=onBlur) 메세지 테스트', () => {
      it.each(REQUIRED_ANSWER_INVALIDATED_CASE_LIST)(
        '필수 질문일 경우, textArea에 포커스가 해제되면 오류 메세지를 띄운다.',
        async (text) => {
          const isUnderMin = text.length < min;
          const expectedErrorMessage = isUnderMin ? TEXT_ANSWER_ERROR_MESSAGE.min : TEXT_ANSWER_ERROR_MESSAGE.max;

          const { result } = renderUseTextAnswerHook({});
          //초기 셋팅 확인
          await waitFor(() => {
            expect(result.current.cardSectionList[0].questions[0].required).toBeTruthy();
          });

          expect(result.current.errorMessage).toEqual('');

          //onBlur
          act(() => {
            const event = { target: { value: text } } as React.ChangeEvent<HTMLTextAreaElement>;

            result.current.handleTextAnswerBlur(event);
          });

          // 오류 메세지 확인
          expect(result.current.errorMessage).toEqual(expectedErrorMessage);
        },
      );

      describe('선택 질문', () => {
        it.each(NOT_REQUIRED_ANSWER_VALIDATE_CASE_LIST)(
          '선택 질문에 대한 유효한 서술형 답변인 경우(= 최소 글자 이하), textArea에 포커스가 해제되어도 오류메세지를 띄우지 않는다',
          async (text) => {
            const expectedErrorMessage = '';

            const { result } = renderUseTextAnswerHook({
              reviewWritingFormSectionListData: NOT_REQUIRED_ANSWER_MOCK_SECTION_LIST,
            });

            //초기 셋팅 확인
            await waitFor(() => {
              expect(result.current.cardSectionList[0].questions[0].required).toBeFalsy();
            });

            expect(result.current.errorMessage).toEqual('');

            //onBlur
            act(() => {
              const event = { target: { value: text } } as React.ChangeEvent<HTMLTextAreaElement>;

              result.current.handleTextAnswerBlur(event);
            });

            // 오류 메세지 확인
            expect(result.current.errorMessage).toEqual(expectedErrorMessage);
          },
        );

        it('선택 질문이어도 작성한 답변이 있고 답변이 유효하지 않다면(= 최대 글자수를 초과), textArea에 포커스가 해제될때 오류 메세지를 띄운다.', async () => {
          const expectedErrorMessage = TEXT_ANSWER_ERROR_MESSAGE.max;

          const { result } = renderUseTextAnswerHook({
            reviewWritingFormSectionListData: NOT_REQUIRED_ANSWER_MOCK_SECTION_LIST,
          });

          //초기 셋팅 확인
          await waitFor(() => {
            expect(result.current.cardSectionList[0].questions[0].required).toBeFalsy();
          });

          expect(result.current.errorMessage).toEqual('');

          //onBlur
          act(() => {
            const event = { target: { value: COMMON_INVALIDATE_CASE } } as React.ChangeEvent<HTMLTextAreaElement>;

            result.current.handleTextAnswerBlur(event);
          });

          // 오류 메세지 확인
          expect(result.current.errorMessage).toEqual(expectedErrorMessage);
        });
      });
    });
  });
});
