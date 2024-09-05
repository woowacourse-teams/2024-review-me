import { useState } from 'react';

import { TEXT_ANSWER_LENGTH } from '@/pages/ReviewWritingPage/constants';
import { ReviewWritingAnswer, ReviewWritingCardQuestion } from '@/types';

import useUpdateReviewerAnswer from '../useUpdateReviewerAnswer';

export const TEXT_ANSWER_ERROR_MESSAGE = {
  empty: '공백만 입력할 수 없어요',
  min: `최소 ${TEXT_ANSWER_LENGTH.min}자 이상 작성해 주세요`,
  max: `최대 ${TEXT_ANSWER_LENGTH.max}자까지만 입력 가능해요`,
  noError: '',
};

export type TextAnswerErrorMessageKey = keyof typeof TEXT_ANSWER_ERROR_MESSAGE;
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
    handleErrorMessageOnChange(value);
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

    const isNotMaxSatisfied = text.length > max;
    const isNotMinSatisfied = text.length < min;
    //선택 질문 유효성 조건 - 최대 글자 이하
    if (!question.required) {
      return isNotMaxSatisfied ? 'max' : 'noError';
    }

    // 필수 질문 유효성 조건 - 최소 글자 이상 최대 글자 이하
    if (isEmpty) return 'empty';
    if (isNotMaxSatisfied) return 'max';
    if (isNotMinSatisfied) return 'min';

    return 'noError';
  };

  /**
    작성한 답변의 유효성 검사 여뷰에 따라 오류 메세지 관리
   */
  const handleErrorMessageOnChange = (value: string) => {
    const validationResult = validateTextLength(value);
    // 입력 중일때는 최소 글자 오류 메세지 보여주지 않음

    const isHideErrorMessage = validationResult !== 'max';
    setErrorMessage(
      isHideErrorMessage ? TEXT_ANSWER_ERROR_MESSAGE.noError : TEXT_ANSWER_ERROR_MESSAGE[validationResult],
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
    const answerValidation = validateTextLength(value) === 'noError';
    // 유효한 답변이여야 text에 value를 반영
    const newAnswer: ReviewWritingAnswer = {
      questionId: question.questionId,
      selectedOptionIds: null,
      text: answerValidation ? value : '',
    };

    return { newAnswer, answerValidation };
  };

  const handleTextAnswerBlur = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { value } = event.target;
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
