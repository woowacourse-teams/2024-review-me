import { useState } from 'react';

import { ReviewWritingAnswer, ReviewWritingCardQuestion } from '@/types';

import useUpdateReviewerAnswer from '../useUpdateReviewerAnswer';

export const TEXT_ANSWER_LENGTH = {
  min: 20,
  max: 1000,
  extra: 10,
};

export const TEXT_ANSWER_ERROR_MESSAGE = {
  empty: '공백만 입력할 수 없어요',
  min: `최소 ${TEXT_ANSWER_LENGTH.min}자 이상 작성해 주세요`,
  max: `최대 ${TEXT_ANSWER_LENGTH.max}자까지만 입력 가능해요`,
  noError: '',
};

export const TEXT_ANSWER_ERROR_MESSAGE_KEY = {
  empty: 'empty',
  min: 'min',
  max: 'max',
  noError: 'noError',
} as const;

export type TextAnswerErrorMessageKey = keyof typeof TEXT_ANSWER_ERROR_MESSAGE_KEY;
interface UseTextAnswerProps {
  question: ReviewWritingCardQuestion;
}
/**
 * 하나의 주관식 질문에 대한 답변을 관리하는 훅
 */
const useTextAnswer = ({ question }: UseTextAnswerProps) => {
  const { updateAnswerMap, updateAnswerValidationMap } = useUpdateReviewerAnswer();

  const [text, setText] = useState('');
  const [errorMessage, setErrorMessage] = useState(TEXT_ANSWER_ERROR_MESSAGE.noError);

  const handleTextAnswerChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { value } = event.target;

    // NOTE: max 넘치는 글자는 max+ extra 만큼 자르는 이유
    // 1. 글자는 사용자가 입력한대로 보여줘야한다. (복붙해서 사용할때 이슈 있었음)
    // 2. 과도한 입력을 방지하기 위해 max를 넘어서는 일정 수준에서 글자를 자른다.
    sliceTextAnswer(value);
    handleErrorMessage(value);
    handleUpdateAnswerState(value);
  };

  const sliceTextAnswer = (value: string) => {
    const { max, extra } = TEXT_ANSWER_LENGTH;

    setText(value.slice(0, max + extra));
  };

  type TextAnswerErrorMessage = keyof typeof TEXT_ANSWER_ERROR_MESSAGE;

  const validateTextLength = (text: string): TextAnswerErrorMessage => {
    const { min, max } = TEXT_ANSWER_LENGTH;

    const isEmpty = text && text.trim() === '';
    const isOverMax = text.length > max;
    const isUnderMin = text.length < min;

    if (isEmpty) return TEXT_ANSWER_ERROR_MESSAGE_KEY.empty;
    if (isOverMax) return TEXT_ANSWER_ERROR_MESSAGE_KEY.max;
    if (isUnderMin) return TEXT_ANSWER_ERROR_MESSAGE_KEY.min;

    return TEXT_ANSWER_ERROR_MESSAGE_KEY.noError;
  };

  /**
    작성한 답변의 유효성 검사 여뷰에 따라 오류 메세지 관리
   */
  const handleErrorMessage = (value: string) => {
    const validationResult = validateTextLength(value);
    // 입력 중일때는 최소 글자 오류 메세지 보여주지 않음
    setErrorMessage(
      validationResult === TEXT_ANSWER_ERROR_MESSAGE_KEY.min
        ? TEXT_ANSWER_ERROR_MESSAGE.noError
        : TEXT_ANSWER_ERROR_MESSAGE[validationResult],
    );
  };

  /**
   * 서술형에 작성한 답변에 따라, answerMap, answerValidationMap을 업데이트하는 함수
   */
  const handleUpdateAnswerState = (value: string) => {
    const { newAnswer, answerValidation } = getNewAnswerAndValidation(value);

    updateAnswerMap(newAnswer);
    updateAnswerValidationMap(newAnswer, answerValidation);
  };

  /**
   * 서술형에 작성한 답변에 따라, answerMap, answerValidationMap에 반영될 새로운 답과 답의 유효성 여부를 반환하는 함수
   */
  const getNewAnswerAndValidation = (value: string) => {
    const validationResult = validateTextLength(value);
    //answer 업데이트
    const isValidatedText = validationResult === TEXT_ANSWER_ERROR_MESSAGE_KEY.noError;
    /**
     * 선택 질문이면서 답변이 없는 지 여부
     */
    const isNotRequiredEmptyAnswer = !question.required && value === '';
    const answerValidation = isValidatedText || isNotRequiredEmptyAnswer;

    // 유효한 답변이여야 text에 value를 반영
    const newAnswer: ReviewWritingAnswer = {
      questionId: question.questionId,
      selectedOptionIds: null,
      // 선택 질문이여도, 글자 개수가 유효성 여부 판단
      text: isValidatedText ? value : '',
    };

    return { newAnswer, answerValidation };
  };

  const handleTextAnswerBlur = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { value } = event.target;

    // 선택 질문, 작성한 답변이 없는 경우
    if (!question.required && value.length === 0) return;
    // 필수 질문 이거나 작성한 답변이 있는 선택 질문
    const validationResult = validateTextLength(value);
    setErrorMessage(TEXT_ANSWER_ERROR_MESSAGE[validationResult]);
  };

  return {
    text,
    minLength: TEXT_ANSWER_LENGTH.min,
    maxLength: TEXT_ANSWER_LENGTH.max,
    handleTextAnswerChange,
    handleTextAnswerBlur,
    errorMessage,
  };
};

export default useTextAnswer;
